package com.example.fogas.Models;

import io.realm.RealmDictionary;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class ProgressDataModel extends RealmObject {
    @PrimaryKey
    String userName;
    RealmList<Progress> progressForEachGame;

    public ProgressDataModel(){}


    public String getUserName() {
        return userName;
    }


    public RealmList<Progress> getProgressForEachGame() {
        return progressForEachGame;
    }

    public void setProgressForEachGame(RealmList<Progress> progressForEachGame) {
        this.progressForEachGame = progressForEachGame;
    }


}
