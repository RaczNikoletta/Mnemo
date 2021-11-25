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


    public ProgressDataModel(){}


    public ProgressDataModel(String name){
        userName = name;

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
                progressForEachGame.get(i).addResult(p.results.get(0));
                progressForEachGame.get(i).setAvg();
                progressForEachGame.get(i).setTimeInGame(progressForEachGame.get(i).getTimeInGame()+p.getTimeInGame());
                isExists = true;
            }
        }if(!isExists){
            progressForEachGame.add(p);
        }

    }
    public Progress getProgressById(int id){
        Progress temp = new Progress();
        for (int i=0;i<progressForEachGame.size();i++){
            if(progressForEachGame.get(i).gameId==id){
                temp = progressForEachGame.get(i);
            }
        }return temp;
    }



}
