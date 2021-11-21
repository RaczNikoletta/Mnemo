package com.example.fogas;

import static androidx.core.content.ContextCompat.getSystemService;
import static io.realm.Realm.getApplicationContext;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.inputmethodservice.Keyboard;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fogas.Models.PegDataModel;
import com.example.fogas.Models.PegModel;
import com.example.fogas.Models.UserDataModel;

import java.util.ArrayList;
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
    String [] helyes_valaszok = new String[10];
    String [] valaszok = new String[10];
    private int pontok = 0;
    private ImageButton helpImage;
    private ImageView imageViewHint;


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

        try{
            updaterRealm = Realm.getDefaultInstance();
            user = updaterRealm.where(UserDataModel.class).equalTo("loggedIn",true).findFirst();
            if(user != null){
                pegmodelAboveNine = updaterRealm.where(PegDataModel.class).equalTo("userName",user.getUserName()).findFirst();
                if(pegmodelAboveNine != null){
                    pegRealmListAboveNine = pegmodelAboveNine.getPegs();
                    for(int i=0;i<pegRealmListAboveNine.size();i++){
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



        /*if(!checkIfWordsInDatabase())

            if(getContext()!= null) {
                new AlertDialog.Builder(getContext())
                        .setTitle(R.string.noPegwordsinDB)
                        .setMessage(R.string.noPegwordsinDB)

                        // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })

                        // A null listener allows the button to dismiss the dialog and take no further action.
                        .setIcon(getResources().getDrawable(R.drawable.ic_baseline_error_24))
                        .show();
            }

        }*/
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




        View view =  inflater.inflate(R.layout.fragment_word_practice,container,false);






        // Inflate the layout for this fragment


        kerdes = (TextView) view.findViewById(R.id.szoveg_kerdes);
        szoveg_bevitel = (TextView) view.findViewById(R.id.szoveg_bevitel);
        helpImage = view.findViewById(R.id.helpImage);
        imageViewHint = view.findViewById(R.id.imageViewHint);
        imageViewHint.setVisibility(View.INVISIBLE);





        view.findViewById(R.id.szoveg_gomb).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean ellenorzes = false;
                if(index==0){


                        valaszok[index] = szoveg_bevitel.getText().toString();

                        tempAboveNine = new PegModel();
                        for(int i = 0;i<10;i++){
                            tempAboveNine = (PegModel) pegRealmListAboveNine.get(i);
                            if(tempAboveNine.getWord().toString()==null){
                                helyes_valaszok[i] = "";
                            }
                            else{
                                helyes_valaszok[i] = tempAboveNine.getWord().toString();
                            }


                        }


                        //ekkor megkapja az 1-es indexü elem betüjét


                        kerdes.setText(kerdes.getResources().getString(R.string.egyes_szo));
                        ellenorzes = true;
                        //szoveg_bevitel.setText("");


                        index = index + 1;



//hello

                }
                if(index>0&&index<10){


                        if (index == 2) {
                            kerdes.setText(kerdes.getResources().getString(R.string.kettes_szo));
                        }
                        if (index == 3) {
                            kerdes.setText(kerdes.getResources().getString(R.string.harmas_szo));
                        }
                        if (index == 4) {
                            kerdes.setText(kerdes.getResources().getString(R.string.negyes_szo));
                        }
                        if (index == 5) {
                            kerdes.setText(kerdes.getResources().getString(R.string.otos_szo));
                        }
                        if (index == 6) {
                            kerdes.setText(kerdes.getResources().getString(R.string.hatos_szo));
                        }
                        if (index == 7) {
                            kerdes.setText(kerdes.getResources().getString(R.string.hetes_szo));
                        }
                        if (index == 8) {
                            kerdes.setText(kerdes.getResources().getString(R.string.nyolcas_szo));
                        }
                        if (index == 9) {
                            kerdes.setText(kerdes.getResources().getString(R.string.kilences_szo));
                        }


                        valaszok[index-1] = szoveg_bevitel.getText().toString();
                        pegAboveNine = new ArrayList<>();
                        pegAboveNine.add(String.valueOf(tempAboveNine.getWord()));


                        ellenorzes = true;
                        szoveg_bevitel.setText("");
                        index = index + 1;


                }

                else{
                    valaszok[index-1]=szoveg_bevitel.getText().toString();
                    for(int i = 0;i<10;i++){
                        if(helyes_valaszok[i].equals(valaszok[i])){
                            pontok= pontok+1;
                        }
                    }
                    hideKeyboard(getActivity()); //won't work
                    String teszteles = "";



                    kerdes.setText(pontok+"");


                    try {
                        FragmentManager fm = getFragmentManager();
                        FragmentTransaction ft = fm.beginTransaction();
                        ft.replace(R.id.container,new PracticeFragment(),"Add hints fragment")
                                .addToBackStack(null)
                                .commit();


                    }catch(Throwable e){
                        Log.d("practicefragment","letterpractice click error "+ e.toString());
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







    }









