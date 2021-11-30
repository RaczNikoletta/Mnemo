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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fogas.Models.PegModel;
import com.example.fogas.Models.SequenceDataModel;
import com.example.fogas.Models.UserDataModel;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmList;

public class before_sequence_practice extends Fragment {

    public before_sequence_practice() {
        // Required empty public constructor
    }

    private ArrayAdapter<String> adapter;
    private UserDataModel user;
    private ArrayList<String> list;
    private Realm beforeSeqRealm;
    private SequenceDataModel tempseq;
    private ListView seqPracListV;
    private String clicked="";
    private ArrayList<Integer> seqPegs;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_before_sequence_practice, container, false);
        beforeSeqRealm = Realm.getDefaultInstance();
        list = new ArrayList<>();
        seqPegs = new ArrayList<>();
        seqPracListV = view.findViewById(R.id.seqPracListV);
        user = beforeSeqRealm.where(UserDataModel.class).equalTo("loggedIn", true).findFirst();
        for (int i = 0; i < user.getSequences().size(); i++) {
            tempseq = user.getSequences().get(i);
            StringBuilder temp = new StringBuilder();
            for (int j = 0; j < tempseq.getSequence().size(); j++) {
                temp.append(String.valueOf(tempseq.getSequence().get(j).getNum()));
                if (j == tempseq.getSequence().size() - 1) {
                    seqPegs.add(tempseq.getSequence().get(j).getNum());
                    list.add(String.valueOf(temp));
                }
            }
        }
        try {
            adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, list){
                @NonNull
                @Override
                public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                    View view = super.getView(position, convertView, parent);
                    TextView text = (TextView) view.findViewById(android.R.id.text1);
                    if (list.size()>=1){
                        text.setTextColor(Color.WHITE);
                        text.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                        text.setTypeface(text.getTypeface(), Typeface.BOLD);
                        text.setTextSize(TypedValue.COMPLEX_UNIT_DIP,20);
                    }
                    return view;
                }
            };
            seqPracListV.setAdapter(adapter);
        } catch (Throwable e) {
            Toast.makeText(getContext(), "adapterset exception " + e.toString(), Toast.LENGTH_LONG).show();
        }

        seqPracListV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View v, int position, long l) {
                for(int i=0;i<list.size();i++){
                    seqPracListV.getChildAt(i).setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                }
                seqPracListV.getChildAt(position).setBackgroundColor(getResources().getColor(android.R.color.holo_blue_dark));
                clicked = list.get(position);
            }
        });

        view.findViewById(R.id.easyBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!clicked.equals("")){
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.container,sequencePracticeFragmentEasy.newInstance(clicked), "easy sequences")
                        .addToBackStack(null)
                        .commit();


            }}
        });
        view.findViewById(R.id.mediumBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!clicked.equals("")) {
                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.replace(R.id.container, SequencePracticeMedium.newInstance(clicked), "medium sequences")
                            .addToBackStack(null)
                            .commit();


                }
            }
        });
        view.findViewById(R.id.hardBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!clicked.equals("")) {
                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.replace(R.id.container,SequencePracticeHard.newInstance(clicked,seqPegs), "hard sequences")
                            .addToBackStack(null)
                            .commit();


                }
            }
        });



        return view;
    }
}