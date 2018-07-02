package com.example.joe.talktalk.im.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.joe.talktalk.R;
import com.example.joe.talktalk.base.BaseFragment;

/**
 * Created by Administrator on 2018/7/2 0002.
 */

public class MeFragment extends BaseFragment {

    private static MeFragment instance;

    public static MeFragment getInstance() {
        if (instance == null) {
            synchronized (MeFragment.class) {
                if (instance == null) {
                    instance = new MeFragment();
                }
            }
        }
        return instance;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_me_layout, container, false);
        init(view);
        return view;
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
