package com.example.joe.talktalk.account.register.fragment;

import android.content.Context;
import android.os.Bundle;
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
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }

    @Override
    public void doClick(int viewId) {
        phoneNumber = mEtPhoneNumber.getText().toString();
        password = mEtPassword.getText().toString();

        if (checkData()) {
            mActivity.changeFragment(Register2Fragment.getInstance());
        }
    }

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
}
