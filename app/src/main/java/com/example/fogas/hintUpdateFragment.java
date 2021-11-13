package com.example.fogas;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.example.fogas.Models.HintDataModel;

import io.realm.Realm;

public class hintUpdateFragment extends Fragment {

    public hintUpdateFragment() {
        // Required empty public constructor
    }
    private ListView hintList;
    private Button addHintBtn;
    private Realm hintRealm;
    private HintDataModel hints;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_letter_update, container, false);
        hintList = view.findViewById(R.id.hintList);
        addHintBtn = view.findViewById(R.id.addHintBtn);

        return view;
    }
}