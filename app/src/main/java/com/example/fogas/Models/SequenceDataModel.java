package com.example.fogas.Models;

import io.realm.RealmList;
import io.realm.RealmObject;

public class SequenceDataModel extends RealmObject {
    long sequence;
    RealmList<String> story;

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
