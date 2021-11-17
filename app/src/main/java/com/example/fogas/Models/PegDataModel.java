package com.example.fogas.Models;

import java.util.ArrayList;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class PegDataModel extends RealmObject {
    String userName;
    RealmList<PegModel> pegs;

    public RealmList<PegModel> getPegs() {
        return pegs;
    }

    public void setPegs(RealmList<PegModel> pegs) {
        this.pegs = pegs;
    }

    public void setOnePeg(PegModel p)
    {
        boolean isExists = false;
        for(int i=0;i<pegs.size();i++){
            if(pegs.get(i).getNum() == p.getNum()){
                isExists =true;
                pegs.get(i).setLetter(p.getLetter());
                pegs.get(i).setWord(p.getWord());
            }
        }if(!isExists){
            pegs.add(p);
    }
    }
    public PegModel getOnePeg(int i){
        return pegs.get(i);
    }

    public PegDataModel(){}

    public String getUserName() {
        return userName;
    }

    public void setUserName(String user) {
        this.userName = user;
    }

    public PegDataModel(String u){
        userName = u;
        pegs = new RealmList<>();
        for(int i=0;i<9;i++){
            PegModel temppeg = new PegModel();
            temppeg.setNum(i);
            temppeg.setLetter("");
            temppeg.setWord("");
        }
    }


}
