package com.example.joe.talktalk.base;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.joe.talktalk.ui.LoadingDialog;

/**
 * Created by Administrator on 2018/6/28 0028.
 */

public abstract class BaseFragment extends Fragment implements View.OnClickListener {

    private LoadingDialog dialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
            Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * 初始化操作，子类一定要调用，否则无法完成初始化
     *
     * @param view
     */
    public void init(View view) {
        initData();
        initView(view);
        initListener();
    }

    public abstract void initView(View view);

    public abstract void initData();

    public abstract void initListener();

    public <T extends View> T $(View view, @IdRes int id) {
        return (T) view.findViewById(id);
    }


    public void click(View view, @IdRes int id) {
        view.findViewById(id).setOnClickListener(this);
    }

    public void click(View v) {
        v.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        doClick(v.getId());
    }

    public abstract void doClick(int viewId);

    public void showShortToast(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    public void showShortToast(@StringRes int resId) {
        Toast.makeText(getActivity(), resId, Toast.LENGTH_SHORT).show();
    }

    public void showLongToast(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
    }

    public void showLongToast(@StringRes int resId) {
        Toast.makeText(getActivity(), resId, Toast.LENGTH_LONG).show();
    }

    public void showLoadingDialog() {
        if (dialog == null) {
            dialog = new LoadingDialog(getActivity());
        }
        dialog.show();
    }

    public void showLoadingDialog(String message) {
        if (dialog == null) {
            dialog = new LoadingDialog(getActivity());
        }
        dialog.setMessage(message);
        dialog.show();
    }

    public void dismissLoadingDialog() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    public boolean filterException(Exception e) {
        if (e != null) {
            e.printStackTrace();
            showLongToast(e.getMessage());
            return false;
        } else {
            return true;
        }
    }
}
