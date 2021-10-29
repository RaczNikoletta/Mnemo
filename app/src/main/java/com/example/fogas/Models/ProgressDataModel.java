package com.example.fogas.Models;

import io.realm.RealmDictionary;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class ProgressDataModel extends RealmObject {
    @PrimaryKey
    int progressId;
    UserDataModel user;
    RealmList<Progress> progressForEachGame;

    public ProgressDataModel(){}

    public int getProgressId() {
        return progressId;
    }

    public void setProgressId(int progressId) {
        this.progressId = progressId;
    }

    public UserDataModel getUser() {
        return user;
    }

    public void setUser(UserDataModel user) {
        this.user = user;
    }

    public RealmList<Progress> getProgressForEachGame() {
        return progressForEachGame;
    }

    public void setProgressForEachGame(RealmList<Progress> progressForEachGame) {
        this.progressForEachGame = progressForEachGame;
    }


}
