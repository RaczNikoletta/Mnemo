package com.example.fogas;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.fogas.Models.PegDataModel;
import com.example.fogas.Models.PegModel;
import com.example.fogas.Models.SequenceDataModel;
import com.example.fogas.Models.UserDataModel;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmList;


public class sequenceUpdateFragment extends Fragment {

    private ListView sequenceListv;
    private ArrayAdapter<String> adapterofs;
    private ArrayList<String> listofs;
    private UserDataModel user;
    private Realm sequenceUpdateRealm;
    private SequenceDataModel tempseq;
    private PegDataModel pegs;
    private String clicked;

    public sequenceUpdateFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sequnce_update, container, false);

        sequenceListv = (ListView) view.findViewById(R.id.sequenceListv);
        tempseq = new SequenceDataModel();
        user = new UserDataModel();
        listofs = new ArrayList<>();
        sequenceUpdateRealm = Realm.getDefaultInstance();

        try {
        } catch (Throwable e) {
            Toast.makeText(getContext(), "makedummyexception " + e.toString(), Toast.LENGTH_LONG).show();
        }
        try {
            user = sequenceUpdateRealm.where(UserDataModel.class).equalTo("loggedIn", true).findFirst();
            for (int i = 0; i < user.getSequences().size(); i++) {
                tempseq = user.getSequences().get(i);
                StringBuilder temp = new StringBuilder();
                for (int j = 0; j < tempseq.getSequence().size(); j++) {
                    temp.append(String.valueOf(tempseq.getSequence().get(j).getNum()));
                    if(j==tempseq.getSequence().size()-1) {
                        listofs.add(String.valueOf(temp));
                    }
                }
            }


        } catch (Throwable e) {
            Toast.makeText(getContext(), "create list" + " " + e.toString(), Toast.LENGTH_LONG).show();
        }
        try {
            adapterofs = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, listofs);
            sequenceListv.setAdapter(adapterofs);
        } catch (Throwable e) {
            Toast.makeText(getContext(), "adapterset exception " + e.toString(), Toast.LENGTH_LONG).show();
        }

        sequenceListv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View v, int position, long l) {
                for(int i=0;i<listofs.size();i++){
                    sequenceListv.getChildAt(i).setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                }
                sequenceListv.getChildAt(position).setBackgroundColor(getResources().getColor(android.R.color.holo_blue_dark));
                clicked = listofs.get(position);
            }
        });
        view.findViewById(R.id.seqenceEditBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.replace(R.id.container,editSequencesFragment.newInstance(clicked),"Edit sequences")
                            .addToBackStack(null)
                            .commit();
                }catch(Throwable e){
                    Log.d("editHintsFragment","editHintsFragment click error "+ e.toString());
                }
            }
        });



        return view;
    }

    public void dummyseq(){
        sequenceUpdateRealm.executeTransaction(r -> {
            user = sequenceUpdateRealm.where(UserDataModel.class).equalTo("loggedIn", true).findFirst();
            tempseq.setUser(user);
            tempseq.setSequence(user.getPegs().getPegs());
            user.setOneSequence(tempseq);
            sequenceUpdateRealm.insertOrUpdate(user);
        });

    }
}