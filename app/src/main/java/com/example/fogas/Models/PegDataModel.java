package com.example.fogas.Models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class PegDataModel extends RealmObject {
    @PrimaryKey
    int pegNum;
    String pegLetter;
    String pegWord;

    public PegDataModel(){}

    public int getPegNum() {
        return pegNum;
    }

    public void setPegNum(int pegNum) {
        this.pegNum = pegNum;
    }

    public String getPegLetter() {
        return pegLetter;
    }

    public void setPegLetter(String pegLetter) {
        this.pegLetter = pegLetter;
    }

    public String getPegWord() {
        return pegWord;
    }

    public void setPegWord(String pegWord) {
        this.pegWord = pegWord;
    }
}
