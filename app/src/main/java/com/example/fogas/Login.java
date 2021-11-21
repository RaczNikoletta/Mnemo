package com.example.fogas;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fogas.Models.UserDataModel;

import io.realm.Realm;

public class Login extends AppCompatActivity {

    private EditText passwordEt;
    private EditText userEt;
    private Button loginMainBtn;
    private TextView noProfileTv;
    private Context context;
    private Realm loginRealm;
    private TextView notValidTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Intent i = getIntent();
        passwordEt = (EditText) findViewById(R.id.passwordEt);
        userEt = (EditText) findViewById(R.id.userEt);
        loginMainBtn = (Button) findViewById(R.id.loginBtn);
        noProfileTv = (TextView) findViewById(R.id.noProfileTv);
        notValidTv = (TextView) findViewById(R.id.notValidTv);
        createNotificationChannel();
        context = this;

        noProfileTv.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                startActivity(new Intent(context,Register.class));
                finish();
            }
        });
    }

    public void LoginAttempt(View v){
        try {
            loginRealm = Realm.getDefaultInstance();
            loginRealm.executeTransaction(r->{
                UserDataModel isValid = loginRealm.where(UserDataModel.class)
                        .equalTo("userName",userEt.getText().toString())
                        .equalTo("password",passwordEt.getText().toString())
                        .findFirst();
                //if username and password are valid (database contains them)
                if(isValid!=null){
                    /*long timeAtButtonClick = System.currentTimeMillis();
                    Intent intent = new Intent(Login.this,PracticeNotificationManager.class);
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(Login.this,0,intent,0);

                    AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

                    long tenSecondsInMillis = 1000*10;
                    alarmManager.set(AlarmManager.RTC_WAKEUP,timeAtButtonClick+tenSecondsInMillis,pendingIntent);*/

                    isValid.setLoggedIn(true);
                    startActivity(new Intent(this,MainMenu.class));
                    finish();
                }


                else{
                    notValidTv.setText(R.string.notValidLogin);
                }
            });

        }catch(Throwable e){
            String error = e.toString();
            Toast.makeText(this,error,Toast.LENGTH_LONG).show();
        }finally{
            if(loginRealm != null){
                loginRealm.close();
            }
        }


    }

    private  void createNotificationChannel(){
        CharSequence name = "PracticeNotifyChannel";
        String desctiption = "Channel for practice";
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("notifyUser",name,importance);
            channel.setDescription(desctiption);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }


}