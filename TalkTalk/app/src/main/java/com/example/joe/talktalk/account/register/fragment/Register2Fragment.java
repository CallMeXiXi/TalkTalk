package com.example.joe.talktalk.account.register.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.cengalabs.flatui.views.FlatButton;
import com.example.joe.talktalk.R;
import com.example.joe.talktalk.account.register.RegisterActivity;
import com.example.joe.talktalk.base.BaseFragment;

/**
 * Created by Administrator on 2018/6/28 0028.
 */

public class Register2Fragment extends BaseFragment {

    private Context mContext;
    private RegisterActivity mActivity;
    private String phoneNumber;
    private String password;
    private static Register2Fragment instance;

    private String code;
    private EditText mEtCode;

    public Register2Fragment(String phoneNumber, String password) {
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

    public static Register2Fragment getInstance(String phoneNumber, String password) {
        if (instance == null) {
            synchronized (Register2Fragment.class) {
                if (instance == null) {
                    instance = new Register2Fragment(phoneNumber, password);
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
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void initView(View view) {
        mEtCode = $(view, R.id.et_verify_code);
        click(view, R.id.btn_get_verify_code);
        click(view, R.id.fb_next);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }

    @Override
    public void doClick(int viewId) {
        switch (viewId){
            case R.id.btn_get_verify_code:
                break;
            case R.id.fb_next:
                break;
        }
    }
}
