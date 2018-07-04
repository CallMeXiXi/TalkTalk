package com.example.joe.talktalk.im.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCreatedCallback;
import com.example.joe.talktalk.R;
import com.example.joe.talktalk.base.BaseAppCompatActivity;
import com.example.joe.talktalk.common.Constants;
import com.example.joe.talktalk.im.AVImClientManager;
import com.example.joe.talktalk.utils.ToastUtil;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class ContactsInfoActivity extends BaseAppCompatActivity {

    //clientId和对话Id
    private String name;
    private String imageUri;
    //控件
    private Toolbar mToolbar;
    private CircleImageView civHeader;
    private TextView tvName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contacts_info_layout);
    }

    @Override
    public void initData() {
        name = getIntent().getStringExtra(Constants.NICK_NAME);
        imageUri = getIntent().getStringExtra(Constants.IMAGE_URI);
    }

    @Override
    public void initView() {
        click(R.id.ll_setting);
        click(R.id.btn_chat);
        mToolbar = $(R.id.toolbar);
        civHeader = $(R.id.civ_header);
        tvName = $(R.id.tv_name);

        tvName.setText(name);
    }

    @Override
    public void initTitle() {
        mToolbar.setTitle(name);
        setSupportActionBar(mToolbar);
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
        switch (viewId) {
            case R.id.btn_chat:
                //TODO 继续或者创建会话
                createConversation();
                break;
            case R.id.ll_setting:
                ToastUtil.showShortToast(this, "该功能暂未开放");
                break;
        }
    }

    /**
     * 开始对话
     */
    private void createConversation() {
        Map<String, Object> attr = new HashMap<>();
        attr.put("nickName", name);
        AVIMClient client = AVImClientManager.getInstance().getAvimClient();
        client.createConversation(Arrays.asList(name), name, attr, false, true,
                new AVIMConversationCreatedCallback() {

                    @Override
                    public void done(AVIMConversation conversation, AVIMException e) {
                        if (e == null) {
                            ToastUtil.showShortToast(ContactsInfoActivity.this, "sghjskdg");
                        }
                    }
                });
    }


    public static void launch(Context context, String name, String imageUri) {
        Intent intent = new Intent(context, ContactsInfoActivity.class);
        intent.putExtra(Constants.NICK_NAME, name);
        intent.putExtra(Constants.IMAGE_URI, imageUri);
        context.startActivity(intent);
    }
}
