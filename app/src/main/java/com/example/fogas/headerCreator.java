package com.example.fogas;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class headerCreator extends AppCompatActivity {
    private TextView headerUserName;
    private TextView headerTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userheader);
        getSupportActionBar().hide();

        headerUserName = (TextView) findViewById(R.id.userTv);
        headerTitle = (TextView) findViewById(R.id.titleTv);
        //TODO: Get username! and title
        //headerUserName.setText("Username");
        //headerTitle.setText("Title");


    }
}
