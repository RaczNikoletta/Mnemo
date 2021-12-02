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
    private EditText edtxt, txtvw2;
    private TextView txtvw1, txtvw3;
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
        txtvw2 = (EditText) view.findViewById(R.id.chainViewText2);
        btn1 = (Button) view.findViewById(R.id.listButton);
        btn2 = (Button) view.findViewById(R.id.listButton2);
        realm = Realm.getDefaultInstance();
        user = realm.where(UserDataModel.class).equalTo("loggedIn", true).findFirst();
        localPegStorage = user.getPegs();
//        sequenceUser = user.getSequences().first();
//        RealmList<String> provider = new RealmList<>();
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
                try {
                    //if (edtxt.getText().toString().contains("[0-9 ]+")) {
                        newstring = edtxt.getText().toString();
                        theStrings = wordFromDatabaseList(newstring, edtxt, " ");
                        txtvw1.setText(newstring);
                        txtvw2.setText(listParsedWords(theStrings));
                    //} else {
                     //   Toast.makeText(getContext(), "Csak számokat várunk!", Toast.LENGTH_LONG).show();
                    //}
                } catch (Throwable e)
                {
                    Toast.makeText(getContext(), "hint" + " " + e.toString(), Toast.LENGTH_LONG).show();
                }
            }
        });

        view.findViewById(R.id.listButton2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Toast.makeText(getContext(), " Button clicked", Toast.LENGTH_LONG).show();
                    txtvw1.setText(numChainFromWords(txtvw2.getText().toString()));
                } catch (Throwable e){
                    Toast.makeText(getContext(), e.toString(), Toast.LENGTH_LONG).show();
                }
            }
        });
        view.findViewById(R.id.listButton3).setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {


                try {
                    SequenceDataModel newModel = new SequenceDataModel(user);
                    PegModel localPegModel = new PegModel();
                    RealmList<PegModel> localRealmPeg = new RealmList<PegModel>();
                    RealmList<String> localRealmStrings = new RealmList<String>();
                    String localTheNums[] = edtxt.getText().toString().split(" ");
                    ArrayList<String> localTheStrings = new ArrayList<String>();
                    realm.executeTransaction( r -> {
                        String[] storySnippets = txtvw2.getText().toString().split(" ");
                        String[] localNums = edtxt.getText().toString().split(" ");
                        int[] localInt;
                     //   {
                     //       localInt[i] = Integer.parseInt(localNums[i]);
                     //   }

                        //System.out.println(Arrays.toString(localNums));

                        //for (String s : localNums) {
                        //    System.out.println("Idáig lefutott");
                        //    localTheStrings.add(s);
                        //}
                        for (int i = 0; i < storySnippets.length; i++)
                        {
                            //if (!storySnippets[i].equals("null")) {
                                localRealmStrings.add(storySnippets[i]);
                            //}
                        }

                        for (int i = 0 ; i < user.getPegs().getPegs().size(); i++){
                            for (int k = 0; k < localRealmStrings.size(); k++){
                                if (user.getPegs().getOnePeg(i).getWord().equals(localRealmStrings.get(k))){
                                    localPegModel.setWord(user.getPegs().getOnePeg(i).getWord());
                                    //System.out.println(user.getPegs().getOnePeg(i).getWord());
                                    localPegModel.setLetter(user.getPegs().getOnePeg(i).getLetter());
                                    //System.out.println(user.getPegs().getOnePeg(i).getLetter());
                                    localPegModel.setNum(user.getPegs().getOnePeg(i).getNum());
                                    //System.out.println(user.getPegs().getOnePeg(i).getNum());
                                    localRealmPeg.add(localPegModel);
                                }
                            }
                        }
                        //System.out.println(localPegModel.getWord() + "The outside word");

                        newModel.setStory(localRealmStrings);
                        newModel.setSequence(user.getPegs().getPegs()); //localRealmPeg jönne ide
                        user.setOneSequence(newModel);
                        realm.insertOrUpdate(user);
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
        //mapbe beleteszi a szó <-> szám párokat (összes)
        while (idx < user.getPegs().getPegs().stream().count())
        {
            indexAdapter.put(Integer.toString(user.getPegs().getOnePeg(idx).getNum()),user.getPegs().getOnePeg(idx).getWord());
            idx++;
        }
        for (String s : splitLocalStrings){
            innerArray.add(indexAdapter.get(s));
        }
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