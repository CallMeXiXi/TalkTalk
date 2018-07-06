package com.example.joe.talktalk.im.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.messages.AVIMTextMessage;
import com.example.joe.talktalk.R;
import com.example.joe.talktalk.im.activity.ChatActivity;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2018/7/6 0006.
 */

public class TalkAdapter extends RecyclerView.Adapter<TalkAdapter.TalkViewHolder> {

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
            holder.tvName.setText(model.getName());
            if (model.getLastMessage() instanceof AVIMTextMessage) {
                content = ((AVIMTextMessage) model.getLastMessage()).getText();
            }
            holder.tvContent.setText(content);
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


    public class TalkViewHolder extends RecyclerView.ViewHolder {

        CircleImageView civHeader;
        TextView tvName;
        TextView tvContent;

        public TalkViewHolder(View itemView) {
            super(itemView);
            civHeader = itemView.findViewById(R.id.civ_header);
            tvName = itemView.findViewById(R.id.tv_name);
            tvContent = itemView.findViewById(R.id.tv_content);
        }
    }
}
