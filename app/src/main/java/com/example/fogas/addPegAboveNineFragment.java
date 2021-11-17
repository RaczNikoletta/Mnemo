package com.example.fogas;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fogas.Models.PegModel;
import com.example.fogas.Models.UserDataModel;

import io.realm.Realm;

public class addPegAboveNineFragment extends Fragment {

    private EditText numberabovenEt;
    private EditText letterabovenEt;
    private EditText wordabovenEt;
    private Button saveabovenBtn;
    private Realm aboveNineRealm;
    private PegModel peg;
    private int emptyCounter = 0;
    private int failCounter = 0;
    private UserDataModel user;


    public addPegAboveNineFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_peg_above_nine, container, false);
        numberabovenEt = view.findViewById(R.id.numberabovenEt);
        letterabovenEt = view.findViewById(R.id.letterabovenEt);
        wordabovenEt = view.findViewById(R.id.wordabovenEt);
        saveabovenBtn = view.findViewById(R.id.saveabovenBtn);
        view.findViewById(R.id.saveabovenBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    aboveNineRealm = Realm.getDefaultInstance();
                    aboveNineRealm.executeTransaction(r->{
                    peg = new PegModel();
                    if (TextUtils.isEmpty(numberabovenEt.getText().toString())) {
                        /*new AlertDialog.Builder(getContext())
                                .setTitle(R.string.numberMandatory)
                                .setMessage(R.string.numberMandatory)

                                // Specifying a listener allows you to take an action before dismissing the dialog.
                                // The dialog is automatically dismissed when a dialog button is clicked.
                                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                })
                                // A null listener allows the button to dismiss the dialog and take no further action.
                                .setIcon(getResources().getDrawable(R.drawable.ic_baseline_error_24))
                                .show();*/
                        numberabovenEt.setError(getString(R.string.numberMandatory));
                    } else {
                        if(isInteger(numberabovenEt.getText().toString(),10) && ((Integer.parseInt(numberabovenEt.getText().toString())) >9 && (Integer.parseInt(numberabovenEt.getText().toString()))<=99  )){
                        peg.setNum(Integer.parseInt(numberabovenEt.getText().toString()));
                    }else{

                            new AlertDialog.Builder(getContext())
                                    .setTitle(R.string.numberError)
                                    .setMessage(R.string.numberError)

                                    // Specifying a listener allows you to take an action before dismissing the dialog.
                                    // The dialog is automatically dismissed when a dialog button is clicked.
                                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    })
                                    // A null listener allows the button to dismiss the dialog and take no further action.
                                    .setIcon(getResources().getDrawable(R.drawable.ic_baseline_error_24))
                                    .show();
                        }}
                    if(TextUtils.isEmpty(letterabovenEt.getText().toString())){
                        emptyCounter++;
                        peg.setLetter("");

                    }else
                    {
                        peg.setLetter(letterabovenEt.getText().toString());
                    }if(TextUtils.isEmpty(wordabovenEt.getText().toString())){
                        emptyCounter++;
                        if(emptyCounter == 2){
                            new AlertDialog.Builder(getContext())
                                    .setTitle(R.string.letterorwordmand)
                                    .setMessage(R.string.letterorwordmand)

                                    // Specifying a listener allows you to take an action before dismissing the dialog.
                                    // The dialog is automatically dismissed when a dialog button is clicked.
                                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    })
                                    // A null listener allows the button to dismiss the dialog and take no further action.
                                    .setIcon(getResources().getDrawable(R.drawable.ic_baseline_error_24))
                                    .show();
                        }else{
                            peg.setWord("");
                        }

                    }else{
                        peg.setWord(wordabovenEt.getText().toString());
                    }

                    if(emptyCounter<2 && (!TextUtils.isEmpty(numberabovenEt.getText().toString())) ){
                        aboveNineRealm.insertOrUpdate(peg);
                        user = aboveNineRealm.where(UserDataModel.class).equalTo("loggedIn",true).findFirst();
                        user.getPegs().setOnePeg(peg);
                        aboveNineRealm.insertOrUpdate(user);

                        new AlertDialog.Builder(getContext())
                                .setTitle(R.string.databaseUpdated)
                                .setMessage(R.string.databaseUpdated2)

                                // Specifying a listener allows you to take an action before dismissing the dialog.
                                // The dialog is automatically dismissed when a dialog button is clicked.
                                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        try {
                                            FragmentManager fm = getFragmentManager();
                                            FragmentTransaction ft = fm.beginTransaction();
                                            ft.replace(R.id.container, new letterUpdateFragment(), "letterUpdate")
                                                    .addToBackStack(null)
                                                    .commit();
                                        } catch (Throwable e) {
                                            Toast.makeText(getContext(), "Fragment change error " + e.toString(), Toast.LENGTH_LONG).show();
                                        }

                                    }
                                })

                                // A null listener allows the button to dismiss the dialog and take no further action.
                                .setIcon(getResources().getDrawable(R.drawable.ic_baseline_done_outline_24))
                                .show();
                    }
                    });
                }catch(Throwable e){
                    Toast.makeText(getContext(),e.toString(),Toast.LENGTH_LONG).show();
                }



            }
        });
        return view;
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
}