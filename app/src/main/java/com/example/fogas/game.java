package com.example.fogas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class game extends AppCompatActivity {
    int i=0;
    String [] betuk = new String[10];
    private Context mContext;
    private Activity mActivity;

    private ConstraintLayout mConstraintLayout;
    private Button mButton;

    private PopupWindow mPopupWindow;


    public static final String EXTRA_TEXT = "com.example.application.example.EXTRA_TEXT";
   

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        getSupportActionBar().hide();

        // Get the application context
        mContext = getApplicationContext();

        // Get the activity
        mActivity = game.this;

        // Get the widgets reference from XML layout
        mConstraintLayout = (ConstraintLayout) findViewById(R.id.cl);
        mButton = (Button) findViewById(R.id.helpBtn);



    }
    public void kijelzo(){
        TextView fejlec = (TextView) findViewById(R.id.szam_fejlec);
        if (i == 1) {
            fejlec.setText("Adja meg az egyhez tartozo betut");
        }
        if(i==2){
            fejlec.setText("Adja meg a keteshez tartozo betut");
        }
        if(i==3){
            fejlec.setText("Adja meg a harmashoz tartozo betut");
        }
        if(i==4){
            fejlec.setText("Adja meg a negyeshez tartozo betut");
        }
        if(i==5){
            fejlec.setText("Adja meg az otoshoz tartozo betut");
        }
        if(i==6){
            fejlec.setText("Adja meg a hatoshoz tartozo betut");
        }
        if(i==7){
            fejlec.setText("Adja meg a heteshez tartozo betut");
        }
        if(i==8){
            fejlec.setText("Adja meg a nyolcashoz tartozo betut");
        }
        if(i==9){
            fejlec.setText("Adja meg a kilenchez tartozo betut");
        }

    }

    public void katthozzad(View view) {


        TextView szam = (TextView) findViewById(R.id.betu);
        TextView part = (TextView) findViewById(R.id.part_jelzo);
        TextView hiba = (TextView) findViewById(R.id.hiba);
        TextView fejlec = (TextView) findViewById(R.id.szam_fejlec);


        String betu= szam.getText().toString();
        if(i<9) {
            if (betu.length() == 1 && betu.matches("[a-zA-Z]+")) {
                betuk[i] = betu;

                i = i + 1;
                part.setText(i + "/9");
               kijelzo();
                hiba.setText("");
                szam.setText("");


            } else {
                hiba.setText("Nem egy betut adott meg!");
            }
        }
        else{
            if (betu.length() == 1 && betu.matches("[a-zA-Z]+")) {
            betuk[i] = betu;

            i = i + 1;
            //part.setText(i+1 + "/9");
            kijelzo();
            hiba.setText("");
            szam.setText("");
            String asd = "asd";
            Intent intent = new Intent(this, kerdesek.class);

                intent.putExtra("chars", betuk);
                startActivity(intent);
                finish();

        } else {
            hiba.setText("Nem egy betut adott meg!");
        }




        }





    }
}