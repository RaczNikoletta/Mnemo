package com.example.fogas;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.fogas.Models.HintDataModel;
import com.example.fogas.Models.PegDataModel;
import com.example.fogas.Models.UserDataModel;

import java.util.ArrayList;

import io.realm.Realm;

public class notificationGame extends AppCompatActivity {
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
            Toast.makeText(getBaseContext(),String.valueOf(pegs.getPegs().size()),Toast.LENGTH_LONG).show();
            if (pegs.getPegs().size() <= 10) {
                try {
                    new AlertDialog.Builder(this)
                            .setTitle(R.string.addNewPegs)
                            .setMessage(R.string.addNewPegs)

                            // Specifying a listener allows you to take an action before dismissing the dialog.
                            // The dialog is automatically dismissed when a dialog button is clicked.
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                                    transaction.replace(R.id.cl2, new letterUpdateFragment(), "fromNotificaton");

                                }
                            })
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            })
                            .setIcon(getResources().getDrawable(R.drawable.ic_baseline_error_24))
                            .show();
                }catch (Throwable e){
                    Toast.makeText(getBaseContext(),"dialog: "+e.toString(),Toast.LENGTH_LONG).show();
                }
            }
        }catch (Throwable e){
            Toast.makeText(getBaseContext(),"other: "+e.toString(),Toast.LENGTH_LONG).show();
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


}