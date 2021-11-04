package com.example.fogas;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import com.nain.securerealmdb.key.RealmEncryptionKeyProvider;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class realmInit extends Application {


    @Override
    public void onCreate() {
        Log.v("valami" ,Thread.currentThread().getName());
        super.onCreate();
        Realm.init(getApplicationContext());
        //for basic realm
        //RealmConfiguration configuration = new RealmConfiguration.Builder().name("RealmData.realm").build();
        Realm.setDefaultConfiguration(initializeRealmConfig());
        //Log.v("valami" ,Thread.currentThread().getName());
    }

    public RealmConfiguration initializeRealmConfig() {
        RealmEncryptionKeyProvider realmEncryptionKeyProvider = new RealmEncryptionKeyProvider(this);
        byte [] encryptionKey = realmEncryptionKeyProvider.getSecureRealmKey();

        return new RealmConfiguration.Builder()
                .name("RealmData.realm")
                .schemaVersion(10)
                .allowWritesOnUiThread(true)
                .build();

    }


}
