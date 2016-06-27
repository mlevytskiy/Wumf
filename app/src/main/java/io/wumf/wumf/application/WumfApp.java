package io.wumf.wumf.application;

import android.app.Application;
import android.content.Context;

import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.wumf.wumf.firebase.FirebaseUtil;
import io.wumf.wumf.firebase.MyDeviceInfo;
import io.wumf.wumf.firebase2.FirebaseApi;
import io.wumf.wumf.friends.Friend;
import io.wumf.wumf.otto.BusProvider;
import io.wumf.wumf.otto.event.LoadAppsFinishEvent;
import io.wumf.wumf.otto.event.LoadAppsNotFullEvent;
import io.wumf.wumf.otto.event.LoadContactsFinishedEvent;
import io.wumf.wumf.util.PhoneNumberDetector;
import io.wumf.wumf.util.phoneNumberDetectorImpl.PhoneNumberProvider;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by max on 30.03.16.
 */
public class WumfApp extends Application {

    public static WumfApp instance;
    private static final String REALM_DATABASE = "myrealm.realm";
    private static final int REALM_VERSION = 42;
    private List<Friend> friends = new ArrayList<>();
    public Map<PhoneNumberProvider, String> phones;

    private final CompositeSubscription subscription = new CompositeSubscription();

    public void onCreate() {
        super.onCreate();
        instance = this;
        initRealm();
        phones = PhoneNumberDetector.getPhones(this);
        BusProvider.getInstance().register(this);
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
        BusProvider.getInstance().post(new LoadContactsFinishedEvent());
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    @Subscribe
    public void uploadMyInfoInFirebase(LoadAppsNotFullEvent event) {
        MyDeviceInfo myDeviceInfo = new MyDeviceInfo();
        myDeviceInfo.setPhoneModel("Samsung s3");
        FirebaseUtil.setMyInfo("+38063767443", myDeviceInfo);
    }

    @Subscribe
    public void uploadMyInfoInFirebase(LoadAppsFinishEvent event) {
        MyDeviceInfo myDeviceInfo = new MyDeviceInfo();
        myDeviceInfo.setPhoneModel("hhhhhh");
        FirebaseApi.setFullMyInfo("8889", myDeviceInfo, event.apps);
    }

}
