package io.wumf.wumf.application;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.wumf.wumf.friends.Friend;

/**
 * Created by max on 30.03.16.
 */
public class WumfApp extends Application {

    public static WumfApp instance;
    private static final String REALM_DATABASE = "myrealm.realm";
    private static final int REALM_VERSION = 42;
    private List<Friend> friends = new ArrayList<>();

    public void onCreate() {
        super.onCreate();
        instance = this;
        initRealm();
    }

    public void initRealm() {
        RealmConfiguration realmConfig = new RealmConfiguration.Builder(WumfApp.this)
                .name(REALM_DATABASE)
                .schemaVersion(REALM_VERSION)
                .build();
        Realm.setDefaultConfiguration(realmConfig);
    }

    public List<Friend> getFriends() {
        return friends;
    }

    public void setFriends(List<Friend> friends) {
        this.friends = friends;
    }
}
