package com.example.fogas;

import java.util.ArrayList;

import io.realm.RealmList;

public class SuperMemo2 {
    int userGrade;
    double repetitionNumber;
    double easynessFactor;
    double interval;

    public  SuperMemo2(){}
    public SuperMemo2(int grade, double rep,double factor, double interval){
        userGrade = grade;
        repetitionNumber = rep;
        easynessFactor = factor;
        this.interval = interval;
    }

    public RealmList<Double> getResult(){
        RealmList<Double> resultarr = new RealmList<>();
        double resultrep;
        double resulteasy;
        double resultint;
        //correct response
        if(userGrade >= 3){
            if(repetitionNumber == 0){
                resultint = 1.0;
            }else if(repetitionNumber == 1){
                resultint = 6.0;
            }else
            {
                resultint = interval*easynessFactor;
            }resultrep = repetitionNumber++;
         //incorrent response
        }else{
            resultrep = 0.0;
            resultint = 1.0;
        }
        resulteasy = easynessFactor +(0.1-(5-userGrade)*(0.08+(5-userGrade)*0.02));
        if(resulteasy < 1.3){
            resulteasy = 1.3;
        }
        resultarr.add(resultrep);
        resultarr.add(resulteasy);
        resultarr.add(resultint);

        return resultarr;
    }

}
