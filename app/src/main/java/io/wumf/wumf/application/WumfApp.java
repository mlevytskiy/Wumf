package io.wumf.wumf.application;

import android.app.Application;
import android.content.Context;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tamir7.contacts.Contacts;
import com.squareup.otto.Subscribe;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.wumf.wumf.CountriesCodes;
import io.wumf.wumf.R;
import io.wumf.wumf.firebase.uploadDataToAppsNode.FirebaseAppsUtil;
import io.wumf.wumf.firebase.uploadDataToPhonesNode.FirebasePhonesUtil;
import io.wumf.wumf.memory.Memory;
import io.wumf.wumf.otto.BusProvider;
import io.wumf.wumf.otto.event.FirebaseUploadFinishedEvent;
import io.wumf.wumf.otto.event.FirebaseUploadStartedEvent;
import io.wumf.wumf.otto.event.LoadAppsFinishEvent;
import io.wumf.wumf.otto.event.LoadAppsNotFullEvent;
import io.wumf.wumf.otto.event.UploadDataPart1FinishedEvent;
import io.wumf.wumf.util.PhoneNumberDetector;
import io.wumf.wumf.util.phoneNumberDetectorImpl.PhoneNumberProvider;

/**
 * Created by max on 30.03.16.
 */
public class WumfApp extends Application {

    private static final String TAG = WumfApp.class.getSimpleName();
    public static WumfApp instance;
    private static final String REALM_DATABASE = "myrealm.realm";
    private static final String UNKNOWN = "UNKNOWN";
    private static final int REALM_VERSION = 42;
    public Map<PhoneNumberProvider, String> phones;
    public Map<String, List<String>> map;
    public String userCountry;
    public String userCity;
    public String androidId;

    public void onCreate() {
        super.onCreate();
        instance = this;
        androidId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        initRealm();
        phones = PhoneNumberDetector.getPhones(this);
        BusProvider.getInstance().register(this);
        Memory.INSTANCE.init(this);
        Contacts.initialize(this);

        ObjectMapper objectMapper = new ObjectMapper();

        InputStream ins = getResources().openRawResource(R.raw.countries_to_cities);

        TypeReference<HashMap<String, List<String>>> typeRef
                = new TypeReference<HashMap<String, List<String>>>() {};

        try {
            map = objectMapper.readValue(ins, typeRef);
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }
        String userCountryCode = getUserCountryCode(this).toUpperCase();
        if (TextUtils.equals(UNKNOWN, userCountryCode)) {

        } else {
            userCountry = CountriesCodes.map.get(userCountryCode);
        }
    }

    private static String getUserCountryCode(Context context) {
        try {
            final TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if ( !hasSimCard(tm) ) {
                return UNKNOWN;
            }
            final String simCountry = tm.getSimCountryIso();
            if (simCountry != null && simCountry.length() == 2) { // SIM country code is available
                return simCountry.toLowerCase(Locale.US);
            }
            else if (tm.getPhoneType() != TelephonyManager.PHONE_TYPE_CDMA) { // device is not 3G (would be unreliable)
                String networkCountry = tm.getNetworkCountryIso();
                if (networkCountry != null && networkCountry.length() == 2) { // network country code is available
                    return networkCountry.toLowerCase(Locale.US);
                }
            }
        }
        catch (Exception e) { }
        return null;
    }

    private static boolean hasSimCard(TelephonyManager tm) {
        return  (tm.getSimState() != TelephonyManager.SIM_STATE_ABSENT);
    }

    public void initRealm() {
        RealmConfiguration realmConfig = new RealmConfiguration.Builder(WumfApp.this)
                .name(REALM_DATABASE)
                .schemaVersion(REALM_VERSION)
                .build();
        Realm.setDefaultConfiguration(realmConfig);
    }

//    public void setFriends(List<Contact> friends) {
//        if (this.friends.isEmpty()) {
//            this.friends = friends;
//            FirebasePhonesUtil.load(friends);
////            BusProvider.getInstance().post(new LoadContactsFinishedEvent(friends));
//        } else {
//            //do nothing
//        }
//    }

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
    public void firebaseUploadStartedEvent(FirebaseUploadStartedEvent event) {

    }

    @Subscribe
    public void firebaseUploadFinishedEvent(FirebaseUploadFinishedEvent event) {
//        if ( !friends.isEmpty() ) {
//            FirebasePhonesUtil.load(friends);
//        }
    }

}
