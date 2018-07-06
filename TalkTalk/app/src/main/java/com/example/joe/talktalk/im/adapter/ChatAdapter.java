package com.example.joe.talktalk.im.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMMessage;
import com.example.joe.talktalk.im.AVImClientManager;
import com.example.joe.talktalk.im.adapter.chatViewHolder.ChatCommonViewHolder;
import com.example.joe.talktalk.im.adapter.chatViewHolder.ChatLeftViewHolder;
import com.example.joe.talktalk.im.adapter.chatViewHolder.ChatRightViewHolder;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatCommonViewHolder<AVIMMessage>> {

    private static final int ITEM_LEFT = 100;
    private static final int ITEM_RIGHT = 101;

    //显示时间为10分钟
    private static final long time = 10 * 60 * 1000;

    private Context context;
    private List<AVIMMessage> lists = new ArrayList<>();

    public ChatAdapter(Context context) {
        this.context = context;
    }

    public void addMessage(AVIMMessage message) {
        this.lists.addAll(Arrays.asList(message));
    }

    public void setMessage(List<AVIMMessage> list) {
        lists.clear();
        lists.addAll(list);
    }

    @Override
    public int getItemViewType(int position) {
        AVIMMessage message = lists.get(position);
        if (message.getFrom().equals(AVImClientManager.getInstance().getClientId())) {
            return ITEM_RIGHT;
        } else {
            return ITEM_LEFT;
        }
    }

    @NonNull
    @Override
    public ChatCommonViewHolder<AVIMMessage> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == ITEM_LEFT) {
            return new ChatLeftViewHolder(context, parent);
        } else {
            return new ChatRightViewHolder(context, parent);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ChatCommonViewHolder<AVIMMessage> holder, int position) {
        AVIMMessage message = lists.get(position);
        if (holder instanceof ChatLeftViewHolder) {
            ChatLeftViewHolder h = (ChatLeftViewHolder) holder;
            h.setShowTime(isShowTime(position));
            h.setData(message);
        } else if (holder instanceof ChatRightViewHolder) {
            ChatRightViewHolder h = (ChatRightViewHolder) holder;
            h.setShowTime(isShowTime(position));
            h.setData(message);
        }
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    /**
     * 判断是否显示时间
     *
     * @param position
     * @return
     */
    public boolean isShowTime(int position) {
        if (position == 0) {
            return true;
        }

        long beforeItem = lists.get(position - 1).getTimestamp();
        long curItem = lists.get(position).getTimestamp();
        return curItem - beforeItem > time;
    }
}
