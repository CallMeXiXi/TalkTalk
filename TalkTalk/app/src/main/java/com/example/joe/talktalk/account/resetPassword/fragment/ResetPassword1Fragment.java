package com.example.joe.talktalk.account.resetPassword.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.RequestMobileCodeCallback;
import com.avos.avoscloud.UpdatePasswordCallback;
import com.example.joe.talktalk.R;
import com.example.joe.talktalk.account.resetPassword.ResetPasswordActivity;
import com.example.joe.talktalk.base.BaseFragment;
import com.example.joe.talktalk.utils.ToastUtil;

public class ResetPassword1Fragment extends BaseFragment {

    private static final String TAG = "ResetPassword1Fragment";
    //验证码及手机号码
    private String code;
    private String phoneNumber;
    //控件
    private EditText mEtPhoneNumber;
    private EditText mEtCode;
    private Button btnSendCode;
    private CountDownTimer timer;
    //上下文
    private ResetPasswordActivity mActivity;
    private Context mContext;
    private static ResetPassword1Fragment instance;

    public static ResetPassword1Fragment getInstance() {
        if (instance == null) {
            synchronized (ResetPassword1Fragment.class) {
                if (instance == null) {
                    instance = new ResetPassword1Fragment();
                }
            }
        }
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reset_passwrd_1_layout, container, false);
        mContext = getActivity();
        mActivity = (ResetPasswordActivity) getActivity();
        init(view);
        return view;
    }

    @Override
    public void initView(View view) {
        click(view, R.id.btn_get_verify_code);
        click(view, R.id.btn_next);
        mEtPhoneNumber = $(view, R.id.et_phone_number);
        mEtCode = $(view, R.id.et_verify_code);
        btnSendCode = $(view, R.id.btn_get_verify_code);
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
            case R.id.btn_get_verify_code://获取验证码
                sendCode();
                break;
            case R.id.btn_next://下一步按钮
                resetPassword();
                break;
        }
    }

    /**
     * 跳转到下一步
     */
    private void resetPassword() {
        code = mEtCode.getText().toString();
        if (TextUtils.isEmpty(code)) {
            ToastUtil.showShortToast(mContext, R.string.phoneNumberError);
            return;
        }
        mActivity.changeFragment(ResetPassword2Fragment.getInstance(code));
    }

    /**
     * 发送验证码
     */
    private void sendCode() {
        phoneNumber = mEtPhoneNumber.getText().toString();
        if (TextUtils.isEmpty(phoneNumber)) {
            ToastUtil.showShortToast(mContext, R.string.phoneNumberError);
            return;
        } else if (phoneNumber.length() != 11) {
            ToastUtil.showShortToast(mContext, "手机号码格式不对");
            return;
        }
        startTimer();//开启倒计时
        AVUser.requestPasswordResetBySmsCodeInBackground(phoneNumber, new RequestMobileCodeCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    Log.d(TAG, "发送验证码成功");
                } else {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 启动Timer
     */
    private void startTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        timer = new CountDownTimer(60 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                btnSendCode.setEnabled(false);
                btnSendCode.setText(millisUntilFinished / 1000 + "S后重新发送");
            }

            @Override
            public void onFinish() {
                btnSendCode.setEnabled(false);
                btnSendCode.setText("获取验证码");
            }
        }.start();
    }

    /**
     * 重置Timer
     */
    private void resetTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        btnSendCode.setEnabled(false);
        btnSendCode.setText("获取验证码");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        resetTimer();
    }
}
