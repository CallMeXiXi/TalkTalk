package com.example.joe.talktalk.im.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.messages.AVIMTextMessage;
import com.example.joe.talktalk.R;
import com.example.joe.talktalk.im.activity.ChatActivity;

import java.text.SimpleDateFormat;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2018/7/6 0006.
 */

public class TalkAdapter extends RecyclerView.Adapter<TalkAdapter.TalkViewHolder> {

    private int unreadCount;
    private Context context;
    private List<AVIMConversation> convs;

    public TalkAdapter(Context context, List<AVIMConversation> convs) {
        this.context = context;
        this.convs = convs;
    }

    @NonNull
    @Override
    public TalkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_main_talk_item_layout, parent, false);
        return new TalkViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TalkViewHolder holder, int position) {
        final AVIMConversation model = convs.get(position);
        if (model != null) {
            String content = "";
            if (model.getLastMessage() instanceof AVIMTextMessage) {
                content = ((AVIMTextMessage) model.getLastMessage()).getText();
            }

            //内容
            if (!TextUtils.isEmpty(content)) {
                holder.tvContent.setText(content);
            } else {
                holder.tvContent.setVisibility(View.GONE);
            }
            //姓名
            holder.tvName.setText(model.getName());
            //时间
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
//            String time = sdf.format(model.getLastMessage().getTimestamp());
//            holder.tvTime.setText(time);
            //未读数
            if (unreadCount == 0) {
                holder.tvUnread.setVisibility(View.GONE);
            } else {
                holder.tvUnread.setVisibility(View.VISIBLE);
                holder.tvUnread.setText(unreadCount);
            }
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChatActivity.launch(context, model.getName(), model.getConversationId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return convs.size();
    }

    /**
     * 设置未读数
     *
     * @param unreadCount
     */
    public void setUnreadCount(int unreadCount) {
        this.unreadCount = unreadCount;
    }

    public class TalkViewHolder extends RecyclerView.ViewHolder {

        CircleImageView civHeader;
        TextView tvName;
        TextView tvContent;
        TextView tvTime;
        TextView tvUnread;

        public TalkViewHolder(View itemView) {
            super(itemView);
            civHeader = itemView.findViewById(R.id.civ_header);
            tvName = itemView.findViewById(R.id.tv_name);
            tvContent = itemView.findViewById(R.id.tv_content);
            tvTime = itemView.findViewById(R.id.tv_time);
            tvUnread = itemView.findViewById(R.id.tv_unread);
        }
    }
}
