package com.example.fogas;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fogas.Models.HintDataModel;
import com.example.fogas.Models.PegDataModel;
import com.example.fogas.Models.UserDataModel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import io.realm.Realm;
import io.realm.RealmList;

public class notificationGame extends AppCompatActivity {
    SharedPreferences prefs = null;
    private int repeat;
    String datum;
    private Realm memoRealm;
    private UserDataModel user;
    private SuperMemo2 superMemo2;
    private PegDataModel pegs;
    private HintDataModel hints;
    private ArrayList<String> questionWord;
    private ArrayList<String>  questionLetter;
    private ArrayList<Integer> answerWord;
    private ArrayList<Integer> answerLetter;
    private Button sendNotBtn;
    private EditText answerNotEt;
    private TextView part_jelzo;
    private TextView questionNot;
    private boolean half = false;
    private boolean letterBool = false;
    private boolean wordBool = false;
    private int counter = 0;
    private int score = 0;
    private Context context;
    RealmList<Double> toInsert = new RealmList<>();
    int reps =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_game);
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        prefs = getSharedPreferences("repeatDatas",MODE_PRIVATE);
        SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd HH:mm:ss", Locale.getDefault());
        datum = sdf.format(new Date());
        context = this;
        answerWord = new ArrayList<>();
        answerLetter = new ArrayList<>();
        sendNotBtn = findViewById(R.id.sendNotBtn);
        answerNotEt = findViewById(R.id.answerNotEt);
        questionNot = findViewById(R.id.questionNotTv);
        part_jelzo = findViewById(R.id.part_jelzo);
        createNotificationChannel();
        //Toast.makeText(this,"from create: "+user.getPegs().getPegs().size(),Toast.LENGTH_LONG).show();
        generateQuestions();
        questionNot.setText(answerNotEt.getResources().getString(R.string.kerdes_betu)+" " + questionLetter.get(counter));
        sendNotBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if ((counter <= 5 && !wordBool) || letterBool) {
                        if (Integer.parseInt(answerNotEt.getText().toString()) == answerLetter.get(counter)) {
                            score++;
                            LayoutInflater inf = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                            View popupView = inf.inflate(R.layout.right_answer_layout, null);
                            ImageView image = popupView.findViewById(R.id.rightIV);

                            // show the popup window
                            // which view you pass in doesn't matter, it is only used for the window tolken
                            Toast toast = new Toast(context);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.setView(popupView);
                            toast.show();
                        } else {
                            LayoutInflater inf = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                            View popupView = inf.inflate(R.layout.wrong_answer_layout, null);
                            ImageView image = popupView.findViewById(R.id.wrongIv);

                            // show the popup window
                            // which view you pass in doesn't matter, it is only used for the window tolken
                            Toast toast = new Toast(context);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.setView(popupView);
                            toast.show();

                        }
                        counter++;
                    } else if ((half && counter > 5)) {
                        if (Integer.parseInt(answerNotEt.getText().toString()) == answerWord.get(counter - 5)) {
                            score++;
                            LayoutInflater inf = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                            View popupView = inf.inflate(R.layout.right_answer_layout, null);
                            ImageView image = popupView.findViewById(R.id.rightIV);

                            // show the popup window
                            // which view you pass in doesn't matter, it is only used for the window tolken
                            Toast toast = new Toast(context);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.setView(popupView);
                            toast.show();
                            counter++;
                        } else {
                            LayoutInflater inf = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                            View popupView = inf.inflate(R.layout.wrong_answer_layout, null);
                            ImageView image = popupView.findViewById(R.id.wrongIv);

                            // show the popup window
                            // which view you pass in doesn't matter, it is only used for the window tolken
                            Toast toast = new Toast(context);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.setView(popupView);
                            toast.show();
                            counter++;

                        }
                    }else if(wordBool){
                         if (Integer.parseInt(answerNotEt.getText().toString()) == answerWord.get(counter)) {
                            score++;
                            LayoutInflater inf = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                            View popupView = inf.inflate(R.layout.right_answer_layout, null);
                            ImageView image = popupView.findViewById(R.id.rightIV);

                            // show the popup window
                            // which view you pass in doesn't matter, it is only used for the window tolken
                            Toast toast = new Toast(context);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.setView(popupView);
                            toast.show();
                            counter++;
                        } else {
                            LayoutInflater inf = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                            View popupView = inf.inflate(R.layout.wrong_answer_layout, null);
                            ImageView image = popupView.findViewById(R.id.wrongIv);

                            // show the popup window
                            // which view you pass in doesn't matter, it is only used for the window tolken
                            Toast toast = new Toast(context);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.setView(popupView);
                            toast.show();
                            counter++;

                        }

                    } if (counter == 10) {
                        part_jelzo.setVisibility(View.INVISIBLE);
                        sendNotBtn.setVisibility(View.INVISIBLE);
                        answerNotEt.setVisibility(View.INVISIBLE);
                        questionNot.setTextColor(getResources().getColor(R.color.white));
                        questionNot.setText(getResources().getString(R.string.score)+Integer.toString(score));
                        int interval = checkResults();
                        new AlertDialog.Builder(context)
                                .setTitle(R.string.nextPractice1)
                                .setMessage(getResources().getString(R.string.nextPractice2) +" "+ Integer.toString(interval) +" "+  getResources().getString(R.string.days))

                                // Specifying a listener allows you to take an action before dismissing the dialog.
                                // The dialog is automatically dismissed when a dialog button is clicked.
                                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        long timeAtButtonClick = System.currentTimeMillis();
                                        Intent intent = new Intent(context,PracticeNotificationManager.class);
                                        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,0,intent,0);

                                        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);

                                        long daysToMili = TimeUnit.DAYS.toMillis(interval);
                                        long tenSec = 1000*10;
                                        Log.d("Daystomili", "DaysToMili: "+Long.toString(daysToMili).toString());
                                        alarmManager.set(AlarmManager.RTC_WAKEUP,timeAtButtonClick+tenSec,pendingIntent);


                                        startActivity(new Intent(context,MainMenu.class));

                                    }
                                })

                                // A null listener allows the button to dismiss the dialog and take no further action.
                                .setIcon(getResources().getDrawable(R.drawable.ic_baseline_help_center_24))
                                .show();
                    }

                    if (half && counter<10) {
                        //Toast.makeText(getBaseContext(),"half" , Toast.LENGTH_SHORT).show();
                        if (counter <= 5) {

                            questionNot.setText(answerNotEt.getResources().getString(R.string.kerdes_betu) + " " + questionLetter.get(counter));
                        } else {
                            try {
                                questionNot.setText(answerNotEt.getResources().getString(R.string.kerdes_szo) + " " + questionWord.get(counter-5));
                            }catch (Throwable e){
                                Toast.makeText(getBaseContext(),"Else ag"+e.toString(),Toast.LENGTH_LONG).show();
                            }
                        }
                    } else if (wordBool && counter <10) {
                        //Toast.makeText(getBaseContext(),"word" , Toast.LENGTH_SHORT).show();
                        questionNot.setText(answerNotEt.getResources().getString(R.string.kerdes_betu) + " " + questionWord.get(counter));
                    } else if (letterBool && counter <10) {
                        //Toast.makeText(getBaseContext(),"letter" , Toast.LENGTH_SHORT).show();
                        questionNot.setText(answerNotEt.getResources().getString(R.string.kerdes_szo) + " " + questionLetter.get(counter));
                    }
                    part_jelzo.setText(Integer.toString(counter+1) + "/10");
                }catch (Throwable e){
                    Toast.makeText(context,"SEndnotBtn: "+e.toString(),Toast.LENGTH_LONG).show();
                }
            }

        });
    }

    private void generateQuestions(){
        try {

            memoRealm = Realm.getDefaultInstance();
            user = memoRealm.where(UserDataModel.class).equalTo("loggedIn", true).findFirst();
            user.getNotifications();
        } catch(Throwable e){
            Toast.makeText(getBaseContext(),"phase one: "+e.toString(),Toast.LENGTH_LONG).show();
            }
        try{
            pegs = user.getPegs();
            hints = user.getHints();
            questionWord = new ArrayList<>();
            questionLetter = new ArrayList<>();

        }catch (Throwable e){
            Toast.makeText(getBaseContext(),"other: "+e.toString(),Toast.LENGTH_LONG).show();
        }
        for(int i=0;i<pegs.getPegs().size();i++){
            if(!pegs.getPegs().get(i).getLetter().equals("")){
                questionLetter.add(pegs.getPegs().get(i).getLetter());
            }
            if(!pegs.getPegs().get(i).getWord().equals("")){
                questionWord.add(pegs.getPegs().get(i).getWord());
            }
        }
        //Toast.makeText(getBaseContext(),"Sizes: "+ Integer.toString(questionLetter.size())+ " " +Integer.toString(questionWord.size()),Toast.LENGTH_LONG).show();
        Collections.shuffle(questionLetter);
        Collections.shuffle(questionWord);
        try {
            for (int i = 0; i < questionLetter.size(); i++) {
                for (int j = 0; j < user.getPegs().getPegs().size(); j++) {
                    if (user.getPegs().getPegs().get(j).getLetter().equals(questionLetter.get(i))) {
                        answerLetter.add(user.getPegs().getPegs().get(j).getNum());
                    }
                }
            }
            for (int i = 0; i < questionWord.size(); i++) {
                for (int j = 0; j < user.getPegs().getPegs().size(); j++) {
                    if (user.getPegs().getPegs().get(j).getWord().equals(questionWord.get(i))) {
                        answerWord.add(user.getPegs().getPegs().get(j).getNum());
                    }
                }
            }
        }catch(Throwable e){
            Toast.makeText(context,"answerWordgenerate"+e.toString(),Toast.LENGTH_LONG).show();
        }
        //Toast.makeText(getBaseContext(),pegs.counter()[0] + " " +pegs.counter()[1],Toast.LENGTH_LONG).show();
        if(pegs.counter()[0]>=5 && pegs.counter()[1]>=5){
            half = true;

        }else if(pegs.counter()[0]>=10){
            letterBool=true;

        }else if(pegs.counter()[1]>=10){
            wordBool = true;

        }
    }

    private int checkResults(){
        if(user.getLastNotification().get(1)==0.0) {
            superMemo2 = new SuperMemo2((score / 2), reps, 2.5, 0);
        }else{// resultrep .get(0),easyness .get(1),lastinterval get(2)
            superMemo2 = new SuperMemo2((score / 2), user.getLastNotification().get(0),
                    user.getLastNotification().get(1), user.getLastNotification().get(2));
        }
        memoRealm.executeTransaction(r -> {
            toInsert = superMemo2.getResult();
            user.setLastNotification(toInsert);
            user.setNotifications(reps);
            memoRealm.insertOrUpdate(user);
        });
        //Toast.makeText(context,toInsert.get(0)+" "+toInsert.get(1)+" "+toInsert.get(2),Toast.LENGTH_LONG).show();
        return (int)(double)toInsert.get(2);


    }


    @Override
    protected void onStop() {
        super.onStop();

        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("repeats_key",repeat+1);
        editor.putBoolean("first_not",false);
        editor.commit();
    }
    private  void createNotificationChannel(){
        CharSequence name = "PracticeNotifyChannel";
        String desctiption = "Channel for practice";
        int importance = NotificationManager.IMPORTANCE_HIGH;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("notifyUser",name,importance);
            channel.setDescription(desctiption);

            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

}