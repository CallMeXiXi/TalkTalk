package com.example.joe.talktalk.im.charFragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.avos.avoscloud.im.v2.AVIMConversation;
import com.example.joe.talktalk.R;
import com.example.joe.talktalk.base.BaseFragment;
import com.example.joe.talktalk.common.Constants;
import com.example.joe.talktalk.im.activity.ChatActivity;
import com.example.joe.talktalk.utils.ToastUtil;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/7/5 0005.
 */

public class ChatFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, TextWatcher {

    //聊天信息
    private String msg;
    private AVIMConversation conversation;
    //上下文
    private Context mContext;
    private ChatActivity mActivity;
    //控件
    private SwipeRefreshLayout srlRefresh;
    private RecyclerView rvChat;
    private EditText etMessage;
    private ImageView ivSpeech;
    private ImageView ivSend;

    private static ChatFragment instance;

    public static ChatFragment getInstance() {
        if (instance == null) {
            synchronized (ChatFragment.class) {
                if (instance == null) {
                    instance = new ChatFragment();
                }
            }
        }
        return instance;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        mActivity = (ChatActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.char_fragment_layout, container, false);
        init(view);
        return view;
    }

    @Override
    public void initView(View view) {
        srlRefresh = $(view, R.id.srl_chat);
        rvChat = $(view, R.id.rv_chat);
        etMessage = $(view, R.id.et_message);
        ivSpeech = $(view, R.id.iv_speech);
        ivSend = $(view, R.id.iv_send);
        click(view, R.id.iv_speech);
        click(view, R.id.iv_send);
    }

    @Override
    public void initData() {
    }

    @Override
    public void initListener() {
        if (!srlRefresh.isRefreshing()) {
            srlRefresh.setOnRefreshListener(this);
        }
        etMessage.addTextChangedListener(this);
    }

    @Override
    public void doClick(int viewId) {
        switch (viewId) {
            case R.id.iv_speech://语音
                ToastUtil.showShortToast(mContext, "还没做到语音拉");
                break;
            case R.id.iv_send://发送

                break;
        }
    }

    /**
     * 上拉加载聊天记录
     */
    @Override
    public void onRefresh() {

    }

    /**
     * @param conversation
     */
    public void setAVImConversation(AVIMConversation conversation) {
        this.conversation = conversation;
    }


    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (TextUtils.isEmpty(charSequence)) {
            ivSend.setImageResource(R.mipmap.un_send);
        } else {
            ivSend.setImageResource(R.mipmap.send);
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}
