package io.wumf.wumf.application;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by max on 30.03.16.
 */
public class WumfApp extends Application {

    private static final String REALM_DATABASE = "myrealm.realm";
    private static final int REALM_VERSION = 42;

    public void onCreate() {
        super.onCreate();
        initRealm();
    }

    private void initRealm() {
        RealmConfiguration realmConfig = new RealmConfiguration.Builder(this)
                .name(REALM_DATABASE)
                .schemaVersion(REALM_VERSION)
                .build();
        Realm.setDefaultConfiguration(realmConfig);
    }

}
