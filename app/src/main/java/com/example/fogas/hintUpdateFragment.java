package com.example.fogas;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fogas.Models.HintDataModel;
import com.example.fogas.Models.HintModel;
import com.example.fogas.Models.PegDataModel;
import com.example.fogas.Models.UserDataModel;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;


public class hintUpdateFragment extends Fragment {

    public hintUpdateFragment() {
        // Required empty public constructor
    }
    private ListView hintList;
    private Button addHintBtn;
    private Realm hintRealm;
    private HintDataModel hints;
    private UserDataModel user;
    private RealmList hintRealmList;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> hintStrings;
    private HintModel h;
    private String clicked="";
    private String [] splitedClicked;
    //TODO check if item is selected !! it is mandatory
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_hint_update, container, false);
        setRetainInstance(true);
        hintList = view.findViewById(R.id.hintList);
        addHintBtn = view.findViewById(R.id.addHintBtn);

        hintStrings = new ArrayList<>();
        try{
            hintRealm = Realm.getDefaultInstance();
            user = hintRealm.where(UserDataModel.class).equalTo("loggedIn",true).findFirst();
            if(user != null){
                hints = hintRealm.where(HintDataModel.class).equalTo("userName",user.getUserName()).findFirst();
                if(hints != null){
                    hintRealmList = hints.getHints();
                    for(int i=0;i<hintRealmList.size();i++){
                        h = new HintModel();
                        h = (HintModel) hintRealmList.get(i);
                        if(h!=null) {
                            hintStrings.add(h.getPegnum()+" ");
                        }
                    }
                }
            }

        }catch (Throwable e){
            Toast.makeText(getContext(),e.toString(),Toast.LENGTH_LONG).show();
        }
        //set ListView
        adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1,hintStrings){
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text = (TextView) view.findViewById(android.R.id.text1);
                if (hintStrings.size()>=1){
                    text.setTextColor(Color.WHITE);
                    text.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    text.setTypeface(text.getTypeface(), Typeface.BOLD);
                    text.setTextSize(TypedValue.COMPLEX_UNIT_DIP,20);
                }
                return view;
            }
        };
        hintList.setAdapter(adapter);
        hintList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View v, int position, long l) {
                for(int i=0;i<hintStrings.size();i++){
                    hintList.getChildAt(i).setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                }
                hintList.getChildAt(position).setBackgroundColor(getResources().getColor(android.R.color.holo_blue_dark));
                clicked = hintStrings.get(position);
                splitedClicked = clicked.split("\\s+");
            }
        });

        view.findViewById(R.id.addHintBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                        FragmentManager fm = getFragmentManager();
                        FragmentTransaction ft = fm.beginTransaction();
                        ft.replace(R.id.container, new addHintsFragment(), "Add hints fragment")
                                .addToBackStack(null)
                                .commit();

                }catch(Throwable e){
                    Log.d("add hints","Add hints "+ e.toString());
                }
            }
        });

        view.findViewById(R.id.editBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if(!clicked.equals("")) {
                        FragmentManager fm = getFragmentManager();
                        FragmentTransaction ft = fm.beginTransaction();
                        ft.replace(R.id.container, editHintsFragment.newInstance(clicked), "Edit hints fragment")
                                .addToBackStack(null)
                                .commit();
                    }else{
                        new AlertDialog.Builder(getContext())
                                .setTitle(R.string.chooseHint1)
                                .setMessage(R.string.chooseHint2)

                                // Specifying a listener allows you to take an action before dismissing the dialog.
                                // The dialog is automatically dismissed when a dialog button is clicked.
                                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        try {
                                            FragmentManager fm = getFragmentManager();
                                            FragmentTransaction ft = fm.beginTransaction();
                                            ft.replace(R.id.container, new hintUpdateFragment(), "hintUpdate")
                                                    .addToBackStack(null)
                                                    .commit();
                                        } catch (Throwable e) {
                                            Toast.makeText(getContext(), "Fragment change error " + e.toString(), Toast.LENGTH_LONG).show();
                                        }

                                    }
                                })
                                // A null listener allows the button to dismiss the dialog and take no further action.
                                .setIcon(getResources().getDrawable(R.drawable.ic_baseline_error_24))
                                .show();

                    }
                }catch(Throwable e){
                    Log.d("editHintsFragment","editHintsFragment click error "+ e.toString());
                }
            }
        });

        view.findViewById(R.id.deleteBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!clicked.equals("")) {
                    hintRealm.executeTransaction(r -> {
                        try {
                            HintDataModel datas = user.getHints();
                            HintModel hint = datas.getOneHint(Integer.parseInt(splitedClicked[0]));
                            hint.deleteFromRealm();
                        } catch (Throwable e) {
                            Toast.makeText(getContext(), e.toString(), Toast.LENGTH_LONG).show();
                        }

                    });
                    try {
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
                                            ft.replace(R.id.container, new hintUpdateFragment(), "hintUpdate")
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
                    } catch (Throwable e) {
                        Toast.makeText(getContext(), "alertdialog" + " " + e.toString(), Toast.LENGTH_LONG).show();
                    }

                }else{
                    new AlertDialog.Builder(getContext())
                            .setTitle(R.string.chooseHint1)
                            .setMessage(R.string.chooseHint2)

                            // Specifying a listener allows you to take an action before dismissing the dialog.
                            // The dialog is automatically dismissed when a dialog button is clicked.
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    try {
                                        FragmentManager fm = getFragmentManager();
                                        FragmentTransaction ft = fm.beginTransaction();
                                        ft.replace(R.id.container, new hintUpdateFragment(), "hintUpdate")
                                                .addToBackStack(null)
                                                .commit();
                                    } catch (Throwable e) {
                                        Toast.makeText(getContext(), "Fragment change error " + e.toString(), Toast.LENGTH_LONG).show();
                                    }

                                }
                            })
                            // A null listener allows the button to dismiss the dialog and take no further action.
                            .setIcon(getResources().getDrawable(R.drawable.ic_baseline_error_24))
                            .show();

                }
            }

        });

        return view;
    }
}