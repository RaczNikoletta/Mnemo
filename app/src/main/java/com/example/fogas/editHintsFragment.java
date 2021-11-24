package com.example.fogas;

import static android.app.Activity.RESULT_OK;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fogas.Models.HintDataModel;
import com.example.fogas.Models.HintModel;
import com.example.fogas.Models.UserDataModel;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import io.realm.Realm;

public class editHintsFragment extends Fragment {

    private String pegNum;
    private String[] splitedPeg;
    private TextView fogasEditTv;
    private EditText hintStringEt2;
    private ImageView imageView10;
    private Button browseButton2;
    private Button saveHintsBtn2;
    private Realm editHintRealm;
    private UserDataModel user;
    private HintDataModel hints;
    private HintModel hint;
    private static final int PICK_IMAGE = 100;
    private Uri imageUri;
    private Bitmap imageBmp;
    private File imageFile;
    private long fileSizeInMB;

    public editHintsFragment() {
        // Required empty public constructor
    }

    public static editHintsFragment newInstance(String pegNum) {
        editHintsFragment f = new editHintsFragment();
        Bundle args = new Bundle();
        f.setArguments(args);
        args.putString("pegNum", pegNum);
        f.setArguments(args);
        return f;
    }

    /*fogasEditTv;
    private EditText hintStringEt2;
    private ImageView imageView10;
    private Button browseButton;
    private Button saveHintsBtn2;-

     */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_hints, container, false);
        fogasEditTv = (TextView) view.findViewById(R.id.fogasEditTv);
        imageView10 = (ImageView) view.findViewById(R.id.imageView10);
        browseButton2 = (Button) view.findViewById(R.id.browseBtn2);
        saveHintsBtn2 = (Button) view.findViewById(R.id.saveHintsBtn2);
        try {
            Bundle args = getArguments();
            pegNum = args.getString("pegNum", "");
        } catch (Throwable e) {
            Toast.makeText(getContext(), "arguments error " + e.toString(), Toast.LENGTH_LONG).show();
        }
        Toast.makeText(getContext(), pegNum, Toast.LENGTH_LONG).show();
        // split into pegnum and hintstring
        splitedPeg = pegNum.split("\\s+");

        fogasEditTv.setText(splitedPeg[0]);

        try {
            editHintRealm = Realm.getDefaultInstance();

            user = editHintRealm.where(UserDataModel.class).equalTo("loggedIn", true).findFirst();
            hints = user.getHints();
            hint = hints.getOneHint(Integer.parseInt(splitedPeg[0]));
        } catch (Throwable e) {
            Toast.makeText(getContext(), "Database error: " + e.toString(), Toast.LENGTH_LONG).show();
        }
        try {

            Bitmap bmp = BitmapFactory.decodeByteArray(hint.getImage(), 0, hint.getImage().length);
            imageView10.setImageBitmap(Bitmap.createScaledBitmap(bmp, 600, 600, false));
        } catch (Throwable e) {
            Toast.makeText(getContext(), "Bitmap error: " + e.toString(), Toast.LENGTH_LONG).show();
        }

        view.findViewById(R.id.browseBtn2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(gallery,PICK_IMAGE);
            }
        });

        view.findViewById(R.id.saveHintsBtn2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                imageBmp.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                long fileSizeinBytes = imageFile.length();
                long fileSizeInKB = fileSizeinBytes / 1024;
                fileSizeInMB = fileSizeInKB / 1024;

                //TODO test with bigger image than 16MB
                if (fileSizeInMB > 16) {
                    new AlertDialog.Builder(getContext())
                            .setTitle(R.string.loginrequest + " " + fileSizeInMB)
                            .setMessage(R.string.loginrequest2 + " " + fileSizeInMB)

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
                        editHintRealm.executeTransaction(r -> {
                            try {
                                hint = new HintModel();
                                hint.setPegnum(Integer.parseInt(fogasEditTv.getText().toString()));
                                hint.setImage(byteArray);
                                editHintRealm.insertOrUpdate(editHintRealm.copyToRealmOrUpdate(hint));
                            }catch (Throwable e){
                                Toast.makeText(getContext(), "hint" + " " + e.toString(), Toast.LENGTH_LONG).show();
                            }
                            HintDataModel usdata = user.getHints();

                            usdata.setOneHint(hint);





                        });
                        try {
                            new AlertDialog.Builder(getContext())
                                    .setTitle(R.string.databaseUpdated)
                                    .setMessage(R.string.databaseUpdated2)

                                    // Specifying a listener allows you to take an action before dismissing the dialog.
                                    // The dialog is automatically dismissed when a dialog button is clicked.
                                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            try {
                                                FragmentManager fm = getFragmentManager();
                                                FragmentTransaction ft = fm.beginTransaction();
                                                ft.replace(R.id.container, new hintUpdateFragment(), "hintUpdate")
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
                        editHintRealm.close();
                    }
                }
            }

        });

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            try {
                imageUri = data.getData();
                imageBmp = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), imageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            imageView10.setImageURI(imageUri);
            imageFile = new File("" + imageUri);

        }

    }
}