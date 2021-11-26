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
    int notifications;
    RealmList<Double> lastNotification;
    double GameTimeInMin;


    public UserDataModel(){sequences = new RealmList<>();}

    public UserDataModel(String user,String pass){
        this.userName = user;
        this.password = pass;
        registryDate = new Date();
        pegs = new PegDataModel(user);
        sequences = new RealmList<SequenceDataModel>();
        hints = new HintDataModel(user);
        progress = new ProgressDataModel(userName);
        GameTimeInMin = 0;
        loggedIn = false;
        notifications = 0;
        lastNotification = new RealmList<>();
        lastNotification.add(0.0);
        lastNotification.add(0.0);
        lastNotification.add(0.0);

    }
    public int getNotifications() {
        return notifications;
    }
    public RealmList<Double> getLastNotification() {
        return lastNotification;
    }

    public void setLastNotification(RealmList<Double> lastNotification) {
        this.lastNotification = lastNotification;
    }

    public void setNotifications(int notifications) {
        this.notifications = notifications;
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

    public void setOneSequence(SequenceDataModel s){
        boolean isExists = false;
        //check if sequence is already exists in the database
        if(sequences == null){
            sequences = new RealmList<>();
        }
        for(int i=0;i<sequences.size();i++){
            SequenceDataModel temp = sequences.get(i);
            if(s.isEqual(temp))
            {
                isExists = true;
            }
        }

        if(!isExists){
            sequences.add(s);
        }
    }



}
