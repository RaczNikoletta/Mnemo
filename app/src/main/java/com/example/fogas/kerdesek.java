package com.example.fogas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;


public class kerdesek extends AppCompatActivity {
    int [] sorrend=new int[21];
    int index = 0;
    ArrayList<Integer> mylist = new ArrayList<Integer>();
    String hello;
    int futas = 0;
    private TextView hibaTv;
    private int bevIfInt;
    private String tempPontokString;
    private Button skipBtn;


    String [] valaszok = new String[20];

    String []helyes_valaszok  = new String[20];





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
        if(betu.equalsIgnoreCase("A") || betu.equalsIgnoreCase("E") || betu.equalsIgnoreCase("F") ||betu.equalsIgnoreCase("I")||
                betu.equalsIgnoreCase("L")||betu.equalsIgnoreCase("M")|| betu.equalsIgnoreCase("N") ||betu.equalsIgnoreCase("O")||
                betu.equalsIgnoreCase("R") ||betu.equalsIgnoreCase("S") ||betu.equalsIgnoreCase("U")||betu.equalsIgnoreCase("X") ||
                betu.equalsIgnoreCase("Y")){
            vege = "az";
        }
        if(betu.equalsIgnoreCase("B") ||betu.equalsIgnoreCase("C") ||betu.equalsIgnoreCase("D")||betu.equalsIgnoreCase("G")||
                betu.equalsIgnoreCase("H") ||betu.equalsIgnoreCase("J")||betu.equalsIgnoreCase("K")||betu.equalsIgnoreCase("P")||
                betu.equalsIgnoreCase("Q")||betu.equalsIgnoreCase("T")||betu.equalsIgnoreCase("V")||betu.equalsIgnoreCase("W")||
                betu.equalsIgnoreCase("Z")){
            vege = "a";
        }
        return vege;
    }

    public String kerdes4(String betu){
        String vege="";
        if(betu.equalsIgnoreCase("A")||betu.equalsIgnoreCase("H")||betu.equalsIgnoreCase("K")||betu.equalsIgnoreCase("O")||
                betu.equalsIgnoreCase("Q")||betu.equalsIgnoreCase("U")||betu.equalsIgnoreCase("Y")){
            vege = "-hoz";
        }
        if(betu.equalsIgnoreCase("B")||betu.equalsIgnoreCase("C")||betu.equalsIgnoreCase("D")||betu.equalsIgnoreCase("E")||
                betu.equalsIgnoreCase("F")||betu.equalsIgnoreCase("G")||betu.equalsIgnoreCase("I")||betu.equalsIgnoreCase("J")||
                betu.equalsIgnoreCase("L")||betu.equalsIgnoreCase("M")||betu.equalsIgnoreCase("N")||betu.equalsIgnoreCase("P")||
                betu.equalsIgnoreCase("R")||betu.equalsIgnoreCase("S")||betu.equalsIgnoreCase("T")||betu.equalsIgnoreCase("V")||
                betu.equalsIgnoreCase("W")||betu.equalsIgnoreCase("X")||betu.equalsIgnoreCase("Z")){
            vege = "-hez";
        }
        return vege;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kerdesek);
        getSupportActionBar().hide();

        TextView teszt = (TextView) findViewById(R.id.hibaTv);
        teszt.setVisibility(View.INVISIBLE);
        skipBtn = (Button) findViewById(R.id.skipBtn);

        for(int i =0;i<20;i++){
            mylist.add(i);

        }
        Collections.shuffle(mylist);
        for(int i = 0;i<20;i++){
            sorrend[i]= mylist.get(i);
        }
        TextView gomb1 = (TextView) findViewById(R.id.mehet);
        gomb1.setVisibility(View.INVISIBLE);
        TextView gomb2 = (TextView) findViewById(R.id.mehet2);







    }


    public void kezdes(View view) {
        TextView bevitel = (TextView) findViewById(R.id.bevitel);
        TextView hibaTv = (TextView) findViewById(R.id.hibaTv);
        String bev = bevitel.toString();
        //TODO: kitalálni hogy hogy nézzük meg hogy a kérdés számra vagy betűre vonatkozik
        //check if bev is an integer to prevent parseint exception  43w
        if(isInteger(bev))
        {
          bevIfInt = Integer.parseInt(bev);
        }
        //int bevIfInt = Integer.parseInt(bev);

        if (bevitel.length() == 1 && ((bev.matches("[a-zA-Z]+")) || (bevIfInt <= 9 && bevIfInt >=0))){


            if(index<20) {
                Intent intent = getIntent();
                String[] betuk = intent.getStringArrayExtra("chars");
                for(int i =0;i<10;i++){
                    helyes_valaszok[i] = betuk[i];
                }


                for(int i=10;i<20;i++){
                    helyes_valaszok[i]= String.valueOf(i-10);
                }


                //TextView gomb = (TextView) findViewById(R.id.mehet);

                bevitel.setVisibility(View.VISIBLE);

                TextView kerdes = (TextView) findViewById(R.id.kerdes);

                String[] kerdesek = new String[20];

                for (int i = 0; i < 10; i++) {
                    kerdesek[i] = "Melyik betu tartozik " + kerdes2(i) + " " + i + kerdes(i) + "?";
                }

                for (int i = 10; i < 20; i++) {
                    kerdesek[i] =
                            "Melyik szam tatozik " + kerdes3(betuk[i - 10]) + " "
                                    + betuk[i - 10] + kerdes4(betuk[i - 10]) + "?";
                }






                kerdes.setText(kerdesek[sorrend[index+1]]);
                kerdes.setTextSize(18);
                String valasz = bevitel.getText().toString();

                valaszok[sorrend[index]]= bevitel.getText().toString();

                TextView teszt = (TextView) findViewById(R.id.hibaTv);

                teszt.setVisibility(View.INVISIBLE);
            /*
           String teszteles = "";
            for(int i =0;i<20;i++){
                teszteles = teszteles+ i+". "+helyes_valaszok[i]+"="+valaszok[i]+"\n";
            }
            teszt.setText(teszteles);

            */


                index = index +1;







                bevitel.setText("");
            }
            else{

                TextView kerdes = (TextView) findViewById(R.id.kerdes);
                bevitel.setVisibility(View.INVISIBLE);
                TextView gomb = (TextView) findViewById(R.id.mehet);
                gomb.setVisibility(View.INVISIBLE);
                int pontok = 0;
                for(int i = 0;i<20;i++){
                    if(helyes_valaszok[i].equalsIgnoreCase(valaszok[i])){
                        pontok= pontok +1;
                    }
                }

                tempPontokString= getText(R.string.betuKerdesekPontok).toString();
                kerdes.setText(tempPontokString + pontok);

            }


        }else
            {
            hibaTv.setText((getString(R.string.betuKerdesekHiba)));
        }


    }

    public void megjelenites(View view) {
            if(futas==0){
                TextView gomb2 = (TextView) findViewById(R.id.mehet2);
                gomb2.setText("Kerdes generalasa");


                TextView text1 = (TextView) findViewById(R.id.textView);
                text1.setVisibility(View.INVISIBLE);
                TextView text2 = (TextView) findViewById(R.id.textView2);
                text2.setVisibility(View.INVISIBLE);



            futas = futas +1;
            }
            else{
                TextView gomb2 = (TextView) findViewById(R.id.mehet2);
                gomb2.setVisibility(View.INVISIBLE);
                TextView bevitel = (TextView) findViewById(R.id.bevitel);
                bevitel.setVisibility(View.VISIBLE);
                TextView gomb = (TextView) findViewById(R.id.mehet);
                gomb.setVisibility(View.VISIBLE);
                Intent intent = getIntent();
                String[] betuk = intent.getStringArrayExtra("chars");
                TextView kerdes = (TextView) findViewById(R.id.kerdes);


                //TODO keredesek generálására egy külön függvény?
                String[] kerdesek = new String[20];

                for (int i = 0; i < 10; i++) {
                    //melyik betu tartozik + a/az + "" + szam + hoz/hez
                    kerdesek[i] = "Melyik betu tartozik " + kerdes2(i) + " " + i + kerdes(i) + "?";
                    kerdesek[i] = (getString(R.string.kerdesgeneralasBetu)) + i;
                }

                for (int i = 10; i < 20; i++) {
                    kerdesek[i] =
                            "Melyik szam tatozik " + kerdes3(betuk[i - 10].toUpperCase()) + " "
                                    + betuk[i - 10].toUpperCase() + kerdes4(betuk[i - 10].toUpperCase()) + "?";

                }

                kerdes.setText(kerdesek[sorrend[index]]);
                //valaszok[sorrend[index]]= bevitel.getText().toString();



                TextView teszt = (TextView) findViewById(R.id.hibaTv);
                teszt.setVisibility(View.VISIBLE);
                /*
                String teszteles = "";
                for(int i =0;i<20;i++){
                    teszteles = teszteles+ i+". "+helyes_valaszok[i]+"="+valaszok[i]+"\n";
                }
                teszt.setText(teszteles);
                */


            }





    }

    //check if bevitel is integer
    public static boolean isInteger(String s) {
        return isInteger(s,10);
    }

    public static boolean isInteger(String s, int radix) {
        if(s.isEmpty()) return false;
        for(int i = 0; i < s.length(); i++) {
            if(i == 0 && s.charAt(i) == '-') {
                if(s.length() == 1) return false;
                else continue;
            }
            if(Character.digit(s.charAt(i),radix) < 0) return false;
        }
        return true;
    }

    public void skipQuestions(View v){
        startActivity(new Intent(this,MainMenu.class));
        finish();
    }
}