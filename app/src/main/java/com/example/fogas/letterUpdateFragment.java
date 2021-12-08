package com.example.fogas;

import static android.content.Context.ALARM_SERVICE;
import static android.content.Context.MODE_PRIVATE;

import android.Manifest;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.TextUtils;
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
    private ArrayAdapter<String> pegAdapter;
    private PegDataModel pegmodelAboveNine;
    private RealmList pegRealmListAboveNine;
    private PegModel tempAboveNine;
    private String selectedFromSpinner;
    private TextView newNumberTv;
    private int belowten;
    private SharedPreferences prefs = null;
    private boolean isFirst;
    private SharedPreferences permissionStatus;

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
        createNotificationChannel();
        prefs = getActivity().getSharedPreferences("repeatDatas", MODE_PRIVATE);
        isFirst = prefs.getBoolean("first_not",true);

        ids = new int[]{R.id.pegletterTv0, R.id.pegletterTv1, R.id.pegletterTv2, R.id.pegletterTv3, R.id.pegletterTv4,
                R.id.pegletterTv5, R.id.pegletterTv6, R.id.pegletterTv7, R.id.pegletterTv8,
                R.id.pegletterTv9, R.id.pegwordTv0, R.id.pegwordTv1, R.id.pegwordTv2, R.id.pegwordTv3, R.id.pegwordTv4, R.id.pegwordTv5,
                R.id.pegwordTv6, R.id.pegwordTv7, R.id.pegwordTv8, R.id.pegwordTv9};

        permissionStatus = getActivity().getSharedPreferences("permissionStatus", MODE_PRIVATE);

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


        try {
            //todo kiszedni függvénybe
            updaterRealm = Realm.getDefaultInstance();
            user = updaterRealm.where(UserDataModel.class).equalTo("loggedIn", true).findFirst();
            if (user != null) {
                pegmodelAboveNine = updaterRealm.where(PegDataModel.class).equalTo("userName", user.getUserName()).findFirst();
                if (pegmodelAboveNine != null) {
                    pegRealmListAboveNine = pegmodelAboveNine.getPegs();
                    for (int i = 0; i < pegRealmListAboveNine.size(); i++) {
                        tempAboveNine = new PegModel();
                        tempAboveNine = (PegModel) pegRealmListAboveNine.get(i);
                        if (tempAboveNine != null) {
                            if (tempAboveNine.getNum() > 9) {
                                try {
                                    pegAboveNine.add(String.valueOf(tempAboveNine.getNum()));
                                } catch (Throwable e) {
                                    Toast.makeText(getContext(), "for loop add: " + i + " " + e.toString(), Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                    }
                }
            }

        } catch (Throwable e) {
            Toast.makeText(getContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
        pegAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, pegAboveNine);
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
            //Todo kiszedni függvénybe
            public void onItemSelected(AdapterView<?> adapterView, View v, int position, long l) {
                selectedFromSpinner = aboveNineSpinner.getSelectedItem().toString();
                //get all peg from the user
                for (int i = 0; i < pegRealmListAboveNine.size(); i++) {
                    tempAboveNine = new PegModel();
                    tempAboveNine = (PegModel) pegRealmListAboveNine.get(i);
                    if (tempAboveNine != null) {
                        //check if i. is equals the selected
                        if (tempAboveNine.getNum() == Integer.parseInt(selectedFromSpinner)) {
                            if (tempAboveNine.getLetter() == null) {
                                pegLetterAboveNineEt.setText("");
                            } else {
                                pegLetterAboveNineEt.setText(tempAboveNine.getLetter());
                            }
                            if (tempAboveNine.getWord() == null) {
                                pegWordAboveNineEt.setText("");
                            } else {
                                pegWordAboveNineEt.setText(tempAboveNine.getWord());
                            }
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
            //todo kiszedni függvénybe
            updaterRealm = Realm.getDefaultInstance();
            user = updaterRealm.where(UserDataModel.class).equalTo("loggedIn", true).findFirst();
            if (user != null) {
                peg = updaterRealm.where(PegDataModel.class).equalTo("userName", user.getUserName()).findFirst();
                if (peg != null) {
                    pegs = peg.getPegs();
                    //set rows from database
                    for (int i = 0; i < pegs.size(); i++) {
                        if (pegs.get(i).getNum() < 10) {
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

                        }
                    }
                } else {
                    Toast.makeText(getContext(), "PegDataModel query returned null object (letterUpdateFragment.java function startQuery())", Toast.LENGTH_LONG).show();
                }
            } else {

                new AlertDialog.Builder(getContext())
                        .setTitle(R.string.loginrequest)
                        .setMessage(R.string.loginrequest2)

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
                        .setIcon(getResources().getDrawable(R.drawable.ic_baseline_done_outline_24))
                        .show();
            }

        } catch (Throwable e) {
            Toast.makeText(getContext(), e.toString(), Toast.LENGTH_LONG).show();

        }


        view.findViewById(R.id.saveUpdatesBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    belowten = 0;
                    user = updaterRealm.where(UserDataModel.class).equalTo("loggedIn", true).findFirst();
                    try {
                        peg = updaterRealm.where(PegDataModel.class).equalTo("userName", user.getUserName()).findFirst();
                        pegs = peg.getPegs();
                        //count pegs below ten to know bounds
                        for (int i = 0; i < pegs.size(); i++) {
                            if (pegs.get(i).getNum() <= 9) {
                                belowten++;
                            }
                        }
                    } catch (Throwable e) {
                        Toast.makeText(getContext(), "phase one" + e.toString(), Toast.LENGTH_LONG).show();
                    }
                    try {
                        //Toast.makeText(getContext(), "belowten " + String.valueOf(belowten), Toast.LENGTH_LONG).show();
                        updaterRealm.executeTransaction(r -> {
                            for (int i = 0; i < belowten; i++) {
                                PegModel p = new PegModel();
                                try {
                                    eLetter = view.findViewById(ids[i]);
                                    eWord = view.findViewById(ids[i + 10]);
                                    Log.v("letterupdate", i + " " + ids[i] + "ennyi");
                                    let = eLetter.getText().toString();
                                    word = eWord.getText().toString();
                                    p.setLetter(let);
                                    p.setWord(word);
                                    p.setNum(i);
                                }catch(Throwable e){
                                        Toast.makeText(getContext(),"beforeDelete" + e.toString(),Toast.LENGTH_LONG).show();
                                    }
                                    user.getPegs().setOnePeg(p);
                            }
                            try {
                                if(aboveNineSpinner.getSelectedItem()!=null){
                                int tempAboveNine = Integer.parseInt(aboveNineSpinner.getSelectedItem().toString());
                                for (int i = 0; i < user.getPegs().getPegs().size(); i++) {
                                    if (user.getPegs().getPegs().get(i).getNum() == tempAboveNine) {
                                        if (!(TextUtils.isEmpty(pegLetterAboveNineEt.getText().toString()))) {
                                            user.getPegs().getPegs().get(i).setLetter(pegLetterAboveNineEt.getText().toString());
                                        }else{
                                            user.getPegs().getPegs().get(i).setLetter("");
                                        }
                                        if (!(TextUtils.isEmpty(pegWordAboveNineEt.getText().toString()))) {
                                            user.getPegs().getPegs().get(i).setWord(pegWordAboveNineEt.getText().toString());
                                        }else{
                                            user.getPegs().getPegs().get(i).setWord("");
                                        }
                                    }
                                }}
                            } catch (Throwable e) {
                                Toast.makeText(getContext(), "Peg above nine: " + e.toString(), Toast.LENGTH_LONG).show();
                            }
                            updaterRealm.copyToRealmOrUpdate(user);
                        });
                    } catch (Throwable e) {
                        Toast.makeText(getContext(), "Saving issue: " + e.toString(), Toast.LENGTH_LONG).show();
                    }

                    new AlertDialog.Builder(getContext())
                            .setTitle(R.string.databaseUpdated)
                            .setMessage(R.string.databaseUpdated2)

                            // Specifying a listener allows you to take an action before dismissing the dialog.
                            // The dialog is automatically dismissed when a dialog button is clicked.
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    PegDataModel checkpegs = user.getPegs();
                                    int[] counter = checkpegs.counter();
                                    //check pegs num -- set notification progress to null
                                    if ((counter[0]+counter[1]) >= 10 && (permissionStatus.getBoolean
                                            (Manifest.permission.ACCESS_NOTIFICATION_POLICY, true))) {
                                        if(user.getLastNotification().get(1)!=0.0){


                                            updaterRealm.executeTransaction(r-> {
                                                user.getLastNotification().set(0, 0.0);
                                                user.getLastNotification().set(1, 0.0);
                                                user.getLastNotification().set(2, 0.0);
                                                updaterRealm.insertOrUpdate(user);
                                            } );
                                        }
                                        long timeAtButtonClick = System.currentTimeMillis();
                                        Intent intent = new Intent(getContext(),PracticeNotificationManager.class);
                                        PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(),0,intent,0);

                                        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(ALARM_SERVICE);

                                        long tenSecondsInMillis = 1000*10;
                                        alarmManager.set(AlarmManager.RTC_WAKEUP,timeAtButtonClick+tenSecondsInMillis,pendingIntent);
                                    }
                                        FragmentManager fm = getFragmentManager();
                                        FragmentTransaction ft = fm.beginTransaction();
                                        ft.replace(R.id.container, new letterUpdateFragment(), "letterUpdate")
                                                .addToBackStack(null)
                                                .commit();
                                }
                            })

                            // A null listener allows the button to dismiss the dialog and take no further action.
                            .setIcon(getResources().getDrawable(R.drawable.ic_baseline_done_outline_24))
                            .show();

                } catch (Throwable e) {
                    Toast.makeText(getContext(), "other issue: " + e.toString(), Toast.LENGTH_LONG).show();
                }

            }
        });
        // Inflate the layout for this fragment
        return view;


    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putIntArray("idsof", ids);
    }
    private  void createNotificationChannel(){
        CharSequence name = "PracticeNotifyChannel";
        String desctiption = "Channel for practice";
        int importance = NotificationManager.IMPORTANCE_HIGH;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("notifyUser",name,importance);
            channel.setDescription(desctiption);

            NotificationManager notificationManager = getActivity().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }


}