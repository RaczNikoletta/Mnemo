package com.example.fogas;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.fogas.Models.HintDataModel;
import com.example.fogas.Models.HintModel;
import com.example.fogas.Models.PegDataModel;
import com.example.fogas.Models.UserDataModel;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmList;



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
                            hintStrings.add(h.getHint());
                        }
                    }
                }
            }

        }catch (Throwable e){
            Toast.makeText(getContext(),e.toString(),Toast.LENGTH_LONG).show();
        }
        //set ListView
        adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1,hintStrings);
        hintList.setAdapter(adapter);

        view.findViewById(R.id.addHintBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.replace(R.id.container,new addHintsFragment(),"Add hints fragment")
                            .addToBackStack(null)
                            .commit();
                }catch(Throwable e){
                    Log.d("practicefragment","letterpractice click error "+ e.toString());
                }
            }
        });

        return view;
    }
}