package com.example.joe.talktalk.im.adapter.chatViewHolder;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.im.v2.AVIMMessage;
import com.avos.avoscloud.im.v2.messages.AVIMTextMessage;
import com.bumptech.glide.Glide;
import com.example.joe.talktalk.R;
import com.example.joe.talktalk.model.UserInfoModel;

import java.text.SimpleDateFormat;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2018/7/5 0005.
 */

public class ChatRightViewHolder extends ChatCommonViewHolder<AVIMMessage> {

    private Context context;
    private TextView tvTime;
    private ProgressBar pbProgress;
    private FrameLayout flContainer;
    private ImageView ivError;
    private CircleImageView civHeader;
    private TextView tvContent;

    public ChatRightViewHolder(Context context, ViewGroup parent) {
        super(context, parent, R.layout.item_chat_right_layout);
        this.context = context;
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
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = sdf.format(avimMessage.getTimestamp());

        String content = "暂不支持此类型";
        if (avimMessage instanceof AVIMTextMessage) {
            content = ((AVIMTextMessage) avimMessage).getText();
        }
        tvTime.setText(date);
        tvContent.setText(content);
        String header = AVUser.getCurrentUser(UserInfoModel.class).getHeader();
        Glide.with(context).load(header).into(civHeader);
//        civHeader.setImageURI(Uri.parse(header));

        //显示，隐藏是否已经发送成功的按钮
        if (avimMessage.getMessageStatus() == AVIMMessage.AVIMMessageStatus.AVIMMessageStatusFailed) {
            pbProgress.setVisibility(View.GONE);
            flContainer.setVisibility(View.VISIBLE);
            ivError.setVisibility(View.VISIBLE);
        } else if (avimMessage.getMessageStatus() == AVIMMessage.AVIMMessageStatus.AVIMMessageStatusSending) {
            pbProgress.setVisibility(View.VISIBLE);
            flContainer.setVisibility(View.VISIBLE);
            ivError.setVisibility(View.GONE);
        } else if (avimMessage.getMessageStatus() == AVIMMessage.AVIMMessageStatus.AVIMMessageStatusSent) {
            flContainer.setVisibility(View.GONE);
        }
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