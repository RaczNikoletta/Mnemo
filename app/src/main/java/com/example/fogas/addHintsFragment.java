package com.example.fogas;

import static android.app.Activity.RESULT_OK;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.fogas.Models.HintDataModel;
import com.example.fogas.Models.HintModel;
import com.example.fogas.Models.PegDataModel;
import com.example.fogas.Models.PegModel;
import com.example.fogas.Models.UserDataModel;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmList;

public class addHintsFragment extends Fragment {

    public addHintsFragment() {
        // Required empty public constructor
    }
    private Realm addRealm;
    private ArrayAdapter<String> adapter;
    private UserDataModel user;
    private PegDataModel pegModel;
    private RealmList pegRealmList;
    private PegModel tempPeg;
    private ArrayList<String> pegList;
    private Spinner pegSpinner;
    private EditText hintStringEt;
    private Button browseBtn;
    private Button saveHintsBtn;
    private Uri imageUri;
    private ImageView imageView;
    private static final int PICK_IMAGE = 100;
    private Bitmap imageBmp;
    private HintDataModel hintData;
    private HintModel hint;
    private File imageFile;
    private long fileSizeInMB;
    private HintDataModel userHints;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_hints, container, false);
        pegSpinner = view.findViewById(R.id.pegSpinner2);
        browseBtn = view.findViewById(R.id.browseBtn);
        saveHintsBtn = view.findViewById(R.id.saveHintsBtn);
        imageView = view.findViewById(R.id.imageView);
        hintData = new HintDataModel();
        pegList = new ArrayList<String>();
        setRetainInstance(true);

        // Inflate the layout for this fragment
        try{
            addRealm = Realm.getDefaultInstance();
            user = addRealm.where(UserDataModel.class).equalTo("loggedIn",true).findFirst();
            if(user != null){
                pegModel = addRealm.where(PegDataModel.class).equalTo("userName",user.getUserName()).findFirst();
                if(pegModel != null){
                    pegRealmList = pegModel.getPegs();
                    for(int i=0;i<pegRealmList.size();i++){
                        tempPeg = new PegModel();
                        tempPeg = (PegModel) pegRealmList.get(i);
                        if(tempPeg!=null) {
                            pegList.add(String.valueOf(tempPeg.getNum()));
                        }
                    }
                }
            }

        }catch (Throwable e){
            Toast.makeText(getContext(),e.toString(),Toast.LENGTH_LONG).show();
        }
        adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1,pegList);
        pegSpinner.setAdapter(adapter);

        //browse image
        view.findViewById(R.id.browseBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(gallery,PICK_IMAGE);
            }
        });

        view.findViewById(R.id.saveHintsBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                imageBmp.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                imageBmp.recycle();
                long fileSizeinBytes = imageFile.length();
                long fileSizeInKB = fileSizeinBytes / 1024;
                fileSizeInMB = fileSizeInKB / 1024;

                //TODO test with bigger image than 16MB
                if (fileSizeInMB > 16) {
                    new AlertDialog.Builder(getContext())
                            .setTitle(R.string.above16mb1 + " " + fileSizeInMB)
                            .setMessage(R.string.above16mb2 + " " + fileSizeInMB)

                            // Specifying a listener allows you to take an action before dismissing the dialog.
                            // The dialog is automatically dismissed when a dialog button is clicked.
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {


                                }
                            })
                            // A null listener allows the button to dismiss the dialog and take no further action.
                            .setIcon(getResources().getDrawable(R.drawable.ic_baseline_error_24))
                            .show();


                } else {

                    try {
                        addRealm.executeTransaction(r -> {
                            try {
                                hint = new HintModel();
                                hint.setPegnum(Integer.parseInt(pegSpinner.getSelectedItem().toString()));
                                hint.setImage(byteArray);
                                addRealm.insertOrUpdate(addRealm.copyToRealmOrUpdate(hint));
                            }catch (Throwable e){
                                Toast.makeText(getContext(), "hint" + " " + e.toString(), Toast.LENGTH_LONG).show();
                            }
                            HintDataModel usdata = user.getHints();

                                usdata.setOneHint(hint);





                        });
                        try {
                            new AlertDialog.Builder(getContext())
                                    .setTitle(R.string.righAnswer1)
                                    .setMessage(R.string.righAnswer2)

                                    // Specifying a listener allows you to take an action before dismissing the dialog.
                                    // The dialog is automatically dismissed when a dialog button is clicked.
                                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            try {
                                                FragmentManager fm = getFragmentManager();
                                                FragmentTransaction ft = fm.beginTransaction();
                                                ft.replace(R.id.container, new before_sequence_practice(), "beforeseqprac")
                                                        .addToBackStack(null)
                                                        .commit();
                                            } catch (Throwable e) {
                                                Toast.makeText(getContext(), "Fragment change error " + e.toString(), Toast.LENGTH_LONG).show();
                                            }

                                        }
                                    })
                                    // A null listener allows the button to dismiss the dialog and take no further action.
                                    .setIcon(getResources().getDrawable(R.drawable.ic_baseline_done_outline_24))
                                    .show();
                        }catch (Throwable e){
                            Toast.makeText(getContext(), "alertdialog" + " " + e.toString(), Toast.LENGTH_LONG).show();
                        }




                    } catch (Throwable e) {
                        Toast.makeText(getContext(), "executetrans" + " " + e.toString(), Toast.LENGTH_LONG).show();
                    }finally {
                        addRealm.close();
                    }
                }
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == PICK_IMAGE){
            try {
            imageUri = data.getData();
                imageBmp = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(),imageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            imageView.setImageURI(imageUri);
            imageFile = new File(""+imageUri);

        }
    }
}