package com.example.fogas;

import static android.content.Context.MODE_PRIVATE;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;

public class SettingsFragment extends Fragment {
    private SharedPreferences permissionStatus;
    private Switch notificationSwitch;


    public SettingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_settings, container, false);
        permissionStatus = getActivity().getSharedPreferences("permissionStatus", MODE_PRIVATE);
        notificationSwitch = view.findViewById(R.id.switch1);
        notificationSwitch.setChecked(permissionStatus.getBoolean
                (Manifest.permission.ACCESS_NOTIFICATION_POLICY, true));

        notificationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                SharedPreferences.Editor editor = permissionStatus.edit();
                if(!b){
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        editor.putBoolean(Manifest.permission.ACCESS_NOTIFICATION_POLICY, false);
                    }
                }else{
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        editor.putBoolean(Manifest.permission.ACCESS_NOTIFICATION_POLICY, true);
                    }
                }
                editor.apply();
            }
        });


        return view;
    }
}