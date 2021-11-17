package com.example.fogas.Models;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.Time;
import java.util.Date;

import io.realm.RealmDictionary;
import io.realm.RealmObject;

public class Progress extends RealmObject {
    int gameId;
    Date lastPractice;
    int lastResult;
    double avgResult;
    Date timeInGame;

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

    public int getLastResult() {
        return lastResult;
    }

    public void setLastResult(int lastResult) {
        this.lastResult = lastResult;
    }

    public double getAvgResult() {
        return avgResult;
    }

    public void setAvgResult(double avgResult) {
        this.avgResult = avgResult;
    }
    public Date getTimeInGame() {
        return timeInGame;
    }

    public void setTimeInGame(Date timeInGame) {
        this.timeInGame = timeInGame;
    }


}
