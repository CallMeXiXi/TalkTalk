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

public class FindFragment extends BaseFragment {

    private static FindFragment instance;

    public static FindFragment getInstance() {
        if (instance == null) {
            synchronized (FindFragment.class) {
                if (instance == null) {
                    instance = new FindFragment();
                }
            }
        }
        return instance;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_find_layout, container, false);
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
