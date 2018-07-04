package com.example.joe.talktalk.account;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;
import com.example.joe.talktalk.MainActivity;
import com.example.joe.talktalk.R;
import com.example.joe.talktalk.account.register.RegisterActivity;
import com.example.joe.talktalk.account.resetPassword.ResetPasswordActivity;
import com.example.joe.talktalk.base.BaseAppCompatActivity;
import com.example.joe.talktalk.ui.LoadingDialog;
import com.example.joe.talktalk.utils.ToastUtil;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2018/6/28 0028.
 */

public class LoginActivity extends BaseAppCompatActivity {

    private static final String TAG = "LoginActivity";

    private String phoneNumber;
    private String password;
    //控件
    private CircleImageView civUserIcon;
    private EditText mEtPhoneNumber;
    private EditText mEtPassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        click(R.id.btn_login);
        click(R.id.tv_register);
        click(R.id.tv_forget_password);
        civUserIcon = $(R.id.iv_header);
        mEtPhoneNumber = $(R.id.et_phone);
        mEtPassword = $(R.id.et_password);
    }

    @Override
    public void initTitle() {
    }

    @Override
    public void initListener() {

    }

    @Override
    public void doClick(int viewId) {
        switch (viewId) {
            case R.id.tv_register://注册
                RegisterActivity.launch(this);
                break;
            case R.id.btn_login://登陆
                showLoadingDialog("登录中..");
                login();
                break;
            case R.id.tv_forget_password://忘记密码
                ResetPasswordActivity.launch(this);
                break;
        }
    }

    private void login() {
        phoneNumber = mEtPhoneNumber.getText().toString();
        password = mEtPassword.getText().toString();

        AVUser.logInInBackground(phoneNumber, password, new LogInCallback<AVUser>() {
            @Override
            public void done(AVUser avUser, AVException e) {
                if (e == null) {
                    MainActivity.launch(LoginActivity.this);
                    LoginActivity.this.finish();
                } else {
                    Log.d(TAG, e.getMessage());
                    showShortToast(e.getMessage());
                }
            }
        });
    }

    /**
     * 检查数据
     */
    private boolean checkData() {
        if (TextUtils.isEmpty(mEtPhoneNumber.getText())) {
            ToastUtil.showShortToast(this, R.string.phoneNumberError);
            return false;
        } else if (TextUtils.isEmpty(mEtPassword.getText())) {
            ToastUtil.showShortToast(this, R.string.passwordError);
            return false;
        } else if (mEtPassword.getText().length() < 6 || mEtPassword.getText().length() > 16) {
            ToastUtil.showShortToast(this, R.string.passwordLengthGreat6);
            return false;
        }
        return true;
    }

    public static void launch(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }
}
