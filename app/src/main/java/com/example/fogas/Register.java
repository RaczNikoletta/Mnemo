package com.example.fogas;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fogas.Models.HintDataModel;
import com.example.fogas.Models.PegDataModel;
import com.example.fogas.Models.PegModel;
import com.example.fogas.Models.ProgressDataModel;
import com.example.fogas.Models.SequenceDataModel;
import com.example.fogas.Models.UserDataModel;

import java.util.Date;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

public class Register extends AppCompatActivity {
    private EditText usernameEt;
    private EditText passwordregEt;
    private EditText passwordregagEt;
    private Realm registerRealm;
    private Button sendRegisterBtn;
    private Context context;
    private TextView alreadyExists;
    private int requestId =99;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        usernameEt = (EditText) findViewById(R.id.usernameEt);
        passwordregEt = (EditText) findViewById(R.id.passwordregEt);
        passwordregagEt = (EditText) findViewById(R.id.passwordregagEt);
        sendRegisterBtn = (Button) findViewById(R.id.sendRegisterBtn);
        alreadyExists = (TextView) findViewById(R.id.alreadyExists);
        context = this;



    }

    public void registerUser(View v) {
        try {
            registerRealm = Realm.getDefaultInstance();
            registerRealm.executeTransaction(r ->{
                //check if user already exist
                UserDataModel exist = registerRealm.where(UserDataModel.class).equalTo("userName",usernameEt.getText().toString()).findFirst();
                if(exist!= null){
                    //TODO textview folyamatos ellenőrzése, piros háttér ha foglalt, zöld pipa ha szabad
                    new AlertDialog.Builder(this)
                            .setTitle(R.string.useralreadyexists)
                            .setMessage(R.string.useralreadyexists)

                            // Specifying a listener allows you to take an action before dismissing the dialog.
                            // The dialog is automatically dismissed when a dialog button is clicked.
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            })

                            // A null listener allows the button to dismiss the dialog and take no further action.
                            .setIcon(getResources().getDrawable(R.drawable.ic_baseline_error_24))
                            .show();
                }else {
                    try {
                        UserDataModel newUser = new UserDataModel();
                        newUser.setUserName(usernameEt.getText().toString());
                        newUser.setPassword(passwordregEt.getText().toString());
                        newUser.setRegistryDate(new Date());
                        newUser.setHints(new HintDataModel(usernameEt.getText().toString()));
                        newUser.setProgress(new ProgressDataModel(usernameEt.getText().toString()));
                        newUser.setSequences(new RealmList<SequenceDataModel>());
                        newUser.setTitle(getResources().getString(R.string.feledekeny));
                        newUser.setPegs(new PegDataModel());
                        newUser.getPegs().setUserName("xxxxxxxxxx");
                        registerRealm.insertOrUpdate(newUser);
                    }catch(Throwable e){
                        Toast.makeText(this,"phase one" +e.toString(),Toast.LENGTH_LONG).show();
                    }
                    //check if registration was successful
                    UserDataModel users = registerRealm.where(UserDataModel.class).equalTo("userName", usernameEt.getText().toString()).findFirst();
                    if (users != null) {
                        PegDataModel pegs = registerRealm.where(PegDataModel.class).equalTo("userName","").findFirst();
                        if(pegs != null) {
                            try {
                                pegs.setUserName(usernameEt.getText().toString());
                                users.setPegs(pegs);
                                registerRealm.insertOrUpdate(users);
                                PegDataModel nullPegs = registerRealm.where(PegDataModel.class).equalTo("userName","xxxxxxxxxx").findFirst();
                                nullPegs.deleteFromRealm();
                            }catch (Throwable e){
                                Toast.makeText(this,"phase two" +e.toString(),Toast.LENGTH_LONG).show();
                            }
                        }else{
                            try {
                                PegDataModel temppegs = new PegDataModel(users.getUserName());
                                 PegModel temppeg = new PegModel();
                                 temppeg.setNum(0);
                                 temppeg.setLetter("");
                                 temppeg.setWord("");
                                 registerRealm.insertOrUpdate(temppeg);
                                 temppegs.setOnePeg(temppeg);
                                 temppegs.setUserName(users.getUserName());
                                registerRealm.insertOrUpdate(temppegs);
                                users.setPegs(temppegs);
                                registerRealm.insertOrUpdate(users);//xxxxxxxxxx
                                RealmResults<PegDataModel> nullPegs = registerRealm.where(PegDataModel.class).equalTo("userName","xxxxxxxxxx").findAll();
                                nullPegs.deleteAllFromRealm();
                                registerRealm.insertOrUpdate(nullPegs);

                            }catch (Throwable e){
                                Toast.makeText(this,"phase three" +e.toString(),Toast.LENGTH_LONG).show();
                            }
                        }






                        registerRealm.close();
                        new AlertDialog.Builder(this)
                                .setTitle(R.string.sikeresregtitle)
                                .setMessage(R.string.sikeresregtext)

                                // Specifying a listener allows you to take an action before dismissing the dialog.
                                // The dialog is automatically dismissed when a dialog button is clicked.
                                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {


                                        startActivity(new Intent(context, Login.class));
                                    }
                                })

                                // A null listener allows the button to dismiss the dialog and take no further action.
                                .setIcon(getResources().getDrawable(R.drawable.ic_baseline_done_outline_24))
                                .show();
                    }
                }
            });
        }catch(Throwable e){
            String error = e.toString();
            Toast.makeText(this, error, Toast.LENGTH_LONG).show();
        }
            finally {
            if (registerRealm != null) {
                registerRealm.close();
        }

    }
}

}



