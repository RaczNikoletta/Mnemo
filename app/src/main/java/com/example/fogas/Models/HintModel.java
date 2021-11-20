package com.example.fogas.Models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class HintModel extends RealmObject {
    @PrimaryKey
    int Pegnum;
    byte [] image;

   public HintModel(){}

    public int getPegnum() {
        return Pegnum;
    }

    public void setPegnum(int pegnum) {
        Pegnum = pegnum;
    }


    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
