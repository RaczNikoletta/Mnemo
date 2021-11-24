package com.example.fogas;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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

import io.realm.Realm;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_game);
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        superMemo2 = new SuperMemo2();
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
                    } else if ((half && counter > 5) || wordBool) {
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

                    } if (counter == 9) {
                        sendNotBtn.setVisibility(View.INVISIBLE);
                        answerNotEt.setVisibility(View.INVISIBLE);
                        questionNot.setText(Integer.toString(score));
                    }
                    if (half && counter<9) {
                        if (counter <= 5) {
                            questionNot.setText(answerNotEt.getResources().getString(R.string.kerdes_betu) + " " + questionLetter.get(counter));
                        } else {
                            questionNot.setText(answerNotEt.getResources().getString(R.string.kerdes_szo) + " " + questionWord.get(counter));
                        }
                    } else if (wordBool && counter <9) {
                        questionNot.setText(answerNotEt.getResources().getString(R.string.kerdes_betu) + " " + questionLetter.get(counter));
                    } else if (letterBool && counter <9) {
                        questionNot.setText(answerNotEt.getResources().getString(R.string.kerdes_szo) + " " + questionWord.get(counter));
                    }
                    part_jelzo.setText(Integer.toString(counter+1) + "/10");
                }catch (Throwable e){
                    Toast.makeText(context,e.toString(),Toast.LENGTH_LONG).show();
                }
            }

        });
    }

    private void generateQuestions(){
        try {

            memoRealm = Realm.getDefaultInstance();
            user = memoRealm.where(UserDataModel.class).equalTo("loggedIn", true).findFirst();
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
            Toast.makeText(context,e.toString(),Toast.LENGTH_LONG).show();
        }
        if(pegs.counter()[0]>=5 && pegs.counter()[1]>=5){
            half = true;

        }else if(pegs.counter()[0]>=10){
            letterBool=true;

        }else if(pegs.counter()[1]>=10){
            wordBool = true;

        }
    }

    private void checkResults(){

        //write out needed datas
        repeat = prefs.getInt("repeats_key",1);
        FileOutputStream fileout = null;
        {
            try {
                fileout = openFileOutput("memoDatas",MODE_APPEND);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            datum = datum +"\n";
            try {
                fileout.write(datum.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if(fileout!=null){
                    try {
                        fileout.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        File file = getExternalFilesDir(null);
        String filename = file.getAbsolutePath()+"superMemoDatas.txt";
        FileOutputStream fs = null;
        try {
            fs = new FileOutputStream(filename);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            fs.write(Integer.toString(repeat).getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fs.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    private  void setNotificationChannel(){
        CharSequence name = "PracticeNotifyChannel";
        String desctiption = "Channel for practice";
        int importance = NotificationManager.IMPORTANCE_HIGH;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("notifyUser",name,importance);
            channel.setDescription(desctiption);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("repeats_key",repeat+1);
        editor.putBoolean("first_not",false);
        editor.commit();
    }
}