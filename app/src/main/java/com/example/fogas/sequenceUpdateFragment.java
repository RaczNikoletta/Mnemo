package com.example.fogas;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fogas.Models.PegDataModel;
import com.example.fogas.Models.PegModel;
import com.example.fogas.Models.SequenceDataModel;
import com.example.fogas.Models.UserDataModel;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmQuery;
import io.realm.RealmResults;


public class sequenceUpdateFragment extends Fragment {

    private ListView sequenceListv;
    private ArrayAdapter<String> adapterofs;
    private ArrayList<String> listofs;
    private ArrayList<Integer> toNextFrag;
    private UserDataModel user;
    private Realm sequenceUpdateRealm;
    private SequenceDataModel tempseq;
    private ArrayList<ArrayList<Integer>> aboveNine;
    private PegDataModel pegs;
    private int seqPos;
    private String clicked="";
    private SequenceDataModel splitedseq = new SequenceDataModel();
    private RealmList<PegModel> tempPegs = new RealmList<>();
    private SequenceDataModel foundSeq = new SequenceDataModel();
    private SequenceDataModel seqToFind = new SequenceDataModel();
    RealmList<SequenceDataModel> seqs = new RealmList<>();


    public sequenceUpdateFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sequnce_update, container, false);

        sequenceListv = (ListView) view.findViewById(R.id.sequenceListv);
        user = new UserDataModel();
        listofs = new ArrayList<>();
        sequenceUpdateRealm = Realm.getDefaultInstance();
        tempseq = new SequenceDataModel();
        aboveNine = new ArrayList<>();

        try {
            //dummyseq();
        } catch (Throwable e) {
            Toast.makeText(getContext(), "makedummyexception " + e.toString(), Toast.LENGTH_LONG).show();
        }
        try {


            user = sequenceUpdateRealm.where(UserDataModel.class).equalTo("loggedIn",
                    true).findFirst();
            seqs = user.getSequences();
            for (int i = 0; i < user.getSequences().size(); i++) {
                tempseq = user.getSequences().get(i);
                StringBuilder temp = new StringBuilder();
                aboveNine.add(new ArrayList<>());
                for (int j = 0; j < tempseq.getSequence().size(); j++) {
                    temp.append(String.valueOf(tempseq.getSequence().get(j).getNum()));
                    if(tempseq.getSequence().get(j).getNum()>9){
                        aboveNine.get(i).add(j);
                    }
                    if (j == tempseq.getSequence().size()-1) {
                        listofs.add(String.valueOf(temp));
                    }
                }
            }


        } catch (Throwable e) {
            Toast.makeText(getContext(), "create list" + " " + e.toString(), Toast.LENGTH_LONG).show();
        }
        try {
            adapterofs = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, listofs) {
                @NonNull
                @Override
                public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                    View view = super.getView(position, convertView, parent);
                    TextView text = (TextView) view.findViewById(android.R.id.text1);
                    if (listofs.size() >= 1) {
                        text.setTextColor(Color.WHITE);
                        text.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                        text.setTypeface(text.getTypeface(), Typeface.BOLD);
                        text.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
                    }
                    return view;
                }
            };
            sequenceListv.setAdapter(adapterofs);
        } catch (Throwable e) {
            Toast.makeText(getContext(), "adapterset exception " + e.toString(), Toast.LENGTH_LONG).show();
        }

        sequenceListv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View v, int position, long l) {
                for (int i = 0; i < listofs.size(); i++) {
                    sequenceListv.getChildAt(i).setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                }
                sequenceListv.getChildAt(position).setBackgroundColor(getResources().getColor(android.R.color.holo_blue_dark));
                clicked = listofs.get(position);
                seqPos = position;
                seqToFind = seqs.get(position);
            }
        });
        view.findViewById(R.id.seqenceEditBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if(!clicked.equals("")) {
                        FragmentManager fm = getFragmentManager();
                        FragmentTransaction ft = fm.beginTransaction();
                        ft.replace(R.id.container, editSequencesFragment.newInstance(clicked,
                                aboveNine.get(seqPos)), "Edit sequences")
                                .addToBackStack(null)
                                .commit();
                    }
                } catch (Throwable e) {
                    Log.d("editHintsFragment", "editHintsFragment click error " + e.toString());
                }
            }
        });
        view.findViewById(R.id.deleteseqBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!clicked.equals("")) {
                    String[] splited = clicked.split("");

                    try {
                        for (int i = 0; i < user.getSequences().size(); i++) {
                            if (user.getSequences().get(i) != null) {
                                if (user.getSequences().get(i).isEqual(seqToFind)) {
                                    foundSeq = user.getSequences().get(i);
                                }
                            }
                        }
                        sequenceUpdateRealm.executeTransaction(r->{
                            if(foundSeq!=null){
                                foundSeq.deleteFromRealm();
                            }
                        });
                    }

                        catch (Throwable e) {
                            Toast.makeText(getContext(), "first" + e.toString(), Toast.LENGTH_LONG).show();
                        }
                        try{

                    } catch (Throwable e) {
                        Toast.makeText(getContext(), "find seqs"+e.toString(), Toast.LENGTH_LONG).show();
                    }


                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.replace(R.id.container, new sequenceUpdateFragment(), "Edit sequences")
                            .addToBackStack(null)
                            .commit();

                }
            }
        });


        return view;
    }

    public void dummyseq(){
        sequenceUpdateRealm.executeTransaction(r -> {
            user = sequenceUpdateRealm.where(UserDataModel.class).equalTo("loggedIn", true).findFirst();
            RealmList<PegModel> dummyseq = new RealmList<>();
            for(int i=3;i>=0;i--){
                PegModel peg = new PegModel();
                peg.setWord("dummy");
                peg.setNum(i);
                peg.setLetter("d");
                dummyseq.add(peg);
            }
            tempseq.setUser(user);
            tempseq.setSequence(dummyseq);
            tempseq.setStory(new RealmList<>());
            user.setOneSequence(tempseq);
            sequenceUpdateRealm.insertOrUpdate(user);
        });

    }
}