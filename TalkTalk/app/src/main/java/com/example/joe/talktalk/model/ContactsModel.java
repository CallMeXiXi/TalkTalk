package com.example.joe.talktalk.model;

/**
 * Created by Administrator on 2018/7/4 0004.
 */

public class ContactsModel {

    private String name;
    private String imageUri;
    private String sortLetters;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public String getSortLetters() {
        return sortLetters;
    }

    public void setSortLetters(String sortLetters) {
        this.sortLetters = sortLetters;
    }

    @Override
    public String toString() {
        return "ContactsModel{" +
                "name='" + name + '\'' +
                ", imageUri='" + imageUri + '\'' +
                ", sortLetters='" + sortLetters + '\'' +
                '}';
    }
}
