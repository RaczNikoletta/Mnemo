package com.example.fogas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Random;



public class kerdesek extends AppCompatActivity {
    int []kieset_szam = new int[10];
    int []kieset_betu = new int[10];
    int kieset_szamok_szama=0;
    int kieset_betuk_szama=0;

    public String kerdes(int szam){
        String vege="";
        if(szam==0){
            vege = "-hoz";
        }
        if(szam==1){
            vege = "-hez";
        }
        if(szam==2){
            vege = "-hoz";
        }
        if(szam==3){
            vege = "-hoz";
        }
        if(szam==4){
            vege = "-hez";
        }
        if(szam==5){
            vege = "-hoz";
        }
        if(szam==6){
            vege = "-hoz";
        }
        if(szam==7){
            vege = "-hez";
        }
        if(szam==8){
            vege = "-hoz";
        }
        if(szam==9){
            vege = "-hez";
        }



        return vege;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kerdesek);
        getSupportActionBar().hide();





        int betu_szamolo=0;
        int szam_szamolo=0;



        for(int i =0;i<20;i++){
            int vagy = (int)Math.floor(Math.random()*(1-0+1)+0);
            int hanyadik;
            if(vagy==0 && szam_szamolo!=10){
                //ha 0 akkor betu
                hanyadik = (int)Math.floor(Math.random()*(9-0+1)+0);



                betu_szamolo = betu_szamolo+1;



            }
            else{
                //ha 1 akkor szam
                szam_szamolo = szam_szamolo+1;




            }


        }

    }

    public void kezdes(View view) {

        TextView text1 = (TextView) findViewById(R.id.textView);
        text1.setVisibility(View.INVISIBLE);
        TextView text2 = (TextView) findViewById(R.id.textView2);
        text2.setVisibility(View.INVISIBLE);
        TextView gomb = (TextView) findViewById(R.id.mehet);
        gomb.setText("Valasz");
        TextView bevitel = (TextView) findViewById(R.id.bevitel);
        bevitel.setVisibility(View.VISIBLE);
        Intent intent  = getIntent();
        String [] betuk = intent.getStringArrayExtra("chars");
        TextView kerdes = (TextView) findViewById(R.id.kerdes);


        Random rand = new Random();
        int betu_vagy_szam =(int)Math.floor(Math.random()*(1-0+1)+0);
        //ha betu 1 ha szam 0
        int nullatol_kilencig =(int)Math.floor(Math.random()*(9-0+1)+0);

        if(betu_vagy_szam==1){
            kerdes.setText("Melyik betu tartozik a "+ nullatol_kilencig+kerdes(nullatol_kilencig)+"?");
            kieset_betu[kieset_szamok_szama] = nullatol_kilencig;


            kieset_betuk_szama= kieset_szamok_szama+1;
            if(betuk[nullatol_kilencig].equals(bevitel.getText().toString())){

            }


        }
        else if(betu_vagy_szam==0){
            kerdes.setText("masik ag");
            kieset_szam[kieset_szamok_szama] = nullatol_kilencig;


            kieset_szamok_szama= kieset_szamok_szama+1;
        }








    }
}