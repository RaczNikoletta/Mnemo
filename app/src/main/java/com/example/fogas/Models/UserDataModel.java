package com.example.fogas.Models;

import java.sql.Time;
import java.util.Date;

import io.realm.RealmDictionary;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class UserDataModel extends RealmObject {
    @PrimaryKey
    String userName;
    String password;
    RealmDictionary<String> progress;
    Date registryDate;
    double GameTimeInMin;
    int age;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public RealmDictionary<String> getProgress() {
        return progress;
    }

    public void setProgress(RealmDictionary<String> progress) {
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
