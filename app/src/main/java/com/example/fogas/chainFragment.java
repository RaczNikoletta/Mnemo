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
import com.example.fogas.Models.PegModel;
import com.example.fogas.Models.SequenceDataModel;
import com.example.fogas.Models.UserDataModel;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import io.realm.Realm;
import io.realm.RealmList;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class chainFragment extends Fragment {

    private Button btn1, btn2;
    private EditText edtxt ;
    private TextView txtvw1, txtvw2 ;
    private Realm realm;
    private UserDataModel user;
    private String newstring;
    private String[] splitStrings;
    private ArrayList<String> theStrings = new ArrayList<String>();
    private SequenceDataModel sequenceUser;
    private HashMap<String, String> indexAdapter = new HashMap<String, String>();
    private PegDataModel localPegStorage;
    private RealmList<String> realmStrings = new RealmList<String>();

    public chainFragment() {
        // Required empty public constructor
    }

    //todo ne induljon el amíg be nincs jelentkezve user
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chain, container, false);
        edtxt = (EditText) view.findViewById(R.id.editTextTextPersonName);
        txtvw1 = (TextView) view.findViewById(R.id.chainViewText1);
        txtvw2 = (TextView) view.findViewById(R.id.chainViewText2);
        btn1 = (Button) view.findViewById(R.id.listButton);
//        btn2 = (Button) view.findViewById(R.id.listButton2);
        realm = Realm.getDefaultInstance();
        user = realm.where(UserDataModel.class).equalTo("loggedIn", true).findFirst();
        localPegStorage = user.getPegs();

        view.findViewById(R.id.listButton).setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                try {
                        newstring = edtxt.getText().toString();
                        theStrings = wordFromDatabaseList(newstring, edtxt, " ");
                        while (theStrings.remove(null)){}
                        txtvw1.setText(newstring);
                        txtvw2.setText(listParsedWords(theStrings));

                } catch (Throwable e)
                {
                    Toast.makeText(getContext(), "hint" + " " + e.toString(), Toast.LENGTH_LONG).show();
                }
            }
        });
/*
        view.findViewById(R.id.listButton2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    ArrayList<String> storyArray = new ArrayList<String>();
                    String[] stringArray = txtvw2.getText().toString().split(" ");
                    String numbers = edtxt.getText().toString();
                    for (String s : stringArray){
                        storyArray.add(s);
                    }

                    for (int i = 0; i < user.getPegs().getPegs().size(); i++){
                        if (storyArray.equals(user.getSequences().get(i).getStory())){
                            numbers = "";
                            for (int k = 0; k < user.getSequences().size(); k++)
                            numbers += user.getSequences().get(k).getOneFromSeq(i).getNum() + " ";
                            System.out.println(numbers);
                        }
                        edtxt.setText(numbers);
                        System.out.println(storyArray.toString());
                        System.out.println(user.getSequences().get(i).getStory().toString());
                        }

                } catch (Throwable e){
                    Toast.makeText(getContext(), e.toString(), Toast.LENGTH_LONG).show();
                }
            }
        });

 */
        view.findViewById(R.id.listButton3).setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {


                try {
                    SequenceDataModel newModel = new SequenceDataModel(user);
                    RealmList<PegModel> localRealmPeg = new RealmList<PegModel>();
                    RealmList<String> localRealmStrings = new RealmList<String>();

                    realm.executeTransaction( r -> {
                        String[] storySnippets = txtvw2.getText().toString().split(" ");

                        for (int i = 0; i < storySnippets.length; i++)
                        {

                                localRealmStrings.add(storySnippets[i]);

                        }

                        for (int i = 0 ; i < user.getPegs().getPegs().size(); i++){
                            for (int k = 0; k < localRealmStrings.size(); k++){
                                if (user.getPegs().getOnePeg(i).getWord().equals(localRealmStrings.get(k))){
                                    PegModel tempPeg = new PegModel();
                                    tempPeg.setWord(user.getPegs().getOnePeg(i).getWord());
                                    tempPeg.setLetter(user.getPegs().getOnePeg(i).getLetter());
                                    tempPeg.setNum(user.getPegs().getOnePeg(i).getNum());
                                    localRealmPeg.add(tempPeg);
                                }
                            }
                        }
                        newModel.setStory(localRealmStrings);
                        newModel.setSequence(localRealmPeg);//localRealmPeg jönne ide
                        user.setOneSequence(newModel);
                        realm.insertOrUpdate(user);
                        Toast.makeText(getContext(), "Bekerült az adatbázisba!", Toast.LENGTH_LONG).show();
                    });
                } catch (Throwable e){
                    Toast.makeText(getContext(), e.toString(), Toast.LENGTH_LONG).show();
                    System.out.println(e.toString());
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
        /*
        //mapbe beleteszi a szó <-> szám párokat (összes)
        while (idx < user.getPegs().getPegs().stream().count())
        {
            indexAdapter.put(Integer.toString(user.getPegs().getOnePeg(idx).getNum()),user.getPegs().getOnePeg(idx).getWord());
            idx++;
        }
        for (String s : splitLocalStrings){
            innerArray.add(indexAdapter.get(s));
        }
        */
        for (int i = 0; i < splitLocalStrings.length; i++){
            for(int k = 0; k < user.getPegs().getPegs().size(); k++){
                if (Integer.parseInt(splitLocalStrings[i]) == user.getPegs().getOnePeg(k).getNum()){
                    innerArray.add(user.getPegs().getOnePeg(k).getWord());
                    System.out.println("Putting in" + user.getPegs().getOnePeg(k).getWord());
                }
            }
        }
        System.out.println(innerArray.toString());
        return innerArray;
    };

    private String numChainFromWords(String words)
    {
        RealmList<String> stories = new RealmList<>();
        String[] theWords = words.split(" ");
        for (String w: theWords){
            stories.add(w);
        }

        for (int i = 0; user.getSequences().size() < i; i++){
            if (stories.equals(user.getSequences().get(i).getStory()))
            {
                System.out.println("A Sequencek száma" + user.getSequences().size());
                System.out.println("A talált sequence (word)" + user.getPegs().getOnePeg(i).getWord());
                return user.getPegs().getOnePeg(i).getWord();
            }
        }
        return "Nincs ilyen!";
    };

    private String listParsedWords(ArrayList<String> cleanStrings){
        String theReturnString = new String();
        for(String s : cleanStrings)
        {
            //if (s.equals(null)){
            //    theReturnString += "";
            //}
            //else {
                theReturnString += s + " ";
            //}

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