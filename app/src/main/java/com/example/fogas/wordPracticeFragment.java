package com.example.fogas;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.fogas.Models.PegDataModel;
import com.example.fogas.Models.PegModel;
import com.example.fogas.Models.UserDataModel;

import io.realm.Realm;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link wordPracticeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class wordPracticeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Realm wordRealm;
    boolean ifExists = false;

    public wordPracticeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment wordPracticeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static wordPracticeFragment newInstance(String param1, String param2) {
        wordPracticeFragment fragment = new wordPracticeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        if(!checkIfWordsInDatabase())
        {
            if(getContext()!= null) {
                new AlertDialog.Builder(getContext())
                        .setTitle(R.string.noPegwordsinDB)
                        .setMessage(R.string.noPegwordsinDB)

                        // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })

                        // A null listener allows the button to dismiss the dialog and take no further action.
                        .setIcon(getResources().getDrawable(R.drawable.ic_baseline_error_24))
                        .show();
            }

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_word_practice, container, false);
    }

    //TODO ne csak az elsőt nézze, hanem for ciklusban
    public boolean checkIfWordsInDatabase(){
        try{
        wordRealm = Realm.getDefaultInstance();
        wordRealm.executeTransaction(r->{
            UserDataModel loggedUsr = wordRealm.where(UserDataModel.class).equalTo("loggedIn",true).findFirst();
            if(loggedUsr != null) {
                PegDataModel pegWord = wordRealm.where(PegDataModel.class).equalTo("userName", loggedUsr.getUserName()).findFirst();
                if (pegWord != null) {
                    PegModel firstPeg = pegWord.getOnePeg(0);
                    if(firstPeg != null){
                            if(firstPeg.getWord() != null){
                                ifExists = true;
                            }
                    }
                }


                }

        });

    }catch(Throwable e)
        {
            Toast.makeText(getContext(), e.toString(), Toast.LENGTH_LONG).show();
            return false;

        }
            return ifExists;
    }
}