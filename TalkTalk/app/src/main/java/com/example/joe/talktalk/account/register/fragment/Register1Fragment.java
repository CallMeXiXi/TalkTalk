package com.example.joe.talktalk.account.register.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.cengalabs.flatui.views.FlatButton;
import com.example.joe.talktalk.R;
import com.example.joe.talktalk.account.register.RegisterActivity;
import com.example.joe.talktalk.base.BaseFragment;
import com.example.joe.talktalk.ui.PwdEditText;
import com.example.joe.talktalk.utils.ToastUtil;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

/**
 * Created by Administrator on 2018/6/28 0028.
 */

public class Register1Fragment extends BaseFragment {

    private RegisterActivity mActivity;
    private Context mContext;
    private EditText mEtPhoneNumber;
    private PwdEditText mEtPassword;
    private String phoneNumber;
    private String password;

    private static Register1Fragment instance;

    public static Register1Fragment getInstance() {
        if (instance == null) {
            synchronized (Register1Fragment.class) {
                if (instance == null) {
                    instance = new Register1Fragment();
                }
            }
        }
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register_1_layout, container, false);
        init(view);
        mContext = getActivity();
        mActivity = (RegisterActivity) getActivity();
        return view;
    }

    @Override
    public void initView(View view) {
        click(view, R.id.fb_next);
        mEtPhoneNumber = $(view, R.id.et_phone_number);
        mEtPassword = $(view, R.id.et_password);
        initSMS();
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }

    @Override
    public void doClick(int viewId) {
        switch (viewId) {
            case R.id.fb_next:
                phoneNumber = mEtPhoneNumber.getText().toString();
                password = mEtPassword.getText().toString();

                if (checkData()) {
                    // 请求验证码，其中country表示国家代码，如“86”；phone表示手机号码，如“13800138000”
                    SMSSDK.getVerificationCode("86", phoneNumber);
                }
                break;
        }
    }

    /**
     * 数据格式判断
     *
     * @return
     */
    private boolean checkData() {
        if (TextUtils.isEmpty(mEtPhoneNumber.getText())) {
            ToastUtil.showShortToast(mContext, R.string.phoneNumberError);
            return false;
        } else if (TextUtils.isEmpty(mEtPassword.getText())) {
            ToastUtil.showShortToast(mContext, R.string.passwordError);
            return false;
        } else if (mEtPassword.getText().length() < 6 || mEtPassword.getText().length() > 16) {
            ToastUtil.showShortToast(mContext, R.string.passwordLengthGreat6);
            return false;
        }
        return true;
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
                if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                    showLongToast("验证码已发送");
                    mActivity.changeFragment(Register2Fragment.getInstance(phoneNumber, password));
                }
            }
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        //用完回调要注销掉，否则可能会出现内存泄露
        SMSSDK.unregisterAllEventHandler();
    }
}
