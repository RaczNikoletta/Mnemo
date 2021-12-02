package com.example.fogas;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fogas.Models.PegModel;
import com.example.fogas.Models.SequenceDataModel;
import com.example.fogas.Models.UserDataModel;

import java.util.ArrayList;
import java.util.Arrays;

import io.realm.Realm;
import io.realm.RealmList;

public class editSequencesFragment extends Fragment {
    private  String sequence;
    private EditText storyEt;
    private Realm editSequenceRealm;
    private UserDataModel user;
    private TextView seqTv;
    SequenceDataModel splitedseq = new SequenceDataModel();
    RealmList<PegModel> pegs = new RealmList<>();
    SequenceDataModel foundSeq = new SequenceDataModel();
    //private ArrayList<String> story = new ArrayList<>();
    StringBuilder storyBuilder = new StringBuilder();
    private RealmList<String> story;
    private ArrayList<Integer> aboveNineIndexes;
    private Button saveStoryBt;

    public static editSequencesFragment newInstance(String sequence, ArrayList<Integer>aboveNineIndexes) {
        editSequencesFragment f = new editSequencesFragment();
        Bundle args = new Bundle();
        f.setArguments(args);
        args.putString("sequence", sequence);
        args.putIntegerArrayList("indexes",aboveNineIndexes);
        f.setArguments(args);
        return f;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_sequences, container, false);
        storyEt = view.findViewById(R.id.storyEt);
        seqTv = view.findViewById(R.id.seqTv);
        story = new RealmList<>();
        aboveNineIndexes = new ArrayList<>();
        try {
            Bundle args = getArguments();
            sequence = args.getString("sequence", "");
            aboveNineIndexes = args.getIntegerArrayList("indexes");
        } catch (Throwable e) {
            Toast.makeText(getContext(), "arguments error " + e.toString(), Toast.LENGTH_LONG).show();
        }
        String[] splited = sequence.split("");

        try {
            editSequenceRealm = Realm.getDefaultInstance();
            user = editSequenceRealm.where(UserDataModel.class).equalTo("loggedIn", true).findFirst();

            boolean aboveNineBool = false;
            for (int i = 0; i < splited.length; i++) {
                PegModel tempPeg = new PegModel();
                for(int j = 0;j<aboveNineIndexes.size();j++){
                    if(i==aboveNineIndexes.get(j)){
                        aboveNineBool = true;
                        tempPeg.setNum(Integer.parseInt(splited[i]+""+splited[i+1]));
                        i++;
                    }
                }
                if(!aboveNineBool) {
                    tempPeg.setNum(Integer.parseInt(splited[i]));
                }
                    pegs.add(tempPeg);
                    aboveNineBool = false;
            }}catch(Throwable e){
                Toast.makeText(getContext(), "setpegs error: " +e.toString(),Toast.LENGTH_LONG).show();
            }
        try{
            splitedseq.setSequence(pegs);
            for (int i = 0; i < user.getSequences().size(); i++) {
                if (user.getSequences().get(i).isEqual(splitedseq)) {
                    foundSeq = user.getSequences().get(i);
                }
            }

        }catch (Throwable e){
            Toast.makeText(getContext(),"find seq error: " + e.toString(),Toast.LENGTH_LONG).show();
        }


       /* for(int i=0;i<foundSeq.getSequence();i++){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                storyBuilder.append(foundSeq.getSequence().get(i).getWord()+ " ");

            }
        }/*
        */
        StringBuilder buildstory = new StringBuilder();
        story = foundSeq.getStory();

        for(int i=0;i<story.size();i++){
            buildstory.append(story.get(i)).append(" ");
        }
        seqTv.setText(sequence);
        storyEt.setText(buildstory.toString());




        view.findViewById(R.id.saveStoryBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    editSequenceRealm.executeTransaction(r -> {
                    String[] storyList;
                    RealmList<String> list = new RealmList<String>();
                    storyList = storyEt.getText().toString().split("\\s+");
                    list.addAll(Arrays.asList(storyList));
                    foundSeq.setStory(list);
                        editSequenceRealm.insertOrUpdate(foundSeq);
                    });
                }catch(Throwable e){
                    Toast.makeText(getContext(),e.toString(),Toast.LENGTH_LONG).show();
                }

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
                                    ft.replace(R.id.container, new sequenceUpdateFragment(), "editSeq")
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


        return view;
    }
}