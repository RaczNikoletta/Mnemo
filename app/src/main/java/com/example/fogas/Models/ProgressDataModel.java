package com.example.fogas.Models;

import java.util.ArrayList;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmDictionary;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class ProgressDataModel extends RealmObject {
    @PrimaryKey
    String userName;
    RealmList<Progress> progressForEachGame;
    RealmList<Integer> allRes;
    Double avgRes;

    public RealmList<Integer> getAllRes() {
        return allRes;
    }

    public void setAllRes(RealmList<Integer> allRes) {
        this.allRes = allRes;
    }

    public Double getAvgRes() {
        return avgRes;
    }

    public void setAvgRes(Double avgRes) {
        this.avgRes = avgRes;
    }

    public ProgressDataModel(){}


    public ProgressDataModel(String name){
        userName = name;
        allRes = new RealmList<>();
        avgRes = 0.0;

    }


    public String getUserName() {
        return userName;
    }


    public RealmList<Progress> getProgressForEachGame() {
        return progressForEachGame;
    }

    public void setProgressForEachGame(RealmList<Progress> progressForEachGame) {
        this.progressForEachGame = progressForEachGame;
    }
    public void addProgress(Progress p,Date now){
        boolean isExists = false;
        for(int i=0;i<progressForEachGame.size();i++){
            assert progressForEachGame.get(i) != null;
            if(p.gameId == progressForEachGame.get(i).gameId){
                progressForEachGame.get(i).setLastPractice(now);
                allRes.add(p.lastResult);
                this.setAvgRes(getAvg(p.lastResult));
                progressForEachGame.get(i).setLastResult(p.lastResult);
                progressForEachGame.get(i).setTimeInGame(progressForEachGame.get(i).getTimeInGame()+p.getTimeInGame());
                isExists = true;
            }
        }if(!isExists){
            progressForEachGame.add(p);
        }

    }

    public double getAvg(double lastRes){
        int sum = 0;
        for(int i=0;i<allRes.size();i++){
            sum += allRes.get(i);
        }
        if(allRes.size()==0){
            return lastRes;
        }
        return (sum/(allRes.size()-1.0));
    }


}
