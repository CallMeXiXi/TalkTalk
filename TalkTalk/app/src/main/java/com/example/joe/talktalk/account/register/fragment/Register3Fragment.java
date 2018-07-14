package com.example.joe.talktalk.account.register.fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.SaveCallback;
import com.avos.avoscloud.SignUpCallback;
import com.example.joe.talktalk.MainActivity;
import com.example.joe.talktalk.R;
import com.example.joe.talktalk.base.BaseFragment;
import com.example.joe.talktalk.common.Constants;
import com.example.joe.talktalk.model.UserInfoModel;
import com.example.joe.talktalk.ui.photocropper.BitmapUtil;
import com.example.joe.talktalk.ui.photocropper.CropHandler;
import com.example.joe.talktalk.ui.photocropper.CropHelper;
import com.example.joe.talktalk.ui.photocropper.CropParams;
import com.example.joe.talktalk.utils.ToastUtil;

import java.io.FileNotFoundException;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2018/6/28 0028.
 */

public class Register3Fragment extends BaseFragment implements CropHandler {

    private static final String TAG = "Register3Fragment";
    //页面2传来的手机号码及密码、邮箱
    private String phoneNumber;
    private String password;
    private String email;
    //上下文
    private Context mContext;
    //控件
    private CircleImageView civUserIcon;
    private EditText mEtName;
    private EditText mEtSignature;
    private EditText mEtEamil;
    private Spinner mSpinner;
    //头像图片选择、本地路径
    private CropParams cropParams;
    private String path;
    private String imagePath;//上传图片后网络图片路径

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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register_3_layout, container, false);
        init(view);
        initSpinner();
        cropParams = new CropParams(mContext);
        return view;
    }

    @Override
    public void initView(View view) {
        click(view, R.id.ll_pick_user_icon);
        click(view, R.id.btn_save);
        civUserIcon = $(view, R.id.civ_user_icon);
        mEtName = $(view, R.id.et_user_nickname);
        mEtSignature = $(view, R.id.et_user_signature);
        mEtEamil = $(view, R.id.et_user_email);
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
            case R.id.btn_save://保存按钮
                showLoadingDialog("注册中..");
                registerUser();//去注册
                break;
        }
    }

    /**
     * 注册用户
     */
    private void registerUser() {
        email = mEtEamil.getText().toString();
        if (TextUtils.isEmpty(email)) {
            showShortToast("邮箱地址不能为空");
        }
        String imageType = ".jpg";
        if (path.contains(".png")) {
            imageType = ".png";
        }
        try {
            //上传头像
            final AVFile file = AVFile.withAbsoluteLocalPath(phoneNumber + imageType, path);
            file.saveInBackground(new SaveCallback() {
                @Override
                public void done(AVException e) {
                    Log.d(TAG, file.getUrl());
                    imagePath = file.getUrl();

                    UserInfoModel user = new UserInfoModel();// 新建 AVUser 对象实例
                    user.setUsername(phoneNumber);
                    user.setNickname(mEtName.getText().toString());
                    user.setMobilePhoneNumber(phoneNumber);
                    user.setPassword(password);
                    user.setEmail(email);
                    user.setSignature(mEtSignature.getText().toString());
                    user.setSex(mSpinner.getSelectedItemPosition());
                    user.setHeader(imagePath);
                    user.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(AVException e) {
                            if (e == null) {
                                showShortToast("注册成功");
                                // 注册成功，把用户对象赋值给当前用户 AVUser.getCurrentUser()
                                MainActivity.launch(getActivity());
                                getActivity().finish();
                            } else if (e.getCode() == 202) {
                                showShortToast("该用户名已存在");
                            } else if (e.getCode() == 214) {
                                showShortToast("该用户已存在，请登录");
                            } else {
                                Log.d(TAG, e.getCause().toString());
                            }
                        }
                    });
                }
            });
        } catch (FileNotFoundException e) {
            e.printStackTrace();
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
                int itemId = (int) parent.getItemIdAtPosition(position);
                Log.d(TAG, itemId + "");
                mSpinner.setSelection(itemId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    /**
     * 上传用户头像
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
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
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
                            }
                        } else {//相册选择
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
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
        Intent intent = CropHelper.buildGalleryIntent(cropParams);
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
