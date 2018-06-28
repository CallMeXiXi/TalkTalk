package com.example.joe.talktalk.account.register.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.joe.talktalk.base.BaseFragment;

/**
 * Created by Administrator on 2018/6/28 0028.
 */

public class Register3Fragment extends BaseFragment {

    private static Register3Fragment instance;

    public static Register3Fragment getInstance() {
        if (instance == null) {
            synchronized (Register3Fragment.class) {
                if (instance == null) {
                    instance = new Register3Fragment();
                }
            }
        }
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void initView(View view) {

    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }

    @Override
    public void doClick(int viewId) {

    }
}
