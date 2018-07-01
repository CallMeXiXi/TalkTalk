package com.example.joe.talktalk.account.register.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.joe.talktalk.R;
import com.example.joe.talktalk.account.register.RegisterActivity;
import com.example.joe.talktalk.base.BaseFragment;
import com.example.joe.talktalk.common.Constants;
import com.example.joe.talktalk.utils.ToastUtil;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

/**
 * Created by Administrator on 2018/6/28 0028.
 */

public class Register2Fragment extends BaseFragment {

    //验证码
    private String code;
    //第一个界面传来的手机号码及密码
    private String phoneNumber;
    private String password;
    //上下文
    private Context mContext;
    private RegisterActivity mActivity;
    //静态实体类变量
    private static Register2Fragment instance;
    //控件
    private Button mBtnGetCode;
    private EditText mEtCode;
    //倒计时控件
    private CountDownTimer timer;


    public static Register2Fragment getInstance(String phoneNumber, String password) {
        if (instance == null) {
            synchronized (Register2Fragment.class) {
                if (instance == null) {
                    instance = new Register2Fragment();
                    Bundle bundle = new Bundle();
                    bundle.putString(Constants.PHONE_NUMBER, phoneNumber);
                    bundle.putString(Constants.PHONE_PASSWORD, password);
                    instance.setArguments(bundle);
                }
            }
        }
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register_2_layout, container, false);
        init(view);
        mContext = getActivity();
        mActivity = (RegisterActivity) getActivity();
        return view;
    }

    @Override
    public void initView(View view) {
        mEtCode = $(view, R.id.et_verify_code);
        mBtnGetCode = $(view, R.id.btn_get_verify_code);
        click(view, R.id.btn_get_verify_code);
        click(view, R.id.btn_next);

        initSMS();
        //发送验证码
        startTimer();
    }

    @Override
    public void initData() {
        phoneNumber = getArguments().getString(Constants.PHONE_NUMBER);
        password = getArguments().getString(Constants.PHONE_PASSWORD);
    }

    @Override
    public void initListener() {

    }

    @Override
    public void doClick(int viewId) {
        switch (viewId) {
            case R.id.btn_get_verify_code://重新发送验证码
                // 请求验证码，其中country表示国家代码，如“86”；phone表示手机号码，如“13800138000”
                SMSSDK.getVerificationCode(Constants.CHINA_COUNTRY, phoneNumber);
                //发送验证码
                startTimer();
                break;
            case R.id.btn_next://下一步按钮
                code = mEtCode.getText().toString();
                if (code != null) {
                    // 提交验证码，其中的code表示验证码
                    SMSSDK.submitVerificationCode(Constants.CHINA_COUNTRY, phoneNumber, code);
                } else {
                    ToastUtil.showShortToast(mContext, R.string.verifyCodeError);
                }
                break;
        }
    }

    /**
     * 初始化短信的SDK
     */
    private void initSMS() {
        //注册回调监听，放到发送和验证前注册，注意这里是子线程需要传到主线程中去操作后续提示
        EventHandler eh = new EventHandler() {

            @Override
            public void afterEvent(int event, int result, Object data) {
                Message msg = new Message();
                msg.arg1 = event;
                msg.arg2 = result;
                msg.obj = data;
                mHandler.sendMessage(msg);
            }
        };
        SMSSDK.registerEventHandler(eh);
    }

    public Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int result = msg.arg2;
            int event = msg.arg1;
            if (result == SMSSDK.RESULT_COMPLETE) {
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {//验证码提交成功
                    resetTimer();
                    mActivity.changeFragment(Register3Fragment.getInstance(phoneNumber, password));
                }
                if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {// 验证码重新发送
                    showLongToast("验证码已发送");
                }
            } else {
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                    showLongToast("验证码错误");
                }
            }
        }
    };

    /**
     * 验证码倒计时60S
     */
    public void startTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        timer = new CountDownTimer(60 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mBtnGetCode.setEnabled(false);
                mBtnGetCode.setText(millisUntilFinished / 1000 + "S后重新发送");
            }

            @Override
            public void onFinish() {
                mBtnGetCode.setEnabled(true);
                mBtnGetCode.setText("获取验证码");
            }
        }.start();
    }

    /**
     * 重置Timer
     */
    public void resetTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        mBtnGetCode.setEnabled(true);
        mBtnGetCode.setText("重新发送");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        resetTimer();
        SMSSDK.unregisterAllEventHandler();
    }
}
