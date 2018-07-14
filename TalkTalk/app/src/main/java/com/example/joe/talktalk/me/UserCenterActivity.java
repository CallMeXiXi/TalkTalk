package com.example.joe.talktalk.me;

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
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SaveCallback;
import com.bumptech.glide.Glide;
import com.example.joe.talktalk.R;
import com.example.joe.talktalk.base.BaseAppCompatActivity;
import com.example.joe.talktalk.common.Constants;
import com.example.joe.talktalk.me.usercenter.EditUserNameActivity;
import com.example.joe.talktalk.me.usercenter.EditUserSignatureActivity;
import com.example.joe.talktalk.me.usercenter.ShowUserImageActivity;
import com.example.joe.talktalk.model.UserInfoModel;
import com.example.joe.talktalk.ui.photocropper.BitmapUtil;
import com.example.joe.talktalk.ui.photocropper.CropHandler;
import com.example.joe.talktalk.ui.photocropper.CropHelper;
import com.example.joe.talktalk.ui.photocropper.CropParams;
import com.example.joe.talktalk.utils.ToastUtil;

import java.io.FileNotFoundException;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2018/7/12 0012.
 */

public class UserCenterActivity extends BaseAppCompatActivity implements CropHandler {

    private static final String TAG = "UserCenterActivity";
    //控件
    private Toolbar mToolbar;
    private CircleImageView civHeader;
    private TextView tvName;
    private TextView tvSex;
    private TextView tvSignature;
    //用户
    private UserInfoModel user;
    //这是头像使用的类、头像路径
    private CropParams cropParams;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_detail_layout);
        cropParams = new CropParams(this);
    }


    @Override
    public void initData() {
        user = AVUser.getCurrentUser(UserInfoModel.class);
    }

    @Override
    public void initView() {
        click(R.id.civ_header);
        click(R.id.rl_edit_user_image);
        click(R.id.rl_edit_user_nickname);
        click(R.id.rl_edit_user_sex);
        click(R.id.rl_edit_user_signature);
        mToolbar = $(R.id.toolbar);
        civHeader = $(R.id.civ_header);
        tvName = $(R.id.tv_user_nickname);
        tvSex = $(R.id.tv_user_sex);
        tvSignature = $(R.id.tv_user_signature);
        //赋值
        initUser();
    }

    /**
     * 给控件赋值
     */
    private void initUser() {
        Glide.with(this).load(user.getHeader()).into(civHeader);
        tvName.setText(user.getNickname());
        if (TextUtils.isEmpty(user.getSignature().toString())) {
            tvSignature.setText("-");
        } else {
            tvSignature.setText(user.getSignature());
        }
        if (user.getSex() == 0) {
            tvSex.setText("保密");
        } else if (user.getSex() == 1) {
            tvSex.setText("男");
        } else if (user.getSex() == 2) {
            tvSex.setText("女");
        }
    }

    @Override
    public void initTitle() {
        mToolbar.setTitle(R.string.personal_info);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void initListener() {
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void doClick(int viewId) {
        switch (viewId) {
            case R.id.civ_header://查看头像大图
                ShowUserImageActivity.launch(this);
                break;
            case R.id.rl_edit_user_image://修改头像
                dialogUserIcon();
                break;
            case R.id.rl_edit_user_nickname://修改昵称
                Intent intentName = new Intent(this, EditUserNameActivity.class);
                intentName.putExtra(Constants.EDIT_NICK_NAME, user.getNickname());
                startActivityForResult(intentName, Constants.EDIT_USER_NAME_CODE);
                break;
            case R.id.rl_edit_user_sex://修改性别
                ToastUtil.showShortToast(this, "rl_edit_user_sex");
                break;
            case R.id.rl_edit_user_signature://修改个签
                Intent intentSignature = new Intent(this, EditUserSignatureActivity.class);
                intentSignature.putExtra(Constants.EDIT_SIGNATURE, user.getSignature());
                startActivityForResult(intentSignature, Constants.EDIT_USER_SIGNATURE_CODE);
                break;
        }
    }

    /**
     * 选择用户头像
     */
    private void dialogUserIcon() {
        cropParams.refreshUri();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("更换头像").setItems(
                getResources().getStringArray(R.array.select_user_icon),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //开启相机
                        if (i == 0) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                int hasPermission = ContextCompat.checkSelfPermission(UserCenterActivity.this, Manifest.permission.CAMERA);
                                if (hasPermission == PackageManager.PERMISSION_GRANTED) {
                                    //打开相机
                                    openCamera();
                                } else {
                                    if (!shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                                        ToastUtil.showShortToast(UserCenterActivity.this, "没有打开相机的权限");
                                    } else {
                                        requestPermissions(new String[]{Manifest.permission.CAMERA}, Constants.PERMISSION_CAMERA);
                                    }
                                }
                            }
                        } else if (i == 1) {
                            //打开相册
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                int hasPermission = ContextCompat.checkSelfPermission(UserCenterActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE);
                                if (hasPermission == PackageManager.PERMISSION_GRANTED) {
                                    //打开相册
                                    openAlbum();
                                } else {
                                    if (!shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                                        ToastUtil.showShortToast(UserCenterActivity.this, "没有打开相册的权限");
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
     * 打开相机
     */
    private void openCamera() {
        cropParams.enable = true;
        cropParams.compress = true;
        Intent intent = CropHelper.buildCameraIntent(cropParams);
        startActivityForResult(intent, CropHelper.REQUEST_CAMERA);
    }

    private void openAlbum() {
        cropParams.enable = true;
        cropParams.compress = true;
        Intent intent = CropHelper.buildGalleryIntent(cropParams);
        startActivityForResult(intent, CropHelper.REQUEST_PICK);
    }

    /**
     * 上传图片
     *
     * @param imagePath 图片路径
     */
    private void uploadUserIcon(String imagePath) {
        if (imagePath != null) {
            String imageType = ".jpg";
            if (imagePath.contains(".png")) {
                imageType = ".jpg";
            }
            try {
                final AVFile file = AVFile.withAbsoluteLocalPath(user.getMobilePhoneNumber() + imageType, imagePath);
                file.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(AVException e) {
                        Log.d(TAG, file.getUrl());//返回一个唯一的 Url 地址

                        //保存网络端的头像路径
                        AVUser.getCurrentUser().saveInBackground(new SaveCallback() {
                            @Override
                            public void done(AVException e) {
                                user.put("header", file.getUrl());
                                user.saveInBackground();
                            }
                        });
                    }
                });
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case Constants.PERMISSION_CAMERA:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openCamera();
                } else {
                    ToastUtil.showShortToast(UserCenterActivity.this, "没有打开相机的权限");
                }
                break;
            case Constants.PERMISSION_ALBUM:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openAlbum();
                } else {
                    ToastUtil.showShortToast(UserCenterActivity.this, "没有打开相册的权限");
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == Constants.EDIT_USER_NAME_CODE) {
                if (data != null) {
                    String nickName = data.getStringExtra(Constants.EDIT_NICK_NAME);
                    tvName.setText(nickName);
                }
            } else if (requestCode == Constants.EDIT_USER_NAME_CODE) {
                if (data != null) {
                    String signature = data.getStringExtra(Constants.EDIT_SIGNATURE);
                    tvSignature.setText(signature);
                }
            } else {
                CropHelper.handleResult(this, requestCode, resultCode, data);
            }
        }
    }

    /**
     * 跳转
     *
     * @param context
     */
    public static void launch(Context context) {
        Intent intent = new Intent(context, UserCenterActivity.class);
        context.startActivity(intent);
    }


    @Override
    public void onPhotoCropped(Uri uri) {
        Log.d(TAG, "onPhotoCropped");
    }

    @Override
    public void onCompressed(Uri uri) {
        Log.d(TAG, "onCompressed");
        //图片地址
        String path = uri.getPath();
        Log.d(TAG, path);
        civHeader.setImageBitmap(BitmapUtil.decodeUriAsBitmap(UserCenterActivity.this, uri));
        //上传头像
        uploadUserIcon(path);
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
