package com.example.fogas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class game extends AppCompatActivity {
    int i=0;
    String [] betuk = new String[10];
    private Context mContext;
    private Activity mActivity;

    private ConstraintLayout mConstraintLayout;
    private Button helpButton;

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
        helpButton = (Button) findViewById(R.id.helpBtn);

        helpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Initialize a new instance of LayoutInflater service
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);

                // Inflate the custom layout/view
                View customView = inflater.inflate(R.layout.popupfogas_layout,null);

                /*
                    public PopupWindow (View contentView, int width, int height)
                        Create a new non focusable popup window which can display the contentView.
                        The dimension of the window must be passed to this constructor.

                        The popup does not provide any background. This should be handled by
                        the content view.

                    Parameters
                        contentView : the popup's content
                        width : the popup's width
                        height : the popup's height
                */
                // Initialize a new instance of popup window
                mPopupWindow = new PopupWindow(
                        customView,
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                );

                // Set an elevation value for popup window
                // Call requires API level 21
                if(Build.VERSION.SDK_INT>=21){
                    mPopupWindow.setElevation(5.0f);
                }

                // Get a reference for the custom view close button
                ImageButton closeButton = (ImageButton) customView.findViewById(R.id.ib_close);

                // Set a click listener for the popup window close button
                closeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Dismiss the popup window
                        mPopupWindow.dismiss();
                    }
                });

                /*
                    public void showAtLocation (View parent, int gravity, int x, int y)
                        Display the content view in a popup window at the specified location. If the
                        popup window cannot fit on screen, it will be clipped.
                        Learn WindowManager.LayoutParams for more information on how gravity and the x
                        and y parameters are related. Specifying a gravity of NO_GRAVITY is similar
                        to specifying Gravity.LEFT | Gravity.TOP.

                    Parameters
                        parent : a parent view to get the getWindowToken() token from
                        gravity : the gravity which controls the placement of the popup window
                        x : the popup's x location offset
                        y : the popup's y location offset
                */
                // Finally, show the popup window at the center location of root relative layout
                mPopupWindow.showAtLocation(mConstraintLayout, Gravity.CENTER,0,0);
            }

        });


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