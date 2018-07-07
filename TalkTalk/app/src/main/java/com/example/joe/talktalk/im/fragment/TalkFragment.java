package com.example.joe.talktalk.im.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMConversationsQuery;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.callback.AVIMConversationQueryCallback;
import com.example.joe.talktalk.MainActivity;
import com.example.joe.talktalk.R;
import com.example.joe.talktalk.base.BaseFragment;
import com.example.joe.talktalk.im.AVImClientManager;
import com.example.joe.talktalk.im.adapter.TalkAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/7/2 0002.
 */

public class TalkFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    //控件
    private SwipeRefreshLayout srlTalk;
    private RecyclerView rvTalk;
    //适配器
    private TalkAdapter mAdapter;
    //数据源
    private List<AVIMConversation> convsList;
    //上下文
    private Context mContext;
    private MainActivity mActivity;


    private static TalkFragment instance;

    public static TalkFragment getInstance() {
        if (instance == null) {
            synchronized (TalkFragment.class) {
                if (instance == null) {
                    instance = new TalkFragment();
                }
            }
        }
        return instance;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
        mActivity = (MainActivity) getContext();

        convsList = new ArrayList<>();
        mAdapter = new TalkAdapter(mContext, convsList);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_talk_layout, container, false);
        init(view);
        return view;
    }

    @Override
    public void initView(View view) {
        srlTalk = $(view, R.id.srl_chat_collection);
        srlTalk.setColorSchemeResources(R.color.red, R.color.yellow, R.color.blue);
        rvTalk = $(view, R.id.rv_chat_collection);
        //添加Android自带的分割线
        rvTalk.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        rvTalk.setAdapter(mAdapter);
    }

    @Override
    public void initData() {
        AVIMConversationsQuery query = AVImClientManager.getInstance().getAvimClient().getConversationsQuery();
        query.findInBackground(new AVIMConversationQueryCallback() {
            @Override
            public void done(List<AVIMConversation> convs, AVIMException e) {
                if (e == null) {
                    if (convs != null) {
                        //convs就是获取到的conversation列表
                        //注意：按每个对话的最后更新日期（收到最后一条消息的时间）倒序排列
                        convsList.clear();
                        convsList.addAll(convs);
                        mAdapter.notifyDataSetChanged();
                        srlTalk.setRefreshing(false);
                    }
                }
            }
        });
    }

    @Override
    public void initListener() {
        if (!srlTalk.isRefreshing()) {
            srlTalk.setOnRefreshListener(this);
        }
    }

    @Override
    public void doClick(int viewId) {

    }

    /**
     * 下拉刷新
     */
    @Override
    public void onRefresh() {
        initData();
    }
}
