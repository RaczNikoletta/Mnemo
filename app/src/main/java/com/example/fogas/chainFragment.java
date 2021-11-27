package com.example.fogas;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fogas.Models.PegDataModel;
import com.example.fogas.Models.SequenceDataModel;
import com.example.fogas.Models.UserDataModel;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import io.realm.Realm;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class chainFragment extends Fragment {

    private Button btn1, btn2;
    private EditText edtxt;
    private TextView txtvw1, txtvw2, txtvw3;
    private Realm realm;
    private UserDataModel user;
    private String newstring;
    private String[] splitStrings;
    private ArrayList<String> theStrings = new ArrayList<String>();
    private SequenceDataModel sequenceUser;
    private HashMap<String, String> indexAdapter = new HashMap<String, String>();
    private PegDataModel localStorage;

    public chainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chain, container, false);
        edtxt = (EditText) view.findViewById(R.id.editTextTextPersonName);
        txtvw1 = (TextView) view.findViewById(R.id.chainViewText1);
        txtvw2 = (TextView) view.findViewById(R.id.chainViewText2);
        realm = Realm.getDefaultInstance();
        user = realm.where(UserDataModel.class).equalTo("loggedIn", true).findFirst();
        localStorage = user.getPegs();
/*
        view.findViewById(R.id.chainBtn).setOnClickListener(new View.OnClickListener(){
            @Override

            public void onClick(View v){
                try {
                    String string1 = edtxt.getText().toString();

                } catch (Throwable e){
                    Toast.makeText(getContext(), e.toString(), Toast.LENGTH_LONG).show();
                    Log.wtf("EXCEPTION","Edit text exception\n"+e.toString());
                    System.out.println(e.toString());
                }

               //Long long1 = Long.getLong(string1);
               //testArray.add(long1);
            }
        });
*/
        view.findViewById(R.id.listButton).setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                //todo wordFromDatabaseList indexnek veszi a beírt számokat, 9 fölött ez gond ha léteznek custom fogasok
                //todo listázni(setText)-hez függvényt és nem a beépített list toString()-je csinálja őket
                //todo módosítani a beírt szöveget (textview helyett edittextbe adja át) és el lehessen menteni (???)
                try {
                    if (edtxt.getText().toString() == "Számsort kérek!")
                    {
                        edtxt.getText().clear();
                    }
                     newstring = edtxt.getText().toString();
                     theStrings = wordFromDatabaseList(newstring, edtxt, " ");
                     txtvw1.setText(newstring);
                     txtvw2.setText(listParsedWords(theStrings));

                     //user.setOneSequence(user.getSequences().first());
                } catch (Throwable e)
                {
                    Toast.makeText(getContext(), "hint" + " " + e.toString(), Toast.LENGTH_LONG).show();
                }
            }
        });
        return view;
    }
    //todo indexAdapter megvalósítás külön függvénybe
    @RequiresApi(api = Build.VERSION_CODES.N)
    private ArrayList<String> wordFromDatabaseList(String bunchOfNumbers, EditText beviteliMezo, String regexToSplit){
        String localString = beviteliMezo.getText().toString();
        String[] bunchOfNumbersString = bunchOfNumbers.split(regexToSplit);
        String splitLocalStrings[] = localString.split(regexToSplit);
        ArrayList<String> innerArray = new ArrayList<String>();

        int idx = 0;
        indexAdapter.clear();
        //mapbe beleteszi a szó <-> szám párokat (összes)
        while (idx < user.getPegs().getPegs().stream().count())
        {
            indexAdapter.put(Integer.toString(user.getPegs().getOnePeg(idx).getNum()),user.getPegs().getOnePeg(idx).getWord());
            idx++;
        }
        System.out.println(indexAdapter.toString());
        for (String s : splitLocalStrings){
            innerArray.add(indexAdapter.get(s));
        }
        System.out.println(innerArray.toString());
        return innerArray;
    };

    private String listParsedWords(ArrayList<String> cleanStrings){
        String theReturnString = new String();
        for(String s : cleanStrings)
        {
            theReturnString += s + " ";
        }
        return theReturnString;
    }

    private Long chainedNumbers(ArrayList<String> stringArray){
        String returnNumbers = null;
        for (String iterate : stringArray){
            returnNumbers.concat(iterate);
        }
        return Long.parseLong(returnNumbers);
    };

    //private Long fastReturnInLong(Long ... szamok){
    //    return chainedNumbers(chainStringAdapter(szamok));
    //}

    private Map makePair(Long theKey, String theValue){
        Map<Long, String> thePair = null;
        thePair.put(theKey, theValue);
        return thePair;
    };

    //hülyeség
    //private String getWordByIdx(int idx)
    //{
    //    return user.getPegs().getOnePeg(Integer.parseInt(edtxt.getText().toString())).getWord().toString();
    //}
}