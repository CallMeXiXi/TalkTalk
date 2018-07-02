package com.example.joe.talktalk.model;

import android.os.Parcel;

import com.avos.avoscloud.AVUser;

/**
 * Created by Administrator on 2018/6/28 0028.
 */

public class UserInfoModel extends AVUser {
    public static final Creator CREATOR = AVObjectCreator.instance;

    public UserInfoModel() {
        super();
    }

    public UserInfoModel(Parcel in) {
        super(in);
    }

    public String getHeader() {
        return getString("header");
    }

    public void setHeader(String header) {
        put("header", header);
    }

    public String getNickname() {
        return getString("nickname");
    }

    public void setNickname(String nickname) {
        put("nickname", nickname);
    }

    //0为保密，1为男，2为女
    public int getSex() {
        return getInt("sex");
    }

    public void setSex(int sex) {
        put("sex", sex);
    }

    public String getSignature() {
        return getString("signature");
    }

    public void setSignature(String signature) {
        put("signature", signature);
    }

    public String getEmail() {
        return getString("email");
    }

    public void setEmail(String email) {
        put("email", email);
    }

    @Override
    public String toString() {
        return "UserInfoModel{" +
                "phone='" + getMobilePhoneNumber() + '\'' +
                ", username='" + getUsername() + '\'' +
                ", header='" + getHeader() + '\'' +
                ", nickname='" + getNickname() + '\'' +
                ", sex='" + getSex() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", signature='" + getSignature() + '\'' +
                '}';
    }
}
