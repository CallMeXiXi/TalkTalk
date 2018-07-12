package com.example.joe.talktalk.im.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.avos.avoscloud.AVUser;
import com.bumptech.glide.Glide;
import com.example.joe.talktalk.R;
import com.example.joe.talktalk.base.BaseFragment;
import com.example.joe.talktalk.im.AVImClientManager;
import com.example.joe.talktalk.me.SettingActivity;
import com.example.joe.talktalk.me.UserCenterActivity;
import com.example.joe.talktalk.model.UserInfoModel;
import com.example.joe.talktalk.utils.ToastUtil;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2018/7/2 0002.
 */

public class MeFragment extends BaseFragment {

    //上下文
    private Context context;
    //控件
    private TextView tvName;
    private CircleImageView civHeader;
    //用户类
    private UserInfoModel user;

    private static MeFragment instance;

    public static MeFragment getInstance() {
        if (instance == null) {
            synchronized (MeFragment.class) {
                if (instance == null) {
                    instance = new MeFragment();
                }
            }
        }
        return instance;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_me_layout, container, false);
        init(view);
        return view;
    }

    @Override
    public void initView(View view) {
        click(view, R.id.rl_me);
        click(view, R.id.fl_qccode);
        click(view, R.id.ll_setting);
        tvName = $(view, R.id.tv_name);
        civHeader = $(view, R.id.civ_header);

        tvName.setText(user.getNickname());
        Glide.with(context).load(user.getHeader()).into(civHeader);
    }

    @Override
    public void initData() {
        user = AVUser.getCurrentUser(UserInfoModel.class);
    }

    @Override
    public void initListener() {

    }

    @Override
    public void doClick(int viewId) {
        switch (viewId) {
            case R.id.rl_me:
                UserCenterActivity.launch(context);
                break;
            case R.id.fl_qccode:
                ToastUtil.showShortToast(context, "二维码");
                break;
            case R.id.ll_setting:
                SettingActivity.launch(context);
                break;
        }
    }
}
