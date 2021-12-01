package com.example.fogas;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fogas.Models.UserDataModel;

import io.realm.Realm;

public class statisticsFragment extends Fragment {
    private TextView StatisticsTitle;
    private TextView letterStatTv;
    private TextView wordStatTv;
    private TextView sequenceStatTv;
    private TextView timeInGameTv;
    private Realm statRealm;
    private UserDataModel user;
    private double minInGame;


    public statisticsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_statistics, container, false);
        statRealm = Realm.getDefaultInstance();
        user = statRealm.where(UserDataModel.class).equalTo("loggedIn",true).findFirst();
        StatisticsTitle = view.findViewById(R.id.StatisticsTitle);
        letterStatTv = view.findViewById(R.id.letterStatTv);
        wordStatTv = view.findViewById(R.id.wordStatTv);
        sequenceStatTv = view.findViewById(R.id.sequenceStatTv);
        timeInGameTv = view.findViewById(R.id.timeInGameTv);
        StatisticsTitle.setText(R.string.statistics);
        try {
            if (user.getProgress().getProgressById(1) != null) {
                letterStatTv.setText(getResources().getString(R.string.statisticsLetter) + " " + Double.toString(user.getProgress().getProgressById(1).getAvgRes()).toString());
            } else {
                letterStatTv.setText(getResources().getString(R.string.statisticsLetter) + " ");
            }
            if (user.getProgress().getProgressById(2) != null) {
                wordStatTv.setText(getResources().getString(R.string.statisticsWord) + " " + Double.toString(user.getProgress().getProgressById(2).getAvgRes()).toString());
            } else {
                wordStatTv.setText(getResources().getString(R.string.statisticsWord) + " ");
            }
            if (user.getProgress().getProgressById(3) != null) {
                int scores = 0;
                if(user.getProgress().getProgressById(3).getResults()!=null) {
                    for (int i = 0; i < user.getProgress().getProgressById(3).getResults().size(); i++) {
                        scores += user.getProgress().getProgressById(3).getResults().get(i);
                    }
                }
                sequenceStatTv.setText(Integer.toString(scores).toString());
            } else {
                sequenceStatTv.setText(getResources().getString(R.string.statisticsSequence) + " ");
            }
            minInGame = 0;
            if(user.getProgress().getProgressForEachGame()!=null) {
                for (int i = 0; i < user.getProgress().getProgressForEachGame().size(); i++) {
                    if(user.getProgress().getProgressForEachGame().get(i)!=null)
                    minInGame += user.getProgress().getProgressForEachGame().get(i).getTimeInGame();
                }
            }
            timeInGameTv.setText(getResources().getString(R.string.timeInGameStat) + " " + String.format( "%.0f",minInGame)+" "+ getResources().getString(R.string.min));
        }catch (Throwable e){
            Toast.makeText(getContext(),e.toString(),Toast.LENGTH_LONG).show();
        }


        return view;
    }
}