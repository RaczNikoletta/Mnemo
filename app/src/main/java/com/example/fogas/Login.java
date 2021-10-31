package com.example.fogas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Login extends AppCompatActivity {

    private EditText passwordEt;
    private EditText userEt;
    private Button loginMainBtn;
    private TextView noProfileTv;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Intent i = getIntent();
        passwordEt = (EditText) findViewById(R.id.passwordEt);
        userEt = (EditText) findViewById(R.id.userEt);
        loginMainBtn = (Button) findViewById(R.id.loginBtn);
        noProfileTv = (TextView) findViewById(R.id.noProfileTv);
        context = this;

        noProfileTv.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                startActivity(new Intent(context,Register.class));
            }
        });
    }

}