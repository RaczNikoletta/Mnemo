package com.example.fogas;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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
import java.util.Date;
import java.util.Locale;

import io.realm.Realm;

public class notificationGame extends AppCompatActivity {
    SharedPreferences prefs = null;
    private int repeat;
    String datum;
    private ArrayList<String> questions;
    private Realm memoRealm;
    private UserDataModel user;
    private SuperMemo2 superMemo2;
    private PegDataModel pegs;
    private HintDataModel hints;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_game);
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        superMemo2 = new SuperMemo2();
        prefs = getSharedPreferences("repeatDatas",MODE_PRIVATE);
        SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd HH:mm:ss", Locale.getDefault());
        datum = sdf.format(new Date());
        //Toast.makeText(this,"from create: "+user.getPegs().getPegs().size(),Toast.LENGTH_LONG).show();
        questions = new ArrayList<>();
        generateQuestions();
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

        }catch (Throwable e){
            Toast.makeText(getBaseContext(),"other: "+e.toString(),Toast.LENGTH_LONG).show();
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