package com.example.fogas;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.fogas.Models.HintDataModel;
import com.example.fogas.Models.HintModel;
import com.example.fogas.Models.PegModel;
import com.example.fogas.Models.Progress;
import com.example.fogas.Models.ProgressDataModel;
import com.example.fogas.Models.SequenceDataModel;
import com.example.fogas.Models.UserDataModel;

import java.util.ArrayList;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmList;

public class SequencePracticeHard extends Fragment {

    private EditText hardsequencePracEt;
    private EditText hardstoryPracticeEt;
    private String sequence;
    private Realm hardSeqPractRealm;
    private long tStart;
    private long tEnd;
    private long tDelta;
    private double elapsedSeconds;
    private int score;
    private int pegCounter;
    //indexes of above nine
    private ArrayList<Integer> aboveNine;
    private ArrayList<Integer> seqPegs;
    private UserDataModel user = new UserDataModel();
    private RealmList<PegModel> pegs = new RealmList<>();
    private SequenceDataModel splitedseq = new SequenceDataModel();
    private SequenceDataModel foundSeq = new SequenceDataModel();
    private ImageView imageViewHintHard;
    private ImageButton imageButton2;
    private boolean isVisible = false;

    public SequencePracticeHard() {
        // Required empty public constructor
    }
    public static SequencePracticeHard newInstance(String sequence, ArrayList<Integer> seqpegs) {
        SequencePracticeHard fragment = new SequencePracticeHard();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        args.putIntegerArrayList("pegs",seqpegs);
        args.putString("sequence", sequence);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sequence_practice_hard, container, false);

        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setRetainInstance(true);
        seqPegs = new ArrayList<>();
        aboveNine = new ArrayList<>();
        tStart = System.currentTimeMillis();
        hardstoryPracticeEt = view.findViewById(R.id.hardstoryPracticeEt);
        hardsequencePracEt = (EditText) view.findViewById(R.id.hardseqencePracEt);
        imageViewHintHard = (ImageView) view.findViewById(R.id.imageViewHintHard);
        imageButton2 = (ImageButton) view.findViewById(R.id.imageButton2);
        imageViewHintHard.setVisibility(View.INVISIBLE);
        try {
            Bundle args = getArguments();
            sequence = args.getString("sequence", "");
            seqPegs = args.getIntegerArrayList("pegs");
        } catch (Throwable e) {
            Toast.makeText(getContext(), "arguments error " + e.toString(), Toast.LENGTH_LONG).show();
        }
        String[] splited = sequence.split("");

        try {
            hardSeqPractRealm = Realm.getDefaultInstance();
            user = hardSeqPractRealm.where(UserDataModel.class).equalTo("loggedIn", true).findFirst();

            for (int i = 0; i < splited.length; i++) {
                PegModel tempPeg = new PegModel();
                tempPeg.setNum(Integer.parseInt(splited[i]));
                pegs.add(tempPeg);
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
        } catch (Throwable e) {
            Toast.makeText(getContext(), "find seq error: " + e.toString(), Toast.LENGTH_LONG).show();
        }

        for(int i=0;i<seqPegs.size();i++){
            if(seqPegs.get(i)>9){
                aboveNine.add(i);
            }
        }
        PegModel firstPeg = foundSeq.getSequence().get(0);
        HintModel firstHint = user.getHints().getOneHint(firstPeg.getNum());
        Bitmap bmp = BitmapFactory.decodeByteArray(firstHint.getImage(),0,firstHint.getImage().length);
        imageViewHintHard.setImageBitmap(Bitmap.createBitmap(bmp));

        imageButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isVisible) {
                    imageViewHintHard.setVisibility(View.VISIBLE);
                    isVisible = true;
                }else{
                    imageViewHintHard.setVisibility(View.INVISIBLE);
                    isVisible = false;
                }
            }
        });


        hardsequencePracEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String[] splitedSeq = hardsequencePracEt.getText().toString().split("");
                String[] newSplited = new String [splitedSeq.length];
                boolean biggerThanTen = false;
                //check if index is in aboveNine arraylist
                for(int i=0;i<splitedSeq.length-1;i++){
                    for(int j =0;j<aboveNine.size()-1;j++){
                        //if it is in it --> bigger than ten
                        if(i==aboveNine.get(j)){
                            biggerThanTen = true;
                            newSplited[i]="skip";
                        }

                    }
                    if(!biggerThanTen){
                        newSplited[i]=splitedSeq[i];
                    }
                }
                try {
                    //count numbers
                        int x = splitedSeq.length-1;
                        if ((!splitedSeq[x].equals("skip"))) {
                            PegModel model = foundSeq.getSequence().get(x+1);
                            if (model != null) {
                                HintModel currentHint = user.getHints().getOneHint(model.getNum());
                                if (currentHint != null && currentHint.getImage() != null) {
                                    Bitmap bmp = BitmapFactory.decodeByteArray(currentHint.getImage(), 0, currentHint.getImage().length);
                                    imageViewHintHard.setImageBitmap(Bitmap.createBitmap(bmp));
                                }
                            }
                            imageViewHintHard.setVisibility(View.INVISIBLE);
                            isVisible = false;
                    }if(TextUtils.isEmpty(hardsequencePracEt.getText())){
                        PegModel model = foundSeq.getSequence().get(0);
                        if (model != null) {
                            HintModel currentHint = user.getHints().getOneHint(model.getNum());
                            if (currentHint != null && currentHint.getImage() != null) {
                                Bitmap bmp = BitmapFactory.decodeByteArray(currentHint.getImage(), 0, currentHint.getImage().length);
                                imageViewHintHard.setImageBitmap(Bitmap.createBitmap(bmp));
                            }
                        }
                                imageViewHintHard.setVisibility(View.INVISIBLE);
                                isVisible = false;

                    }



                }catch(Throwable e){
                    Toast.makeText(getContext(),"get hint error"+ e.toString(),Toast.LENGTH_LONG).show();
                }
            }
        });

        view.findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tEnd = System.currentTimeMillis();
                tDelta = tEnd - tStart;
                elapsedSeconds = tDelta / 1000.0;
                RealmList<PegModel> tempPegs = new RealmList<>();
                SequenceDataModel tempSeq = new SequenceDataModel();
                SequenceDataModel answerfoundSeq = new SequenceDataModel();
                String[] tosplited = hardsequencePracEt.getText().toString().split("");
                for (int i = 0; i < tosplited.length; i++) {
                    PegModel tempPeg = new PegModel();
                    tempPeg.setNum(Integer.parseInt(tosplited[i]));
                    tempPegs.add(tempPeg);
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
                            score = 15;
                            insertProgress();
                            new AlertDialog.Builder(getContext())
                                    .setTitle(R.string.righAnswer1)
                                    .setMessage(R.string.righAnswer4)

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

        });



        return view;
    }

    public void insertProgress() {
        hardSeqPractRealm.executeTransaction(r -> {
            assert user != null;
            Progress newProg = new Progress(3);
            ProgressDataModel progress = user.getProgress();
            newProg.setTimeInGame(elapsedSeconds / 60);
            newProg.addResult(score);
            Date now = new Date();
            newProg.setAvg();
            progress.addProgress(newProg, now);
            hardSeqPractRealm.insertOrUpdate(user);
        });


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