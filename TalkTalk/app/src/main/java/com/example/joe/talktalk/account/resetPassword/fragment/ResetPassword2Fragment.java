package com.example.joe.talktalk.account.resetPassword.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.UpdatePasswordCallback;
import com.example.joe.talktalk.R;
import com.example.joe.talktalk.account.LoginActivity;
import com.example.joe.talktalk.account.resetPassword.ResetPasswordActivity;
import com.example.joe.talktalk.base.BaseFragment;
import com.example.joe.talktalk.common.Constants;
import com.example.joe.talktalk.utils.ToastUtil;

public class ResetPassword2Fragment extends BaseFragment {

    private static final String TAG = "ResetPassword2Fragment";
    //验证码及新密码
    private String code;
    private String password1;
    private String password2;
    //控件
    private EditText mEtPassword1;
    private EditText mEtPassword2;
    //上下文
    private ResetPasswordActivity mActivity;
    private Context mContext;
    private static ResetPassword2Fragment instance;

    public static ResetPassword2Fragment getInstance(String code) {
        if (instance == null) {
            synchronized (ResetPassword2Fragment.class) {
                if (instance == null) {
                    instance = new ResetPassword2Fragment();
                    Bundle bundle = new Bundle();
                    bundle.putString(Constants.VERIFY_CODE, code);
                    instance.setArguments(bundle);
                }
            }
        }
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reset_passwrd_2_layout, container, false);
        mContext = getActivity();
        mActivity = (ResetPasswordActivity) getActivity();
        init(view);
        return view;
    }

    @Override
    public void initView(View view) {
        click(view, R.id.btn_sure);
        mEtPassword1 = $(view, R.id.et_forget_password1);
        mEtPassword2 = $(view, R.id.et_forget_password2);
    }

    @Override
    public void initData() {
        code = getArguments().getString(Constants.VERIFY_CODE);
    }

    @Override
    public void initListener() {

    }

    @Override
    public void doClick(int viewId) {
        switch (viewId) {
            case R.id.btn_sure:
                reset();
                break;
        }
    }

    /**
     * 重置密码
     */
    private void reset() {
        if (checkData()) {
            AVUser.resetPasswordBySmsCodeInBackground(code, password1, new UpdatePasswordCallback() {
                @Override
                public void done(AVException e) {
                    if (e == null) {
                        Log.d(TAG, "重置密码成功");
                        ToastUtil.showShortToast(mContext, "重置密码成功");
                        LoginActivity.launch(mContext);
                        mActivity.finish();
                    } else {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    /**
     * 检查数据
     */
    private boolean checkData() {
        password1 = mEtPassword1.getText().toString();
        password2 = mEtPassword2.getText().toString();

        if (TextUtils.isEmpty(password1)) {
            ToastUtil.showShortToast(mContext, "新密码不能为空");
            return false;
        } else if (password1.length() < 6 || password1.length() > 16) {
            ToastUtil.showShortToast(mContext, "新密码长度设置在6~16之间");
            return false;
        } else if (password1 != password2) {
            ToastUtil.showShortToast(mContext, "新密码长度设置在6~16之间");
            return false;
        }
        return true;
    }
}
