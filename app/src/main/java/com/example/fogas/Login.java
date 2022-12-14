package com.example.fogas;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
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

       /* if(Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat.checkSelfPermission(getBaseContext(),
                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {//nincmég engedély
                AlertDialog.Builder alertD = new AlertDialog.Builder(getBaseContext());
                alertD.setCancelable(false);
                alertD.setTitle("Access");
                alertD.setIcon(R.drawable.ic_baseline_lock_24);
                alertD.setMessage("Please permit notifications!");
                alertD.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ActivityCompat.requestPermissions(Login.this, new String[]
                                {Manifest.permission.ACCESS_NOTIFICATION_POLICY}, 1); //onRequestPermissions(...) fgv.
                        // hívódik meg
                    }
                });
                alertD.setNegativeButton("Nem", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(Login.this, "Nem tudsz hívást kezdeményezni", Toast.LENGTH_SHORT).show();
                        //hivasBtn.setEnabled(false);
                    }
                });
                alertD.show();

            }
        }//nincsmég engedély*/

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