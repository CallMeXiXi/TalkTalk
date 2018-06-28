package com.example.joe.talktalk.account.register.fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.cengalabs.flatui.views.FlatButton;
import com.example.joe.talktalk.R;
import com.example.joe.talktalk.account.register.RegisterActivity;
import com.example.joe.talktalk.base.BaseFragment;
import com.example.joe.talktalk.common.Constants;
import com.example.joe.talktalk.ui.photocropper.BitmapUtil;
import com.example.joe.talktalk.ui.photocropper.CropHandler;
import com.example.joe.talktalk.ui.photocropper.CropHelper;
import com.example.joe.talktalk.ui.photocropper.CropParams;
import com.example.joe.talktalk.utils.ToastUtil;

import java.security.Permission;
import java.security.Permissions;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2018/6/28 0028.
 */

public class Register3Fragment extends BaseFragment implements CropHandler {

    private static final String TAG = "Register3Fragment";
    //页面2传来的手机号码及密码
    private String phoneNumber;
    private String password;
    //上下文
    private Context mContext;
    //控件
    private CircleImageView civUserIcon;
    private EditText mEtName;
    private EditText mEtSignature;
    private Spinner mSpinner;
    //头像图片选择、路径
    private CropParams cropParams;
    private String path;

    private static Register3Fragment instance;

    public static Register3Fragment getInstance(String phoneNumber, String password) {
        if (instance == null) {
            synchronized (Register3Fragment.class) {
                if (instance == null) {
                    instance = new Register3Fragment();
                    Bundle bundle = new Bundle();
                    bundle.putString(Constants.PHONE_NUMBER, phoneNumber);
                    bundle.putString(Constants.PHONE_PASSWORD, password);
                    instance.setArguments(bundle);
                }
            }
        }
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register_3_layout, container, false);
        init(view);
        initSpinner();
        mContext = getActivity();
        cropParams = new CropParams(mContext);
        return view;
    }

    @Override
    public void initView(View view) {
        click(view, R.id.ll_pick_user_icon);
        click(view, R.id.fb_save);
        civUserIcon = $(view, R.id.civ_user_icon);
        mEtName = $(view, R.id.et_nickname);
        mEtSignature = $(view, R.id.et_user_signature);
        mSpinner = $(view, R.id.s_user_sex);
    }

    @Override
    public void initData() {
        phoneNumber = getArguments().getString(Constants.PHONE_NUMBER);
        password = getArguments().getString(Constants.PHONE_PASSWORD);
    }

    @Override
    public void initListener() {

    }

    @Override
    public void doClick(int viewId) {
        switch (viewId) {
            case R.id.ll_pick_user_icon://选择头像
                getUserIcon();
                break;
            case R.id.fb_save://保存按钮

                break;
        }
    }

    /**
     * 初始化性别下拉
     */
    private void initSpinner() {
        // 建立数据源
        final String[] sexArray = getResources().getStringArray(R.array.sex_array);
        // 建立Adapter并且绑定数据源
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, sexArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //绑定 Adapter到控件
        mSpinner.setAdapter(adapter);
        mSpinner.setSelection(0, true);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, position + "");
                int itemId = (int) parent.getItemAtPosition(position);
                Log.d(TAG, itemId + "");
                mSpinner.setSelection(itemId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    /**
     * 获取用户头像
     */
    private void getUserIcon() {
        cropParams.refreshUri();
        AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
        dialog.setTitle(R.string.pick_user_icon).setItems(
                getResources().getStringArray(R.array.select_user_icon),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {//拍照
                            int hasPermission = ContextCompat.checkSelfPermission(mContext, Manifest.permission.CAMERA);
                            if (hasPermission == PackageManager.PERMISSION_GRANTED) {
                                //有权限，打开相机
                                openCamera();
                            } else {
                                //没权限，去打开权限
                                if (!shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                                    ToastUtil.showShortToast(mContext, "没有打开相机的权限");
                                } else {
                                    requestPermissions(new String[]{Manifest.permission.CAMERA}, Constants.PERMISSION_CAMERA);
                                }
                            }
                        } else {//相册选择
                            int hasPermission = ContextCompat.checkSelfPermission(mContext, Manifest.permission.READ_EXTERNAL_STORAGE);
                            if (hasPermission == PackageManager.PERMISSION_GRANTED) {
                                //有权限，打开相册
                                openAlbum();
                            } else {
                                //没有权限，提示打开权限
                                if (!shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                                    ToastUtil.showShortToast(mContext, "没有打开相册的权限");
                                } else {
                                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, Constants.PERMISSION_ALBUM);
                                }
                            }
                        }
                    }
                }).show();
    }

    /**
     * 权限判断
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case Constants.PERMISSION_CAMERA:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //有权限，打开相机
                    openCamera();
                } else {
                    ToastUtil.showShortToast(mContext, "没有打开相机的权限");
                }
                break;
            case Constants.PERMISSION_ALBUM:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //有权限，打开相机
                    openAlbum();
                } else {
                    ToastUtil.showShortToast(mContext, "没有打开相册的权限");
                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        CropHelper.handleResult(this, requestCode, resultCode, data);
    }

    /**
     * 打开相册
     */
    private void openAlbum() {
        cropParams.enable = true;
        cropParams.compress = true;
        Intent intent = CropHelper.buildCameraIntent(cropParams);
        startActivityForResult(intent, CropHelper.REQUEST_PICK);
    }

    /**
     * 打开相机
     */
    private void openCamera() {
        cropParams.enable = true;
        cropParams.compress = true;
        Intent intent = CropHelper.buildCameraIntent(cropParams);
        startActivityForResult(intent, CropHelper.REQUEST_CAMERA);
    }


    @Override
    public void onPhotoCropped(Uri uri) {
        Log.d(TAG, "onPhotoCropped");

    }

    @Override
    public void onCompressed(Uri uri) {
        Log.d(TAG, "onCompressed");
        //图片地址
        path = uri.getPath();
        Log.d(TAG, path);
        civUserIcon.setImageBitmap(BitmapUtil.decodeUriAsBitmap(mContext, uri));
    }

    @Override
    public void onCancel() {
        Log.d(TAG, "onCancel");
    }

    @Override
    public void onFailed(String message) {
        Log.d(TAG, "onFailed");
        showShortToast(message);
    }

    @Override
    public void handleIntent(Intent intent, int requestCode) {
        Log.d(TAG, "handleIntent");
        startActivityForResult(intent, requestCode);
    }

    @Override
    public CropParams getCropParams() {
        Log.d(TAG, "getCropParams");
        return cropParams;
    }

    @Override
    public void onDestroy() {
        CropHelper.clearCacheDir();
        super.onDestroy();
    }
}
