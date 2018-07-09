package com.example.joe.talktalk.im.charFragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.AVIMMessage;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCallback;
import com.avos.avoscloud.im.v2.callback.AVIMMessagesQueryCallback;
import com.avos.avoscloud.im.v2.messages.AVIMTextMessage;
import com.example.joe.talktalk.R;
import com.example.joe.talktalk.base.BaseFragment;
import com.example.joe.talktalk.common.Constants;
import com.example.joe.talktalk.im.activity.ChatActivity;
import com.example.joe.talktalk.im.adapter.ChatAdapter;
import com.example.joe.talktalk.utils.ToastUtil;

import java.util.List;

/**
 * Created by Administrator on 2018/7/5 0005.
 */

public class ChatFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, TextWatcher {

    private static final String TAG = "ChatFragment";

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
    private LinearLayoutManager manager;
    //适配器
    private ChatAdapter mAdapter;
    //广播
    private ReceiveMessageBroadCastReceive receive;

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
        //注册广播
        receive = new ReceiveMessageBroadCastReceive();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constants.HANDLER_MESSAGE);
        mContext.registerReceiver(receive, intentFilter);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.fragment_char_layout, container, false);
        init(view);
        return view;
    }

    @Override
    public void initView(View view) {
        srlRefresh = $(view, R.id.srl_chat);
        srlRefresh.setColorSchemeResources(R.color.red, R.color.yellow, R.color.blue);
        rvChat = $(view, R.id.rv_chat);
        etMessage = $(view, R.id.et_message);
        ivSpeech = $(view, R.id.iv_speech);
        ivSend = $(view, R.id.iv_send);
        click(view, R.id.iv_speech);
        click(view, R.id.iv_send);

        manager = new LinearLayoutManager(mContext);
        rvChat.setLayoutManager(manager);
        mAdapter = new ChatAdapter(mContext);
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
                if (ivSend.isEnabled()) {
                    sendMessage();
                }
                break;
        }
    }

    /**
     * 发送消息
     */
    private void sendMessage() {
        // 发送消息
        if (conversation != null) {
            AVIMTextMessage msg = new AVIMTextMessage();
            msg.setText(etMessage.getText().toString());
            mAdapter.addMessage(msg);
            etMessage.setText("");
            mAdapter.notifyDataSetChanged();
            conversation.sendMessage(msg, new AVIMConversationCallback() {

                @Override
                public void done(AVIMException e) {
                    if (e == null) {
                        Log.d(TAG, "发送成功！");
                        mAdapter.notifyDataSetChanged();
                        scrollButton();
                    }
                }
            });
        }
    }

    /**
     * 上拉加载聊天记录
     */
    @Override
    public void onRefresh() {
        conversation.queryMessages(new AVIMMessagesQueryCallback() {
            @Override
            public void done(List<AVIMMessage> messages, AVIMException e) {
                if (e == null) {
                    if (messages != null && !messages.isEmpty()) {
                        Log.d(TAG, "获取到 " + messages.size() + " 条messages ");

                        //返回的消息一定是时间增序排列，也就是最早的消息一定是第一个
                        AVIMMessage oldestMessage = messages.get(0);

                        conversation.queryMessages(oldestMessage.getMessageId(), oldestMessage.getTimestamp(), 20,
                                new AVIMMessagesQueryCallback() {
                                    @Override
                                    public void done(List<AVIMMessage> msgs, AVIMException e) {
                                        if (e == null) {
                                            //查询成功返回
                                            Log.d(TAG, "got " + msgs.size() + " messages ");

                                            mAdapter.addMessagesList(msgs);
                                            mAdapter.notifyDataSetChanged();
                                            srlRefresh.setRefreshing(false);
                                        }
                                    }
                                });
                    }
                }
            }
        });
    }

    /**
     * @param conversation
     */
    public void setAVImConversation(AVIMConversation conversation) {
        this.conversation = conversation;
        freshMessage();
    }

    /**
     * 获取历史数据
     */
    private void freshMessage() {
        if (null != conversation) {
            conversation.queryMessages(20, new AVIMMessagesQueryCallback() {
                @Override
                public void done(List<AVIMMessage> list, AVIMException e) {
                    mAdapter.setMessage(list);
                    rvChat.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                    scrollButton();
                }
            });
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mContext.unregisterReceiver(receive);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (TextUtils.isEmpty(charSequence)) {
            ivSend.setImageResource(R.mipmap.un_send);
            ivSend.setEnabled(false);
        } else if (charSequence.length() > 0 && charSequence.length() < 200) {
            etMessage.setSelection(charSequence.length());
            ivSend.setImageResource(R.mipmap.send);
            ivSend.setEnabled(true);
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    /**
     * 滑动到最新一条
     */
    private void scrollButton() {
        manager.scrollToPositionWithOffset(mAdapter.getItemCount() - 1, 0);
    }

    /**
     * 接收信息的广播
     * Created by Administrator on 2018/7/6 0006.
     */
    public class ReceiveMessageBroadCastReceive extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Constants.HANDLER_MESSAGE)) {
                AVIMMessage message = (AVIMMessage) intent.getSerializableExtra("message");
                String conversationId = intent.getStringExtra("conversation_id");
                if (conversation != null
                        && conversation.getConversationId().equals(conversationId)) {
                    mAdapter.addMessage(message);
                    mAdapter.notifyDataSetChanged();
                    scrollButton();
                }
            }
        }
    }
}
