package com.example.fogas.Models;

import java.util.ArrayList;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class PegDataModel extends RealmObject {
    String userName;
    RealmList<PegModel> pegs;

    public RealmList<PegModel> getPegs() {
        return pegs;
    }

    public void setPegs(RealmList<PegModel> pegs) {
        this.pegs = pegs;
    }

    public void setOnePeg(PegModel p)
    {
        pegs.add(p);
    }
    public PegModel getOnePeg(int i){
        return pegs.get(i);
    }

    public PegDataModel(){}

    public String getUserName() {
        return userName;
    }

    public void setUserName(String user) {
        this.userName = user;
    }


}
