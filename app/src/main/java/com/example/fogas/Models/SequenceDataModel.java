package com.example.fogas.Models;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class SequenceDataModel extends RealmObject {
    UserDataModel user;
    @PrimaryKey
    long sequence;
    RealmList<String> story;



    public UserDataModel getUser() {
        return user;
    }

    public void setUser(UserDataModel user) {
        this.user = user;
    }

    public SequenceDataModel(){}

    public long getSequence() {
        return sequence;
    }

    public void setSequence(long sequence) {
        this.sequence = sequence;
    }

    public RealmList<String> getStory() {
        return story;
    }

    public void setStory(RealmList<String> story) {
        this.story = story;
    }


    public SequenceDataModel(long sequence){
        this.sequence = sequence;
    }

}
