package com.example.fogas.Models;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class HintDataModel extends RealmObject {

    String username;
    RealmList<HintModel> hints;

    public HintDataModel(){}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public RealmList<HintModel> getHints() {
        return hints;
    }

    public void setHints(RealmList<HintModel> hints) {
        this.hints = hints;
    }



}
