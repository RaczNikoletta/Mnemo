package com.example.fogas;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.fogas.Models.UserDataModel;

import io.realm.Realm;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class updateFragment extends Fragment {

    private Button letterBtn;
    private Button hintBtn;
    private Realm updaterRealm;
    UserDataModel userLogged;

    public updateFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_update, container,false);
        setRetainInstance(true);
        updaterRealm = Realm.getDefaultInstance();
        userLogged = updaterRealm.where(UserDataModel.class).equalTo("loggedIn", true).findFirst();
        view.findViewById(R.id.letterBtn).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                try {
                    if (userLogged != null) {
                        FragmentManager fm = getFragmentManager();
                        FragmentTransaction ft = fm.beginTransaction();
                        ft.replace(R.id.container, new letterUpdateFragment(),"letterUpdate")
                                .addToBackStack(null)
                                .commit();

                    }
                } catch (Throwable e) {
                    Toast.makeText(getContext(), e.toString(), Toast.LENGTH_LONG).show();

                }
            }
        });

        view.findViewById(R.id.hintBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                if (userLogged != null) {
                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.replace(R.id.container, new hintUpdateFragment(),"hintUpdate")
                            .addToBackStack(null)
                            .commit();

                }
            } catch (Throwable e) {
                Toast.makeText(getContext(), e.toString(), Toast.LENGTH_LONG).show();

            }
        }
    });

        view.findViewById(R.id.sequenceBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    if (userLogged != null) {
                        FragmentManager fm = getFragmentManager();
                        FragmentTransaction ft = fm.beginTransaction();
                        ft.replace(R.id.container, new sequenceUpdateFragment(),"sequenceUpdate")
                                .addToBackStack(null)
                                .commit();

                    }
                } catch (Throwable e) {
                    Toast.makeText(getContext(), e.toString(), Toast.LENGTH_LONG).show();

                }

            }
        });


        // Inflate the layout for this fragment
        return view;
    }
}