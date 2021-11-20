package com.example.fogas.Models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class PegModel extends RealmObject{
    int num;
    String letter;
    String word;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public PegModel(){}
}
