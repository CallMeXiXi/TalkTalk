package com.example.joe.talktalk.im.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;

import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMConversationsQuery;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCallback;
import com.avos.avoscloud.im.v2.callback.AVIMConversationQueryCallback;
import com.example.joe.talktalk.R;
import com.example.joe.talktalk.account.LoginActivity;
import com.example.joe.talktalk.base.BaseAppCompatActivity;
import com.example.joe.talktalk.common.Constants;
import com.example.joe.talktalk.im.AVImClientManager;
import com.example.joe.talktalk.im.charFragment.ChatFragment;
import com.example.joe.talktalk.model.UserInfoModel;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2018/7/5 0005.
 */

public class ChatActivity extends BaseAppCompatActivity {

    //好友名称、对话ID、聊天信息
    private String name;
    private String conversationId;
    //控件
    private Toolbar mToolbar;
    private FragmentTransaction mTransaction;
    //IM
    private AVIMConversation conversation;

    private ChatFragment chatFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_layout);
    }

    @Override
    public void initData() {
        name = getIntent().getStringExtra(Constants.NICK_NAME);
        conversationId = getIntent().getStringExtra(Constants.CONVERSATION_ID);
    }

    @Override
    public void initView() {
        mToolbar = $(R.id.toolbar);
        chatFragment = ChatFragment.getInstance();
        addFragment(chatFragment);
        //添加对话
        getConversation(conversationId);
        queryConversation(conversationId);
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

    }


    /**
     * 添加对话,根据conversationId去拿缓存数据，如果没有改缓存对话，新增一条对话
     */
    private void getConversation(String conversationId) {
        if (TextUtils.isEmpty(conversationId)) {
            throw new IllegalArgumentException("conversationId can not be null");
        }

        AVIMClient client = AVImClientManager.getInstance().getAvimClient();
        if (null != client) {
            conversation = client.getConversation(conversationId);
        } else {
            UserInfoModel.getCurrentUser().logOut();
            LoginActivity.launch(this);
            this.finish();
            showLongToast("Please try Login!");
        }
    }

    /**
     * 查找conversation
     *
     * @param conversationId
     */
    private void queryConversation(String conversationId) {
        AVIMClient client = AVImClientManager.getInstance().getAvimClient();
        AVIMConversationsQuery conversationQuery = client.getConversationsQuery();
        conversationQuery.whereEqualTo("objectId", conversationId);
//        conversationQuery.containsMembers(Arrays.asList(AVImClientManager.getInstance().getClientId()));
        conversationQuery.findInBackground(new AVIMConversationQueryCallback() {
            @Override
            public void done(List<AVIMConversation> list, AVIMException e) {
                if (filterException(e)) {
                    if (null != list && list.size() > 0) {
                        //list.get(0) 就是想要的conversation
                        chatFragment.setAVImConversation(list.get(0));
                    } else {
                        joinConversation();
                    }
                }
            }
        });
    }

    /**
     * 新增conversation
     */
    private void joinConversation() {
        conversation.join(new AVIMConversationCallback() {
            @Override
            public void done(AVIMException e) {
                if (e == null) {
                    //加入成功
                    chatFragment.setAVImConversation(conversation);
                }
            }
        });
    }

    /**
     * 添加聊天界面
     *
     * @param f
     */
    private void addFragment(Fragment f) {
        mTransaction = getSupportFragmentManager().beginTransaction();
        mTransaction.replace(R.id.ll_char_container, f);
        mTransaction.commit();
    }

    public static void launch(Context context, String name, String conversationId) {
        Intent intent = new Intent(context, ChatActivity.class);
        intent.putExtra(Constants.NICK_NAME, name);
        intent.putExtra(Constants.CONVERSATION_ID, conversationId);
        context.startActivity(intent);
    }
}
