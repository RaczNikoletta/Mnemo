package com.example.fogas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Random;





public class kerdesek extends AppCompatActivity {
int [] elhasznalt_index=new int[20];
int index = 0;
int pontok = 0;

    public String kerdes(int szam){
        String vege="";
        if(szam==0 || szam==2 || szam==3 || szam==5 ||szam==6 || szam==8){
            vege = "-hoz";
        }
        if(szam==1 || szam==4 ||szam==7 ||szam==9){
            vege = "-hez";
        }
        return vege;
    }

    public String kerdes2(int szam){
        String vege="";
        if(szam==0 || szam==2 ||szam==3||szam==4||szam==6 ||szam==7||szam==8||szam==9){
            vege = "a";
        }
        if(szam==1 ||szam==5){
            vege = "az";
        }
        return vege;
    }
    public String kerdes3(String betu){
        String vege="";
        if(betu.equals("A") || betu.equals("E") || betu.equals("F") ||betu.equals("I")||
                betu.equals("L")||betu.equals("M")|| betu.equals("N") ||betu.equals("O")||
                betu.equals("R") ||betu.equals("S") ||betu.equals("U")||betu.equals("X") ||
                betu.equals("Y")){
            vege = "az";
        }
        if(betu.equals("B") ||betu.equals("C") ||betu.equals("D")||betu.equals("G")||
                betu.equals("H") ||betu.equals("J")||betu.equals("K")||betu.equals("P")||
                betu.equals("Q")||betu.equals("T")||betu.equals("V")||betu.equals("W")||
                betu.equals("Z")){
            vege = "a";
        }
        return vege;
    }

    public String kerdes4(String betu){
        String vege="";
        if(betu.equals("A")||betu.equals("H")||betu.equals("K")||betu.equals("O")||
                betu.equals("Q")||betu.equals("U")||betu.equals("Y")){
            vege = "-hoz";
        }
        if(betu.equals("B")||betu.equals("C")||betu.equals("D")||betu.equals("E")||
                betu.equals("F")||betu.equals("G")||betu.equals("I")||betu.equals("J")||
                betu.equals("L")||betu.equals("M")||betu.equals("N")||betu.equals("P")||
                betu.equals("R")||betu.equals("S")||betu.equals("T")||betu.equals("V")||
                betu.equals("W")||betu.equals("X")||betu.equals("Z")){
            vege = "-hez";
        }
        return vege;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kerdesek);
        getSupportActionBar().hide();






    }

    public void kezdes(View view) {
        if(index<20) {

            TextView text1 = (TextView) findViewById(R.id.textView);
            text1.setVisibility(View.INVISIBLE);
            TextView text2 = (TextView) findViewById(R.id.textView2);
            text2.setVisibility(View.INVISIBLE);
            TextView gomb = (TextView) findViewById(R.id.mehet);
            gomb.setText("Valasz");
            TextView bevitel = (TextView) findViewById(R.id.bevitel);
            bevitel.setVisibility(View.VISIBLE);
            Intent intent = getIntent();
            String[] betuk = intent.getStringArrayExtra("chars");
            TextView kerdes = (TextView) findViewById(R.id.kerdes);




            String[] kerdesek = new String[20];

            for (int i = 0; i < 10; i++) {
                kerdesek[i] = "Melyik betu tartozik " + kerdes2(i) + " " + i + kerdes(i) + "?";
            }

            for (int i = 10; i < 20; i++) {
                kerdesek[i] =
                        "Melyik szam tatozik " + kerdes3(betuk[i - 10].toUpperCase()) + " "
                                + betuk[i - 10].toUpperCase() + kerdes4(betuk[i - 10].toUpperCase()) + "?";
            }

              //int random_szam  = (int)Math.floor(Math.random()*(19-0+1)+0);

                    kerdes.setText(kerdesek[index]);
                    String valasz= bevitel.getText().toString();
                if(valasz.equals(betuk[0])){
                    pontok++;
                }




            index = index+1;
                    bevitel.setText("");
        }
        else{
            TextView kerdes = (TextView) findViewById(R.id.kerdes);
            TextView bevitel = (TextView) findViewById(R.id.bevitel);
            bevitel.setVisibility(View.INVISIBLE);
            TextView gomb = (TextView) findViewById(R.id.mehet);
            gomb.setVisibility(View.INVISIBLE);
            kerdes.setText("Ennyi helyes valasza volt: " + pontok);
            //kerdes.setText("Vege!");
        }
    }


}