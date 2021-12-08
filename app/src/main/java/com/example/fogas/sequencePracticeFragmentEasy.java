package com.example.fogas;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fogas.Models.PegDataModel;
import com.example.fogas.Models.PegModel;
import com.example.fogas.Models.Progress;
import com.example.fogas.Models.ProgressDataModel;
import com.example.fogas.Models.SequenceDataModel;
import com.example.fogas.Models.UserDataModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import io.realm.Realm;
import io.realm.RealmList;

public class sequencePracticeFragmentEasy extends Fragment {

    private EditText easysequencePracEt;
    private EditText easystoryPracticeEt;
    private String sequence;
    private Realm easySeqPractRealm;
    private long tStart;
    private long tEnd;
    private long tDelta;
    private double elapsedSeconds;
    private int score;
    private UserDataModel user = new UserDataModel();
    private RealmList<PegModel> pegs = new RealmList<>();
    private SequenceDataModel splitedseq = new SequenceDataModel();
    private SequenceDataModel foundSeq = new SequenceDataModel();
    private RealmList<String> story = new RealmList<>();
    private ArrayList<Integer> aboveNineIndexes = new ArrayList<>();
    private Button sendEasyBtn;


    public static sequencePracticeFragmentEasy newInstance(String sequence, ArrayList<Integer> aboveNine) {
        sequencePracticeFragmentEasy f = new sequencePracticeFragmentEasy();
        Bundle args = new Bundle();
        f.setArguments(args);
        args.putString("sequence", sequence);
        args.putIntegerArrayList("aboveNine",aboveNine);
        f.setArguments(args);
        return f;
    }


    public sequencePracticeFragmentEasy() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sequence_practice_easy, container, false);
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setRetainInstance(true);
        tStart = System.currentTimeMillis();
        easystoryPracticeEt = view.findViewById(R.id.easystoryPracticeEt);
        easysequencePracEt = view.findViewById(R.id.easyseqencePracEt);
        sendEasyBtn = view.findViewById(R.id.sendEasyBtn);
        try {
            Bundle args = getArguments();
            sequence = args.getString("sequence", "");
            aboveNineIndexes = args.getIntegerArrayList("aboveNine");
        } catch (Throwable e) {
            Toast.makeText(getContext(), "arguments error " + e.toString(), Toast.LENGTH_LONG).show();
        }
        String[] splited = sequence.split("");

        try {
            easySeqPractRealm = Realm.getDefaultInstance();
            user = easySeqPractRealm.where(UserDataModel.class).equalTo("loggedIn", true).findFirst();
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
            }
        } catch (Throwable e) {
            Toast.makeText(getContext(), "setpegs error: " + e.toString(), Toast.LENGTH_LONG).show();
        }
        try {
            splitedseq.setSequence(pegs);
            for (int i = 0; i < user.getSequences().size(); i++) {
                if (user.getSequences().get(i).isEqual(splitedseq)) {
                    foundSeq = user.getSequences().get(i);
                }
            }

            StringBuilder buildstory = new StringBuilder();
            story = foundSeq.getStory();

            for (int i = 0; i < story.size(); i++) {
                buildstory.append(story.get(i)).append(" ");
            }

            easystoryPracticeEt.setText(buildstory.toString());

        } catch (Throwable e) {
            Toast.makeText(getContext(), "find seq error: " + e.toString(), Toast.LENGTH_LONG).show();
        }

        view.findViewById(R.id.sendEasyBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(easysequencePracEt.getText().toString())) {
                    tEnd = System.currentTimeMillis();
                    tDelta = tEnd - tStart;
                    elapsedSeconds = tDelta / 1000.0;
                    RealmList<PegModel> tempPegs = new RealmList<>();
                    SequenceDataModel tempSeq = new SequenceDataModel();
                    SequenceDataModel answerfoundSeq = new SequenceDataModel();
                    String[] tosplited = easysequencePracEt.getText().toString().split("");
                    try {
                        for (int i = 0; i < tosplited.length; i++) {
                            PegModel tempPeg = new PegModel();
                            tempPeg.setNum(Integer.parseInt(tosplited[i]));
                            tempPegs.add(tempPeg);
                        }
                    }catch (Throwable e){
                        Toast.makeText(getContext(),getResources().getString(R.string.invalidFormatSeq),Toast.LENGTH_LONG).show();
                    }
                    try {
                        tempSeq.setSequence(tempPegs);
                        for (int i = 0; i < user.getSequences().size(); i++) {
                            if (user.getSequences().get(i).isEqual(tempSeq)) {
                                answerfoundSeq = user.getSequences().get(i);
                            }
                        }


                    } catch (Throwable e) {
                        Log.d("answerseq", e.toString());
                    }
                    if (answerfoundSeq.getSequence() != null) {
                        if (foundSeq.isEqual(answerfoundSeq)) {
                            try {
                                score = 5;
                                insertProgress();
                                new AlertDialog.Builder(getContext())
                                        .setTitle(R.string.righAnswer1)
                                        .setMessage(R.string.righAnswer2)

                                        // Specifying a listener allows you to take an action before dismissing the dialog.
                                        // The dialog is automatically dismissed when a dialog button is clicked.
                                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                try {
                                                    FragmentManager fm = getFragmentManager();
                                                    FragmentTransaction ft = fm.beginTransaction();
                                                    ft.replace(R.id.container, new before_sequence_practice(), "beforeseq")
                                                            .addToBackStack(null)
                                                            .commit();
                                                } catch (Throwable e) {
                                                    Toast.makeText(getContext(), "Fragment change error " + e.toString(), Toast.LENGTH_LONG).show();
                                                }

                                            }
                                        })
                                        // A null listener allows the button to dismiss the dialog and take no further action.
                                        .setIcon(getResources().getDrawable(R.drawable.ic_rightanswersmile))
                                        .show();
                            } catch (Throwable e) {
                                Toast.makeText(getContext(), "alertdialog" + " " + e.toString(), Toast.LENGTH_LONG).show();
                            }
                        }
                    } else {
                        try {
                            new AlertDialog.Builder(getContext())
                                    .setTitle(R.string.wrongAnswer1)
                                    .setMessage(getResources().getString(R.string.wrongAnswer2) + " " + sequence)

                                        // Specifying a listener allows you to take an action before dismissing the dialog.
                                        // The dialog is automatically dismissed when a dialog button is clicked.
                                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                try {
                                                    FragmentManager fm = getFragmentManager();
                                                    FragmentTransaction ft = fm.beginTransaction();
                                                    ft.replace(R.id.container, new before_sequence_practice(), "beforeseq")
                                                            .addToBackStack(null)
                                                            .commit();
                                                } catch (Throwable e) {
                                                    Toast.makeText(getContext(), "Fragment change error " + e.toString(), Toast.LENGTH_LONG).show();
                                                }

                                        }
                                    })
                                    // A null listener allows the button to dismiss the dialog and take no further action.
                                    .setIcon(getResources().getDrawable(R.drawable.ic_wrondiaspp))
                                    .show();
                        } catch (Throwable e) {
                            Toast.makeText(getContext(), "alertdialog" + " " + e.toString(), Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }

        });


        return view;
    }

    public void insertProgress() {
        easySeqPractRealm.executeTransaction(r -> {
            assert user != null;
            Progress newProg = new Progress(3);
            ProgressDataModel progress = user.getProgress();
            newProg.setTimeInGame(elapsedSeconds / 60);
            newProg.addResult(score);
            Date now = new Date();
            newProg.setAvg();
            progress.addProgress(newProg, now);
            easySeqPractRealm.insertOrUpdate(user);
        });


    }

    @Override
    public void onStop() {
        super.onStop();
        sendEasyBtn.setEnabled(false);

    }
}