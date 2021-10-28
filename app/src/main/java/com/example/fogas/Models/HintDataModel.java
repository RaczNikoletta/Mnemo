package com.example.fogas.Models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class HintDataModel extends RealmObject {
    UserDataModel user;
    @PrimaryKey
    int pegNum;
    String hint;
    byte [] image;

    public UserDataModel getUser() {
        return user;
    }

    public void setUser(UserDataModel user) {
        this.user = user;
    }

    public int getPegNum() {
        return pegNum;
    }

    public void setPegNum(int pegNum) {
        this.pegNum = pegNum;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

}
