package com.example.fogas.Models;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class HintDataModel extends RealmObject {
    @PrimaryKey
    String userName;
    RealmList<HintModel> hints;

    public HintDataModel() {
    }

    public HintDataModel(String name) {
        userName = name;
        hints = new RealmList<>();
    }

    public String getUsername() {
        return userName;
    }

    public void setUsername(String username) {
        this.userName = username;
    }

    public RealmList<HintModel> getHints() {
        return hints;
    }

    public void setHints(RealmList<HintModel> hints) {
        this.hints = hints;
    }

    public void setOneHint(HintModel hint) {
        boolean isExist = false;
        for(int i=0;i<hints.size();i++) {
            if (hints.get(i).getPegnum() == hint.getPegnum()) {
                isExist = true;
                hints.get(i).setImage(hint.image);
            }
        }if(!isExist){
            hints.add(hint);
        }
    }

    public HintModel getOneHint(int pegNum) {
       HintModel hint = new HintModel();
       boolean isExists = false;
       if(hints==null){
           hints = new RealmList<>();
       }
        for (int i = 0; i < hints.size(); i++) {
            if (hints.get(i).getPegnum() == pegNum) {
                isExists = true;
                hint =hints.get(i);
            }
        }
        if(isExists) {
            return hint;
        }else return null;
    }
}

