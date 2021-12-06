package com.example.fogas;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class HelpFragment extends Fragment {
    private ListView helpList;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> helps;
    private String clicked;
    private Button checkBtn;

    public HelpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_help, container, false);

        helpList = view.findViewById(R.id.helpList);
        helps = new ArrayList<>();
        helps.add(getString(R.string.aboutPegs));
        checkBtn = view.findViewById(R.id.checkBtn);
        adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1,helps){
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text = (TextView) view.findViewById(android.R.id.text1);
                if (helps.size()>=1){
                    text.setTextColor(Color.WHITE);
                    text.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    text.setTypeface(text.getTypeface(), Typeface.BOLD);
                    text.setTextSize(TypedValue.COMPLEX_UNIT_DIP,20);
                }
                return view;
            }

        };

        helpList.setAdapter(adapter);
        helpList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View v, int position, long l) {
                for(int i=0;i<helps.size();i++){
                    helpList.getChildAt(i).setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                }
                helpList.getChildAt(position).setBackgroundColor(getResources().getColor(android.R.color.holo_blue_dark));
                clicked = helps.get(position);
            }
        });

        checkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(clicked.equals(getString(R.string.aboutPegs))){
                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.replace(R.id.container, new aboutPegsFragment(), "About pegs fragment")
                            .addToBackStack(null)
                            .commit();
                }
            }
        });



        return view;
    }
}