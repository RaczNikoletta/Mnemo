package com.example.fogas;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class editSequencesFragment extends Fragment {
    private  String sequence;

    public static editSequencesFragment newInstance(String sequence) {
        editSequencesFragment f = new editSequencesFragment();
        Bundle args = new Bundle();
        f.setArguments(args);
        args.putString("sequence", sequence);
        f.setArguments(args);
        return f;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        try {
            Bundle args = getArguments();
            sequence = args.getString("sequence", "");
        } catch (Throwable e) {
            Toast.makeText(getContext(), "arguments error " + e.toString(), Toast.LENGTH_LONG).show();
        }
        Toast.makeText(getContext(), sequence, Toast.LENGTH_LONG).show();
        return inflater.inflate(R.layout.fragment_edit_sequences, container, false);
    }
}