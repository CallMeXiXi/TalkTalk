package com.example.joe.talktalk;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.joe.talktalk.base.BaseAppCompatActivity;
import com.example.joe.talktalk.im.fragment.ContactsFragment;
import com.example.joe.talktalk.im.fragment.FindFragment;
import com.example.joe.talktalk.im.fragment.MeFragment;
import com.example.joe.talktalk.im.fragment.TalkFragment;

public class MainActivity extends BaseAppCompatActivity {

    //控件
    private Toolbar mToolbar;
    private TextView mTvTalk;
    private TextView mTvContacts;
    private TextView mTvFind;
    private TextView mTvMe;
    private ImageView mIvTalk;
    private ImageView mIvContacts;
    private ImageView mIvFind;
    private ImageView mIvMe;

    private FragmentTransaction mTransaction;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        click(R.id.ll_main_talk);
        click(R.id.ll_main_contacts);
        click(R.id.ll_main_find);
        click(R.id.ll_main_me);
        mToolbar = $(R.id.toolbar);
        mTvTalk = $(R.id.tv_main_talk);
        mTvContacts = $(R.id.tv_main_contacts);
        mTvFind = $(R.id.tv_main_find);
        mTvMe = $(R.id.tv_main_me);
        mIvTalk = $(R.id.iv_main_talk);
        mIvContacts = $(R.id.iv_main_contacts);
        mIvFind = $(R.id.iv_main_find);
        mIvMe = $(R.id.iv_main_me);
        changeFragment(TalkFragment.getInstance());
    }

    @Override
    public void initTitle() {
        mToolbar.setTitle("TT");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void initListener() {
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.this.finish();
            }
        });
    }

    @Override
    public void doClick(int viewId) {
        switch (viewId) {
            case R.id.ll_main_talk:
                changeFragment(TalkFragment.getInstance());
                break;
            case R.id.ll_main_contacts:
                changeFragment(ContactsFragment.getInstance());
                break;
            case R.id.ll_main_find:
                changeFragment(FindFragment.getInstance());
                break;
            case R.id.ll_main_me:
                changeFragment(MeFragment.getInstance());
                break;
        }
    }

    /**
     * 切换fragment
     *
     * @param f
     */
    public void changeFragment(Fragment f) {
        mTransaction = getSupportFragmentManager().beginTransaction();
        mTransaction.replace(R.id.ll_main_container, f);
        mTransaction.commit();
        switchStatus(f);
    }

    /**
     * 切换页面时地下菜单状态改变
     *
     * @param f
     */
    private void switchStatus(Fragment f) {
        if (f instanceof TalkFragment) {
            mTvTalk.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
            mIvTalk.setImageResource(R.mipmap.main_talk_select);
            mTvContacts.setTextColor(ContextCompat.getColor(this, R.color.main_gray));
            mIvContacts.setImageResource(R.mipmap.main_contacts_unselect);
            mTvFind.setTextColor(ContextCompat.getColor(this, R.color.main_gray));
            mIvFind.setImageResource(R.mipmap.main_find_unselect);
            mTvMe.setTextColor(ContextCompat.getColor(this, R.color.main_gray));
            mIvMe.setImageResource(R.mipmap.main_me_unselect);
        }
        if (f instanceof ContactsFragment) {
            mTvTalk.setTextColor(ContextCompat.getColor(this, R.color.main_gray));
            mIvTalk.setImageResource(R.mipmap.main_talk_unselect);
            mTvContacts.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
            mIvContacts.setImageResource(R.mipmap.main_contacts_select);
            mTvFind.setTextColor(ContextCompat.getColor(this, R.color.main_gray));
            mIvFind.setImageResource(R.mipmap.main_find_unselect);
            mTvMe.setTextColor(ContextCompat.getColor(this, R.color.main_gray));
            mIvMe.setImageResource(R.mipmap.main_me_unselect);
        }
        if (f instanceof FindFragment) {
            mTvTalk.setTextColor(ContextCompat.getColor(this, R.color.main_gray));
            mIvTalk.setImageResource(R.mipmap.main_talk_unselect);
            mTvContacts.setTextColor(ContextCompat.getColor(this, R.color.main_gray));
            mIvContacts.setImageResource(R.mipmap.main_contacts_unselect);
            mTvFind.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
            mIvFind.setImageResource(R.mipmap.main_find_select);
            mTvMe.setTextColor(ContextCompat.getColor(this, R.color.main_gray));
            mIvMe.setImageResource(R.mipmap.main_me_unselect);
        }
        if (f instanceof MeFragment) {
            mTvTalk.setTextColor(ContextCompat.getColor(this, R.color.main_gray));
            mIvTalk.setImageResource(R.mipmap.main_talk_unselect);
            mTvContacts.setTextColor(ContextCompat.getColor(this, R.color.main_gray));
            mIvContacts.setImageResource(R.mipmap.main_contacts_unselect);
            mTvFind.setTextColor(ContextCompat.getColor(this, R.color.main_gray));
            mIvFind.setImageResource(R.mipmap.main_find_unselect);
            mTvMe.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
            mIvMe.setImageResource(R.mipmap.main_me_select);
        }
    }

    public static void launch(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }
}
