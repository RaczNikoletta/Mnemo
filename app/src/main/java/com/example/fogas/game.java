package com.example.fogas;

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
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.fogas.Models.PegDataModel;

import io.realm.Realm;


public class game extends AppCompatActivity {


    private int i=0;
    String [] betuk = new String[10];
    private Realm letterRealm;
    private Context mContext;
    private Activity mActivity;
    private ConstraintLayout mConstraintLayout;
    private Button helpButton;
    private PopupWindow mPopupWindow;
    private TextView szam;
    private TextView part;
    private TextView hiba;
    private TextView fejlec;


    public static final String EXTRA_TEXT = "com.example.application.example.EXTRA_TEXT";
   
 //hello
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
        szam = (TextView) findViewById(R.id.betu);
        part = (TextView) findViewById(R.id.part_jelzo);
        hiba = (TextView) findViewById(R.id.hiba);
        fejlec = (TextView) findViewById(R.id.szam_fejlec);
        letterRealm = Realm.getDefaultInstance();

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
            fejlec.setText(getString(R.string.egyes));
        }
        if(i==2){
            fejlec.setText(getString(R.string.kettes));
        }
        if(i==3){
            fejlec.setText(getString(R.string.harmas));
        }
        if(i==4){
            fejlec.setText(getString(R.string.negyes));
        }
        if(i==5){
            fejlec.setText(getString(R.string.otos));
        }
        if(i==6){
            fejlec.setText(getString(R.string.hatos));
        }
        if(i==7){
            fejlec.setText(getString(R.string.hetes));
        }
        if(i==8){
            fejlec.setText(getString(R.string.nyolcas));
        }
        if(i==9){
            fejlec.setText(getString(R.string.kilences));
        }


    }

    public void katthozzad(View view) {


        String betu= szam.getText().toString();
        if(i<9) {
            if (betu.length() == 1 && betu.matches("[a-zA-Z]+")) {
                betuk[i] = betu;
                saveData(betu);
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
            saveData(betu);

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

    private  void saveData (String b){

        letterRealm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                PegDataModel peg = new PegDataModel();
                if(i==0){
                    peg.setPegNum(i);
                }
                else{
                    peg.setPegNum(i-1);
                }

                peg.setPegLetter(b);
                peg.setPegWord("");
                realm.insertOrUpdate(peg);
            }

        }, new Realm.Transaction.OnSuccess(){
            @Override
            public void onSuccess(){
                //Transaction successfull
                Toast.makeText(game.this,"Success " ,Toast.LENGTH_LONG).show();
            }
        }, new Realm.Transaction.OnError(){
            @Override
            public  void onError(Throwable error){
                //Transaction failed and automatically canceled
                String errors = error.toString();
                Toast.makeText(game.this,errors,Toast.LENGTH_LONG).show();
            }
        });

    }


}