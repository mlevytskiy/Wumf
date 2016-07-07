package io.wumf.wumf.application;

import android.app.Application;
import android.content.Context;

import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.wumf.wumf.contacts.Contact;
import io.wumf.wumf.firebase.pojo.UploadStatus;
import io.wumf.wumf.firebase.uploadDataToAppsNode.FirebaseAppsUtil;
import io.wumf.wumf.firebase.uploadDataToPhonesNode.FirebasePhonesUtil;
import io.wumf.wumf.memory.Memory;
import io.wumf.wumf.otto.BusProvider;
import io.wumf.wumf.otto.event.FirebaseLoadFriendsFinishedEvent;
import io.wumf.wumf.otto.event.FirebaseUploadFinishedEvent;
import io.wumf.wumf.otto.event.FirebaseUploadStartedEvent;
import io.wumf.wumf.otto.event.LoadAppsFinishEvent;
import io.wumf.wumf.otto.event.LoadAppsNotFullEvent;
import io.wumf.wumf.otto.event.LoadContactsFinishedEvent;
import io.wumf.wumf.otto.event.UploadDataPart1FinishedEvent;
import io.wumf.wumf.pojo.App;
import io.wumf.wumf.util.PhoneNumberDetector;
import io.wumf.wumf.util.phoneNumberDetectorImpl.PhoneNumberProvider;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by max on 30.03.16.
 */
public class WumfApp extends Application {

    private static final String TAG = WumfApp.class.getSimpleName();
    public static WumfApp instance;
    private static final String REALM_DATABASE = "myrealm.realm";
    private static final int REALM_VERSION = 42;
    private List<Contact> friends = new ArrayList<>();
    public Map<Contact, List<App>> friendsFullInfo;
    public Map<PhoneNumberProvider, String> phones;
    private UploadStatus firebaseUploadStatus = UploadStatus.NOT_STARTING;

    private final CompositeSubscription subscription = new CompositeSubscription();

    public void onCreate() {
        super.onCreate();
        instance = this;
        initRealm();
        phones = PhoneNumberDetector.getPhones(this);
        BusProvider.getInstance().register(this);
        Memory.INSTANCE.init(this);
    }

    public void initRealm() {
        RealmConfiguration realmConfig = new RealmConfiguration.Builder(WumfApp.this)
                .name(REALM_DATABASE)
                .schemaVersion(REALM_VERSION)
                .build();
        Realm.setDefaultConfiguration(realmConfig);
    }

    public List<Contact> getFriends() {
        return friends;
    }

    public void setFriends(List<Contact> friends) {
        if (this.friends.isEmpty()) {
            this.friends = friends;
            FirebasePhonesUtil.load(friends);
//            BusProvider.getInstance().post(new LoadContactsFinishedEvent(friends));
        } else {
            //do nothing
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    @Subscribe
    public void uploadMyInfoInFirebase(LoadAppsNotFullEvent event) {
//        MyDeviceInfo myDeviceInfo = new MyDeviceInfo();
//        myDeviceInfo.setPhoneModel("Samsung s3");
//        FirebaseUtil.setMyInfo("+38063767443", myDeviceInfo);
    }

    @Subscribe
    public void uploadMyInfoInFirebase(LoadAppsFinishEvent event) {
        FirebaseAppsUtil.upload(event.apps);
//        MyDeviceInfo myDeviceInfo = new MyDeviceInfo();
//        myDeviceInfo.setPhoneModel("hhhhhh");
//        FirebaseApi.setFullMyInfo("8889", myDeviceInfo, event.apps);
    }

    @Subscribe
    public void uploadDataPart1(UploadDataPart1FinishedEvent event) {
        FirebasePhonesUtil.upload(event.apps);
    }

    @Subscribe
    public void loadContacts(LoadContactsFinishedEvent event) {
        if (firebaseUploadStatus == UploadStatus.NOT_STARTING) {
            FirebasePhonesUtil.load(event.friends);
        }
    }

    @Subscribe
    public void firebaseUploadStartedEvent(FirebaseUploadStartedEvent event) {
        firebaseUploadStatus = UploadStatus.NOT_STARTING;
    }

    @Subscribe
    public void firebaseUploadFinishedEvent(FirebaseUploadFinishedEvent event) {
        firebaseUploadStatus = UploadStatus.FINISH;
        if ( !friends.isEmpty() ) {
            FirebasePhonesUtil.load(friends);
        }
    }

    @Subscribe
    public void firebaseLoadFriendsFinishedEvent(FirebaseLoadFriendsFinishedEvent event) {
        friendsFullInfo = event.map;
    }

}
