package com.example.fogas;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fogas.Models.PegDataModel;
import com.example.fogas.Models.PegModel;
import com.example.fogas.Models.UserDataModel;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class letterUpdateFragment extends Fragment {

    private Realm updaterRealm;
    private String temp;
    private UserDataModel user;
    private PegDataModel peg;
    private RealmList<PegModel> pegs;
    private EditText eLetter;
    private EditText eWord;
    private String let;
    private String word;
    private int[] ids;
    private ArrayList<String> pegAboveNine;
    private Spinner aboveNineSpinner;
    private EditText pegLetterAboveNineEt;
    private EditText pegWordAboveNineEt;
    private  ArrayAdapter<String> pegAdapter;
    private PegDataModel pegmodelAboveNine;
    private RealmList pegRealmListAboveNine;
    private PegModel tempAboveNine;
    private String selectedFromSpinner;
    private TextView newNumberTv;

    public letterUpdateFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment letterUpdateFragment.
     */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
                View view = inflater.inflate(R.layout.fragment_letter_update, container, false);
                setRetainInstance(true);


        ids = new int[]{R.id.pegletterTv0, R.id.pegletterTv1, R.id.pegletterTv2, R.id.pegletterTv3, R.id.pegletterTv4,
                R.id.pegletterTv5, R.id.pegletterTv6, R.id.pegletterTv7, R.id.pegletterTv8,
                R.id.pegletterTv9, R.id.pegwordTv0, R.id.pegwordTv1, R.id.pegwordTv2, R.id.pegwordTv3, R.id.pegwordTv4, R.id.pegwordTv5,
                R.id.pegwordTv6, R.id.pegwordTv7, R.id.pegwordTv8, R.id.pegwordTv9};

        /*private ArrayList<String> pegAboveNine;
        private Spinner aboveNineSpinner;
        private EditText pegLetterAboveNineEt;
        private EditText pegWordAboveNineEt;
        private  ArrayAdapter<String> pegAdapter;*/

        pegAboveNine = new ArrayList<>();
        aboveNineSpinner = (Spinner) view.findViewById(R.id.aboveNineSpinner);
        pegLetterAboveNineEt = (EditText) view.findViewById(R.id.pegLetterAboveNineEt);
        pegWordAboveNineEt = (EditText) view.findViewById(R.id.pegWordAboveNineEt);
        newNumberTv = (TextView) view.findViewById(R.id.newNumberTv);


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
        pegAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1,pegAboveNine);
        aboveNineSpinner.setAdapter(pegAdapter);

        view.findViewById(R.id.newNumberTv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.container, new addPegAboveNineFragment(), "addPegAboveNine")
                        .addToBackStack(null)
                        .commit();

            }
        });

        ((Spinner) view.findViewById(R.id.aboveNineSpinner)).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View v, int position, long l) {
                selectedFromSpinner = aboveNineSpinner.getSelectedItem().toString();
                for(int i=0;i<pegRealmListAboveNine.size();i++) {
                    tempAboveNine = new PegModel();
                    tempAboveNine = (PegModel) pegRealmListAboveNine.get(i);
                    if (tempAboveNine != null) {
                        if(tempAboveNine.getNum() == Integer.parseInt(selectedFromSpinner))
                        {
                            if(tempAboveNine.getLetter() == null){
                                pegLetterAboveNineEt.setText("");
                            }else{
                            pegLetterAboveNineEt.setText(tempAboveNine.getLetter());}
                            if(tempAboveNine.getWord() == null) {
                                pegWordAboveNineEt.setText("");
                            }else{
                            pegWordAboveNineEt.setText(tempAboveNine.getWord());}
                        }
                    }
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        PegModel p;
        try {
            updaterRealm = Realm.getDefaultInstance();
            user = updaterRealm.where(UserDataModel.class).equalTo("loggedIn", true).findFirst();
            if (user != null) {
                peg = updaterRealm.where(PegDataModel.class).equalTo("userName", user.getUserName()).findFirst();
                if (peg != null) {
                    pegs = peg.getPegs();
                    //set rows from database
                    for (int i = 0; i < pegs.size(); i++) {
                        if(pegs.get(i).getNum()<10){
                        p = pegs.get(i);
                        eLetter = view.findViewById(ids[i]);
                        eWord = view.findViewById(ids[i + 10]);
                        if (p != null) {
                            eLetter.setText(p.getLetter());
                            eWord.setText(p.getWord());
                        } else {
                            eLetter.setText("");
                            eWord.setText("");
                        }

                    }}
                } else {
                    Toast.makeText(getContext(), "PegDataModel query returned null object (letterUpdateFragment.java function startQuery())", Toast.LENGTH_LONG).show();
                }
            } else {

                new AlertDialog.Builder(getContext())
                        .setTitle(R.string.databaseUpdated)
                        .setMessage(R.string.databaseUpdated2)

                        // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                startActivity(new Intent(getContext(), Login.class));
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })

                        // A null listener allows the button to dismiss the dialog and take no further action.
                        .setIcon(getResources().getDrawable(R.drawable.ic_baseline_error_24))
                        .show();
            }

        } catch (Throwable e) {
            Toast.makeText(getContext(), e.toString(), Toast.LENGTH_LONG).show();

        }


        view.findViewById(R.id.saveUpdatesBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    PegDataModel newDatas = new PegDataModel();
                    newDatas.setPegs(new RealmList<PegModel>());
                    updaterRealm.executeTransaction(r -> {
                        for (int i = 0; i < pegs.size(); i++) {
                            PegModel p = new PegModel();
                            eLetter = view.findViewById(ids[i]);
                            eWord = view.findViewById(ids[i + 10]);
                            Log.v("letterupdate", i + " " + ids[i] + "ennyi");
                            let = eLetter.getText().toString();
                            word = eWord.getText().toString();
                            p.setLetter(let);
                            p.setWord(word);
                            p.setNum(i);
                            newDatas.setOnePeg(p);
                        }
                        newDatas.setUserName(user.getUserName());
                        updaterRealm.insertOrUpdate(newDatas);
                    });

                    new AlertDialog.Builder(getContext())
                            .setTitle(R.string.loginrequest)
                            .setMessage(R.string.loginrequest2)

                            // Specifying a listener allows you to take an action before dismissing the dialog.
                            // The dialog is automatically dismissed when a dialog button is clicked.
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                //reload the fragment
                                public void onClick(DialogInterface dialog, int which) {
                                    try {
                                        Fragment frg = null;
                                        frg = getFragmentManager().findFragmentByTag("letterUpdate");
                                        FragmentManager fm = getFragmentManager();
                                        FragmentTransaction ft = fm.beginTransaction();
                                        ft.detach(frg);
                                        ft.attach(frg);
                                        ft.commit();
                                    } catch (Throwable e) {
                                        Log.d("practicefragment", "letterpractice click error " + e.toString());
                                    }
                                }
                            })


                            // A null listener allows the button to dismiss the dialog and take no further action.
                            .setIcon(getResources().getDrawable(R.drawable.ic_baseline_error_24))
                            .show();

                } catch (Throwable e) {
                    Toast.makeText(getContext(), "Saving issue: " + e.toString(), Toast.LENGTH_LONG).show();
                }

            }
        });
        // Inflate the layout for this fragment
        return view;


    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putIntArray("idsof",ids);
    }
}