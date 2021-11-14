package com.example.fogas.Models;

import java.sql.Time;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmDictionary;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class UserDataModel extends RealmObject {
    @PrimaryKey
    String userName;
    String password;
    String title;
    boolean loggedIn;
    PegDataModel pegs;
    ProgressDataModel progress;
    RealmList<SequenceDataModel> sequences;

    HintDataModel hints;
    Date registryDate;
    double GameTimeInMin;


    public UserDataModel(){}

    public UserDataModel(String user,String pass){
        this.userName = user;
        this.password = pass;
        registryDate = new Date();
        pegs = new PegDataModel();
        sequences = new RealmList<SequenceDataModel>();
        hints = new HintDataModel();
        GameTimeInMin = 0;
        loggedIn = false;

    }

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


    public ProgressDataModel getProgress() {
        return progress;
    }

    public void setProgress(ProgressDataModel progress) {
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

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setOneHint(HintModel hintout){
        hints.setOneHint(hintout);
    }

    public HintDataModel getHints() {
        return hints;
    }

    public void setHints(HintDataModel hints) {
        this.hints = hints;
    }



}
