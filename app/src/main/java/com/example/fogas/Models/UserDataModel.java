package com.example.fogas.Models;

import java.sql.Time;
import java.util.Date;

import io.realm.RealmDictionary;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class UserDataModel extends RealmObject {
    @PrimaryKey
    String userName;
    String password;

    PegDataModel pegs;

    Progress progress;
    RealmList<SequenceDataModel> sequences;
    RealmList<HintDataModel> hints;
    Date registryDate;
    double GameTimeInMin;

    public PegDataModel getPegs() {
        return pegs;
    }

    public void setPegs(PegDataModel pegs) {
        this.pegs = pegs;
    }

    public RealmList<SequenceDataModel> getSequences() {
        return sequences;
    }

    public void setSequences(RealmList<SequenceDataModel> sequences) {
        this.sequences = sequences;
    }

    public RealmList<HintDataModel> getHints() {
        return hints;
    }

    public void setHints(RealmList<HintDataModel> hints) {
        this.hints = hints;
    }

    public Progress getProgress() {
        return progress;
    }

    public void setProgress(Progress progress) {
        this.progress = progress;
    }


    public Date getRegistryDate() {
        return registryDate;
    }

    public void setRegistryDate(Date registryDate) {
        this.registryDate = registryDate;
    }

    public double getGameTimeInMin() {
        return GameTimeInMin;
    }

    public void setGameTimeInMin(double gameTimeInMin) {
        GameTimeInMin = gameTimeInMin;
    }


    public UserDataModel(){}

    public UserDataModel(String userName,String password){
        this.userName = userName;
        this.password = password;
        registryDate = new Date();
    }



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


}
