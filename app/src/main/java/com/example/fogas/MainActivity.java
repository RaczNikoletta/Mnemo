package com.example.fogas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import io.realm.Realm;
import nl.psdcompany.duonavigationdrawer.views.DuoDrawerLayout;
import nl.psdcompany.duonavigationdrawer.views.DuoMenuView;
import nl.psdcompany.duonavigationdrawer.widgets.DuoDrawerToggle;

public class MainActivity extends AppCompatActivity {
    private Realm startRealm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();




    }

    public void ujlap(View view) {
        //check if the pegDataModel table is not empty
        startRealm = Realm.getDefaultInstance();
        if(startRealm.isEmpty()) { // true if no objects are in the Realm
            startActivity(new Intent(this, game.class));
        }else
            startActivity(new Intent(this,MainMenu.class));
    }
}