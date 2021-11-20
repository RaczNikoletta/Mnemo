package com.example.fogas;

import java.util.ArrayList;

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

    public double[] getResult(){
        double[] resultarr = new double[4];
        double resultrep;
        double resulteasy;
        double resultint;
        //correct response
        if(userGrade >= 3){
            if(repetitionNumber == 0){
                resultint = 1;
            }else if(repetitionNumber == 1){
                resultint = 6;
            }else
            {
                resultint = interval*easynessFactor;
            }resultrep = repetitionNumber++;
         //incorrent response
        }else{
            resultrep = 0;
            resultint = 1;
        }
        resulteasy = easynessFactor +(0.1-(5-userGrade)*(0.08+(5-userGrade)*0.02));
        if(resulteasy < 1.3){
            resulteasy = 1.3;
        }
        resultarr[0] = resultrep;
        resultarr[1] = resulteasy;
        resultarr[2] = resultint;

        return resultarr;
    }

}
