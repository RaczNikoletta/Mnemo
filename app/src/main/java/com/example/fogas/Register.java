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
import android.text.Editable;
import android.text.TextWatcher;
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

import java.util.ArrayList;
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
        registerRealm = Realm.getDefaultInstance();
        context = this;

   usernameEt.addTextChangedListener(new TextWatcher() {
       @Override
       public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

       }

       @Override
       public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

       }

       @Override
       public void afterTextChanged(Editable editable) {
           UserDataModel userExists =  registerRealm.where(UserDataModel.class).equalTo("userName",
                   usernameEt.getText().toString()).findFirst();
           if(userExists == null){
               usernameEt.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_baseline_done_outline_24,0);
           }
           else{
               usernameEt.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_baseline_error_24,0);
           }
       }
   });

   passwordregagEt.addTextChangedListener(new TextWatcher() {
       @Override
       public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

       }

       @Override
       public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

       }

       @Override
       public void afterTextChanged(Editable editable) {
           if(passwordregagEt.getText().toString().equals(passwordregEt.getText().toString())){
               passwordregEt.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_baseline_done_outline_24,0);
               passwordregagEt.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_baseline_done_outline_24,0);
           }else
           {
               passwordregagEt.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_baseline_error_24,0);
           }

       }
   });



    }

    public void registerUser(View v) {
        try {
            registerRealm.executeTransaction(r ->{
                //check if user already exist
                UserDataModel exist = registerRealm.where(UserDataModel.class).equalTo("userName",usernameEt.getText().toString()).findFirst();
                if(exist!= null){
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
                }
                if(!(passwordregagEt.getText().toString().equals(passwordregEt.getText().toString()))){
                    new AlertDialog.Builder(this)
                            .setTitle(R.string.passwordsnotequal)
                            .setMessage(R.string.passwordsnotequal)

                            // Specifying a listener allows you to take an action before dismissing the dialog.
                            // The dialog is automatically dismissed when a dialog button is clicked.
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            })

                            // A null listener allows the button to dismiss the dialog and take no further action.
                            .setIcon(getResources().getDrawable(R.drawable.ic_baseline_error_24))
                            .show();
                }
                else {
                    try {
                        UserDataModel newUser = new UserDataModel(usernameEt.getText().toString(), passwordregagEt.getText().toString());
                        PegDataModel firstPegs = registerRealm.where(PegDataModel.class).equalTo("userName","").findFirst();
                        ArrayList<PegModel>pegsTodel = new ArrayList<>();
                        newUser.setTitle(getResources().getString(R.string.feledekeny));
                        if(firstPegs!=null){
                            for(int i=0;i<firstPegs.getPegs().size();i++){
                                PegModel p = new PegModel();
                                p.setNum(i);
                                p.setLetter(firstPegs.getPegs().get(i).getLetter());
                                p.setWord("");
                                registerRealm.insertOrUpdate(p);
                                newUser.getPegs().setOnePeg(p);
                            }
                            firstPegs.deleteFromRealm();
                            }


                        else {
                            for (int i = 0; i < 10; i++) {
                                PegModel p = new PegModel();
                                p.setNum(i);
                                p.setLetter("");
                                p.setWord("");
                                registerRealm.insertOrUpdate(p);
                                newUser.getPegs().setOnePeg(p);
                            }
                        }
                        registerRealm.insertOrUpdate(newUser);
                    } catch (Throwable e) {
                        Toast.makeText(this, "phase one" + e.toString(), Toast.LENGTH_LONG).show();
                    }
                    //check if registration was successful
                    UserDataModel users = registerRealm.where(UserDataModel.class).equalTo("userName", usernameEt.getText().toString()).findFirst();







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



