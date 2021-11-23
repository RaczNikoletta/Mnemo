package com.example.fogas.Models;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.Time;
import java.util.Date;

import io.realm.RealmDictionary;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Progress extends RealmObject {
    @PrimaryKey
    int gameId;
    Date lastPractice;
    double timeInGame;
    double avgRes;
    RealmList<Integer> results;

    public Progress(int gameId){
        this.gameId = gameId;
        results = new RealmList<>();
    }


    public RealmList<Integer> getResults() {
        return results;
    }

    public void setResults(RealmList<Integer> results) {
        this.results = results;
    }



    public double getAvgRes() {
        return avgRes;
    }


    public Progress(){};

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public Date getLastPractice() {
        return lastPractice;
    }

    public void setLastPractice(Date lastPractice) {
        this.lastPractice = lastPractice;
    }


    public double getTimeInGame() {
        return timeInGame;
    }

    public void setTimeInGame(double timeInGame) {
        this.timeInGame = timeInGame;
    }

    public void addResult(int res){
        results.add(res);
    }

    public void setAvg(){
        int sum = 0;
        for(int i=0;i<results.size();i++){
            sum += results.get(i);
        }
        if(results.size()==0){
            avgRes = 0;
        }
        else avgRes =(sum/(results.size()+0.0));
    }


}
