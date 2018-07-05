package com.example.joe.talktalk.im.adapter.chatViewHolder;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.avos.avoscloud.im.v2.AVIMMessage;
import com.avos.avoscloud.im.v2.messages.AVIMTextMessage;
import com.example.joe.talktalk.R;

import java.text.SimpleDateFormat;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2018/7/5 0005.
 */

public class ChatLeftViewHolder extends ChatCommonViewHolder<AVIMMessage> {

    private TextView tvTime;
    private ProgressBar pbProgress;
    private FrameLayout flContainer;
    private ImageView ivError;
    private CircleImageView civHeader;
    private TextView tvContent;

    public ChatLeftViewHolder(Context context, ViewGroup parent) {
        super(context, parent, R.layout.item_chat_left_layout);
    }

    @Override
    public void init() {
        tvTime = itemView.findViewById(R.id.tv_time);
        pbProgress = itemView.findViewById(R.id.pb_progressbar);
        flContainer = itemView.findViewById(R.id.fl_container);
        ivError = itemView.findViewById(R.id.iv_error);
        civHeader = itemView.findViewById(R.id.civ_header);
        tvContent = itemView.findViewById(R.id.tv_content);
    }

    @Override
    public void initData(AVIMMessage avimMessage) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String date = sdf.format(avimMessage.getTimestamp());

        String content = "暂不支持此类型";
        if (avimMessage instanceof AVIMTextMessage) {
            content = avimMessage.getContent();
        }
        tvTime.setText(date);
        tvContent.setText(content);
    }

    /**
     * 是否显示时间
     *
     * @param isShow
     */
    public void setShowTime(boolean isShow) {
        tvTime.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }
}