package com.example.fogas;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fogas.Models.UserDataModel;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Map;

import io.realm.Realm;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class chainFragment extends Fragment {

    private Button btn1, btn2;
    private EditText edtxt;
    private TextView txtvw1, txtvw2, txtvw3;


    public chainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chain, container, false);

        edtxt = (EditText) view.findViewById(R.id.bevitel);
        txtvw1 = (TextView) view.findViewById(R.id.chainViewText1);
        txtvw2 = (TextView) view.findViewById(R.id.chainViewText2);
        txtvw3 = (TextView) view.findViewById(R.id.chainViewText3);
        Long string1;
        ArrayList<Long> testArray = null;

        view.findViewById(R.id.chainBtn).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
               String string1 = edtxt.getText().toString();
               Long long1 = Long.getLong(string1);
               testArray.add(long1);
            }
        });
        view.findViewById(R.id.listButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = 0;
                Long[] miazisten = null;
                for (Long local : testArray){
                    miazisten[i] = local;
                    i++;
                }
                txtvw1.setText(fastReturnInLong(miazisten).toString());
            }
        });

        return view;
    }

    private ArrayList<String> chainStringAdapter(Long ... bunchOfNumbers){
        ArrayList<String> innerArray = null;
        for (Long s : bunchOfNumbers){
            innerArray.add(s.toString());
        }
        return innerArray;
    };

    private Long chainedNumbers(ArrayList<String> stringArray){
        String returnNumbers = null;
        for (String iterate : stringArray){
            returnNumbers.concat(iterate);
        }
        return Long.parseLong(returnNumbers);
    };

    private Long fastReturnInLong(Long ... szamok){
        return chainedNumbers(chainStringAdapter(szamok));
    }

    private Map makePair(Long theKey, String theValue){
        Map<Long, String> thePair = null;
        thePair.put(theKey, theValue);
        return thePair;
    };
}