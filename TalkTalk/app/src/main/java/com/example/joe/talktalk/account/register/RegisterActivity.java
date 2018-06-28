package com.example.joe.talktalk.account.register;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.joe.talktalk.R;
import com.example.joe.talktalk.account.register.fragment.Register1Fragment;
import com.example.joe.talktalk.account.register.fragment.Register2Fragment;
import com.example.joe.talktalk.account.register.fragment.Register3Fragment;
import com.example.joe.talktalk.base.BaseAppCompatActivity;

/**
 * Created by Administrator on 2018/6/28 0028.
 */
public class RegisterActivity extends BaseAppCompatActivity {

    private Toolbar mToolbar;
    private TextView mTvTips1;
    private TextView mTvTips2;
    private TextView mTvTips3;
    private FragmentTransaction mTransaction;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_layout);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        mToolbar = $(R.id.toolbar);
        mTvTips1 = $(R.id.tv_tips_1);
        mTvTips2 = $(R.id.tv_tips_2);
        mTvTips3 = $(R.id.tv_tips_3);
        changeFragment(Register1Fragment.getInstance());
    }

    @Override
    public void initTitle() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(R.string.register);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void initListener() {
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void doClick(int viewId) {

    }

    /**
     * 切换fragment颜色变化
     *
     * @param f
     */
    private void switchStatusColor(Fragment f) {
        if (f instanceof Register1Fragment) {
            mTvTips1.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
            mTvTips2.setTextColor(ContextCompat.getColor(this, R.color.gray));
            mTvTips3.setTextColor(ContextCompat.getColor(this, R.color.gray));
        }
        if (f instanceof Register2Fragment) {
            mTvTips1.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
            mTvTips2.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
            mTvTips3.setTextColor(ContextCompat.getColor(this, R.color.gray));
        }
        if (f instanceof Register3Fragment) {
            mTvTips1.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
            mTvTips2.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
            mTvTips3.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        }
    }

    public void changeFragment(Fragment f) {
        mTransaction = getSupportFragmentManager().beginTransaction();
        mTransaction.replace(R.id.ll_container, f);
        mTransaction.commit();
        switchStatusColor(f);
    }

    public static void launch(Context context) {
        Intent intent = new Intent(context, RegisterActivity.class);
        context.startActivity(intent);
    }
}
