package com.example.fogas;

import android.app.Application;

import com.nain.securerealmdb.key.RealmEncryptionKeyProvider;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class realmInit extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        //for basic realm
        //RealmConfiguration configuration = new RealmConfiguration.Builder().name("RealmData.realm").build();
        Realm.setDefaultConfiguration(initializeRealmConfig());
    }

    private RealmConfiguration initializeRealmConfig() {
        RealmEncryptionKeyProvider realmEncryptionKeyProvider = new RealmEncryptionKeyProvider(this);
        byte [] encryptionKey = realmEncryptionKeyProvider.getSecureRealmKey();

        return new RealmConfiguration.Builder()
                .schemaVersion(10)
                .encryptionKey(encryptionKey)
                .build();
    }

}
