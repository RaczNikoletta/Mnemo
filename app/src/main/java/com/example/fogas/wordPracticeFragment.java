package com.example.fogas;

import static androidx.core.content.ContextCompat.getSystemService;
import static io.realm.Realm.getApplicationContext;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.inputmethodservice.Keyboard;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fogas.Models.PegDataModel;
import com.example.fogas.Models.PegModel;
import com.example.fogas.Models.Progress;
import com.example.fogas.Models.ProgressDataModel;
import com.example.fogas.Models.UserDataModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.zip.Inflater;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link wordPracticeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class wordPracticeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Realm wordRealm;
    boolean ifExists = false;
    private TextView kerdes;
    private int index = 0;
    private String nyelv;
    private TextView szoveg_bevitel;
    private Realm updaterRealm;
    private UserDataModel user;
    private RealmList pegRealmListAboveNine;
    private PegDataModel pegmodelAboveNine;
    private PegModel tempAboveNine;
    private ArrayList<String> pegAboveNine;
    private String selectedFromSpinner;
    private Spinner aboveNineSpinner;
    private EditText pegLetterAboveNineEt;
    private EditText pegWordAboveNineEt;
    private String [] helyes_valaszok = new String[10];
    private String [] valaszok = new String[10];
    private int pontok = 0;
    private ImageButton helpImageBtn;
    private ImageView imageViewHint;
    private long tStart;
    private long tEnd;
    private long tDelta;
    private double elapsedSeconds;
    private ArrayList<Integer> indexek = new ArrayList<>();
    private TextView gomb;
    private int max = 0;
    int [] bekerultSzamok = new int[10];
    HashMap<Integer,Bitmap> bmp = new HashMap<>();

    private int db = 0;
    private int helyes = 0;


    public wordPracticeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment wordPracticeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static wordPracticeFragment newInstance(String param1, String param2) {
        wordPracticeFragment fragment = new wordPracticeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        //WORDPRACTICE GameID = 2
        try{
            updaterRealm = Realm.getDefaultInstance();
            user = updaterRealm.where(UserDataModel.class).equalTo("loggedIn",true).findFirst();
            if(user != null){
                pegmodelAboveNine = updaterRealm.where(PegDataModel.class).equalTo("userName",user.getUserName()).findFirst();
                if(pegmodelAboveNine != null){
                    pegRealmListAboveNine = pegmodelAboveNine.getPegs();
                    for(int i=0;i<pegRealmListAboveNine.size();i++){
                        max = pegRealmListAboveNine.size();
                        tempAboveNine = new PegModel();
                        tempAboveNine = (PegModel) pegRealmListAboveNine.get(i);
                        if(tempAboveNine!=null) {
                            if(tempAboveNine.getNum()>9) {
                                try {
                                    pegAboveNine.add(String.valueOf(tempAboveNine.getNum()));
                                }catch(Throwable e){
                                    Toast.makeText(getContext(),"for loop add: " + i +" "+e.toString(),Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                    }
                }
            }

        }catch (Throwable e){
            Toast.makeText(getContext(),e.toString(),Toast.LENGTH_LONG).show();
        }






        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }







    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setRetainInstance(true);
        tStart = System.currentTimeMillis();




        View view =  inflater.inflate(R.layout.fragment_word_practice,container,false);






        // Inflate the layout for this fragment


        kerdes = (TextView) view.findViewById(R.id.szoveg_kerdes);
        szoveg_bevitel = (TextView) view.findViewById(R.id.szoveg_bevitel);
        helpImageBtn = view.findViewById(R.id.helpImage);
        imageViewHint = view.findViewById(R.id.imageViewHint);
        imageViewHint.setVisibility(View.INVISIBLE);
        gomb = (TextView) view.findViewById(R.id.szoveg_gomb);



        helpImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext()," onclicked hint: "+index,Toast.LENGTH_LONG).show();
                if(index!=0) {
                    if (bmp.get(index - 1) != null) {
                        imageViewHint.setVisibility(View.VISIBLE);
                        imageViewHint.setImageBitmap(Bitmap.createBitmap(bmp.get(index -1)));
                    }
                }else{
                    if (bmp.get(index) != null) {
                        imageViewHint.setVisibility(View.VISIBLE);
                        imageViewHint.setImageBitmap(Bitmap.createBitmap(bmp.get(index)));
                    }else{
                        Toast.makeText(getContext(),R.string.noHintInDB,Toast.LENGTH_LONG).show();
                    }
                }


            }
        });





        view.findViewById(R.id.szoveg_gomb).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String string = szoveg_bevitel.getText().toString();
                boolean numeric = true;
                try {
                    Double num = Double.parseDouble(string);
                } catch (NumberFormatException e) {
                    numeric = false;
                }

                if (numeric||db==0) {
                    if (index == 0) {


                        valaszok[index] = szoveg_bevitel.getText().toString();



                        //ekkor megkapja az 1-es indexü elem betüjét




                        //szoveg_bevitel.setText("");

                        if (db == 0) {
                            db = 1;
                            kerdes.setVisibility(View.VISIBLE);
                            szoveg_bevitel.setVisibility(View.VISIBLE);
                            helpImageBtn.setVisibility(View.VISIBLE);
                            gomb.setText(kerdes.getResources().getString(R.string.hozzaadGomb));

                            tempAboveNine = new PegModel();

                            for (int i = 0; i < max; i++) {
                                tempAboveNine = (PegModel) pegRealmListAboveNine.get(i);
                                if(!tempAboveNine.getWord().equals("")){
                                    indexek.add(tempAboveNine.getNum());
                                    helyes = helyes + 1;

                                }

                            }


                            Collections.shuffle(indexek);

                            for (int i = 0; i < helyes; i++) {
                                tempAboveNine = (PegModel) pegRealmListAboveNine.get(indexek.get(i));

                                helyes_valaszok[i] = tempAboveNine.getWord().toString();
                                bekerultSzamok[i] =tempAboveNine.getNum();

                                if(user.getHints().getOneHint(tempAboveNine.getNum()).getImage()!=null)
                                       bmp.put(i,BitmapFactory.decodeByteArray(user.getHints().getOneHint(tempAboveNine.getNum()).getImage(),0,
                                        user.getHints().getOneHint(tempAboveNine.getNum()).getImage().length));


                            }
                            kerdes.setText(kerdes.getResources().getString(R.string.kerdes_szo) + " " + helyes_valaszok[index] + "?");

                        } else {

                            index = index + 1;
                        }


//hello

                    }
                    if (index > 0 && db == 1) {
                        if (index > 0 && (index < 10&& index<helyes)) {



                            kerdes.setText(kerdes.getResources().getString(R.string.kerdes_szo) + " " + helyes_valaszok[index] + "?");


                            valaszok[index - 1] = szoveg_bevitel.getText().toString();
                            if (indexek.get(index - 1).equals(Integer.parseInt(valaszok[index - 1]))) {
                                LayoutInflater inf = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                View popupView = inf.inflate(R.layout.right_answer_layout, null);
                                ImageView image = popupView.findViewById(R.id.rightIV);

                                // show the popup window
                                // which view you pass in doesn't matter, it is only used for the window tolken
                               Toast toast = new Toast(getContext());
                               toast.setGravity(Gravity.CENTER,0,0);
                               toast.setView(popupView);
                               toast.show();
                            } else {
                                LayoutInflater inf = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                View popupView = inf.inflate(R.layout.wrong_answer_layout, null);
                                ImageView image = popupView.findViewById(R.id.wrongIv);

                                // show the popup window
                                // which view you pass in doesn't matter, it is only used for the window tolken
                                Toast toast = new Toast(getContext());
                                toast.setGravity(Gravity.CENTER,0,0);
                                toast.setView(popupView);
                                toast.show();
                            }
                            pegAboveNine = new ArrayList<>();
                            pegAboveNine.add(String.valueOf(tempAboveNine.getWord()));


                            szoveg_bevitel.setText("");
                            index = index + 1;
                            imageViewHint.setVisibility(View.INVISIBLE);


                        } else {

                            valaszok[index - 1] = szoveg_bevitel.getText().toString();
                            for (int i = 0; i < helyes; i++) {
                                if (i == helyes-1) {
                                    tEnd = System.currentTimeMillis();
                                    tDelta = tEnd - tStart;
                                    elapsedSeconds = tDelta / 1000.0;

                                }
                                if (indexek.get(i).equals(Integer.parseInt(valaszok[i]))) {
                                    pontok = pontok + 1;

                                }
                            }
                            hideKeyboard(getActivity()); //won't work


                            kerdes.setText(pontok + "");
                            insertProgress();


                        try {
                            FragmentManager fm = getFragmentManager();
                            FragmentTransaction ft = fm.beginTransaction();
                            ft.replace(R.id.container, new PracticeFragment(), "Add hints fragment")
                                    .addToBackStack(null)
                                    .commit();


                        } catch (Throwable e) {
                            Log.d("practicefragment", "letterpractice click error " + e.toString());
                        }


                        }
                    }
                }
            }
        });







        return view;





    }

    //TODO ne csak az elsőt nézze, hanem for ciklusban
    public boolean checkIfWordsInDatabase(){
        try{
            wordRealm = Realm.getDefaultInstance();
            wordRealm.executeTransaction(r->{
                UserDataModel loggedUsr = wordRealm.where(UserDataModel.class).equalTo("loggedIn",true).findFirst();
                if(loggedUsr != null) {
                    PegDataModel pegWord = wordRealm.where(PegDataModel.class).equalTo("userName", loggedUsr.getUserName()).findFirst();
                    if (pegWord != null) {
                        PegModel firstPeg = pegWord.getOnePeg(0);
                        if(firstPeg != null){
                            if(firstPeg.getWord() != null){
                                ifExists = true;
                            }
                        }
                    }


                }

            });

        }catch(Throwable e)
        {
            Toast.makeText(getContext(), e.toString(), Toast.LENGTH_LONG).show();
            return false;

        }
        return ifExists;
    }

    public void insertProgress(){
        updaterRealm.executeTransaction(r-> {
            user = updaterRealm.where(UserDataModel.class).equalTo("loggedIn", true).findFirst();
            assert user != null;
            Progress newProg = new Progress(2);
            ProgressDataModel progress = user.getProgress();
            newProg.setTimeInGame(elapsedSeconds / 60);
            newProg.addResult(pontok);
            Date now = new Date();
            progress.addProgress(newProg,now);
            updaterRealm.insertOrUpdate(user);
        });



    }







    }









