package com.example.fogas.Models;

import io.realm.RealmDictionary;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class UserDataModel extends RealmObject {
    @PrimaryKey
    String userName;
    String password;
    RealmDictionary<UserDataModel> progress;

    public UserDataModel(){}


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public RealmDictionary<UserDataModel> getProgress() {
        return progress;
    }

    public void setProgress(RealmDictionary<UserDataModel> progress) {
        this.progress = progress;
    }

}
