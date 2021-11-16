package com.example.fogas.Models;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class SequenceDataModel extends RealmObject {
    UserDataModel user;
    RealmList<PegModel> sequence;
    RealmList<String> story;


    public RealmList<PegModel> getSequence() {
        return sequence;
    }

    public void setSequence(RealmList<PegModel> sequence) {
        this.sequence = sequence;
    }

    public UserDataModel getUser() {
        return user;
    }

    public void setUser(UserDataModel user) {
        this.user = user;
    }

    public SequenceDataModel(){}

    public RealmList<String> getStory() {
        return story;
    }

    public void setStory(RealmList<String> story) {
        this.story = story;
    }


    public SequenceDataModel(RealmList<PegModel> sequence){
        this.sequence = sequence;
    }

    public PegModel getOneFromSeq(int position){
        PegModel temp = new PegModel();
        if(position > sequence.size()){
            return null;
        }
        if(position < 0){
            return null;
        }
        for(int i=0;i<sequence.size();i++){
            if(i==position){
                temp = sequence.get(i);
            }
        }return temp;
    }

}
