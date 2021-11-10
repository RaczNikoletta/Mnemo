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
    private Realm updaterRealm;

    public updateFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_update, container,false);
        view.findViewById(R.id.letterBtn).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                try {
                    updaterRealm = Realm.getDefaultInstance();
                    UserDataModel user = updaterRealm.where(UserDataModel.class).equalTo("loggedIn", true).findFirst();
                    if (user != null) {
                        FragmentManager fm = getFragmentManager();
                        FragmentTransaction ft = fm.beginTransaction();
                        ft.replace(R.id.container, new letterUpdateFragment())
                                .addToBackStack(null)
                                .commit();

                    } else {

                        new AlertDialog.Builder(getContext())
                                .setTitle(R.string.loginrequest)
                                .setMessage(R.string.loginrequest2)

                                // Specifying a listener allows you to take an action before dismissing the dialog.
                                // The dialog is automatically dismissed when a dialog button is clicked.
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
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
                                .setIcon(getResources().getDrawable(R.drawable.ic_baseline_error_24))
                                .show();
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