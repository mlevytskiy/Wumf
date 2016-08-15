package io.wumf.wumf;

import android.os.Bundle;
import android.text.TextUtils;

import com.squareup.otto.Subscribe;

import java.util.Map;

import co.moonmonkeylabs.realmrecyclerview.RealmRecyclerView;
import io.realm.Realm;
import io.wumf.wumf.activity.common.AnimationActivity;
import io.wumf.wumf.adapter.RemoteAppsAdapter;
import io.wumf.wumf.firebase.pojo.App;
import io.wumf.wumf.firebase.uploadDataToAppsNode.FirebaseAppsUtil;
import io.wumf.wumf.firebase.uploadDataToPlacesNode.FirebasePlaceUtils;
import io.wumf.wumf.firebase.uploadDataToPlacesNode.UsersCountListener;
import io.wumf.wumf.otto.BusProvider;
import io.wumf.wumf.otto.event.FirebaseLoadAppsFinishedEvent;
import io.wumf.wumf.otto.event.OnAppItemClickEvent;
import io.wumf.wumf.realmObject.RemoteApp;
import io.wumf.wumf.util.IntentApi;
import io.wumf.wumf.view.CustomTopBar;

/**
 * Created by max on 22.07.16.
 */
public class AppsActivity extends AnimationActivity {

    public static final String PLACE_ID_KEY = "regionIdKey";
    public static final String CITY_KEY = "city";
    public static final String COUNTRY_KEY = "country";

    private String placeId;

    private CustomTopBar topBar;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_apps);

        placeId = getIntent().getExtras().getString(PLACE_ID_KEY);
        FirebaseAppsUtil.loadAppsByPlace(placeId);

        RealmRecyclerView realmRecyclerView = (RealmRecyclerView) findViewById(R.id.realm_recycler_view);
        realmRecyclerView.setAdapter(new RemoteAppsAdapter(this, placeId));

        topBar = (CustomTopBar) findViewById(R.id.top_panel);

        final String country = getIntent().getExtras().getString(COUNTRY_KEY);
        final String city = getIntent().getExtras().getString(CITY_KEY);
        topBar.setText(country, city, 0);

        FirebasePlaceUtils.loadUsersCountByPlace(placeId, new UsersCountListener() {
            @Override
            public void getResult(int usersCount) {
                final String countryCode = getCountryCode(country);
                topBar.setText(countryCode, city, usersCount);
            }
        });
    }

    public void onStart() {
        super.onStart();
        BusProvider.getInstance().register(this);
        topBar.bind(this);
    }

    public void onStop() {
        super.onStop();
        BusProvider.getInstance().unregister(this);
        topBar.unbind();
    }

    @Subscribe
    public void onItemClickEvent(OnAppItemClickEvent event) {
        try {
            startActivity(IntentApi.openGooglePlayPage(event.appId));
        } catch (Exception e) {
            //do nothing
        }
    }

    @Subscribe
    public void receiveApps(FirebaseLoadAppsFinishedEvent event) {
        Realm.getDefaultInstance().beginTransaction();
        for (App app : event.apps) {
            RemoteApp remoteApp = new RemoteApp();
            remoteApp.setName(app.name);
            remoteApp.setIcon(app.icon);
            remoteApp.setPackageName(app.packageName);
            remoteApp.setRegionId(placeId);
            remoteApp.setUsersCount(app.usersCount);
            Realm.getDefaultInstance().copyToRealmOrUpdate(remoteApp);
        }
        Realm.getDefaultInstance().commitTransaction();
    }

    private String getCountryCode(String country) {
        String[] codes = new String[2];
        for (Map.Entry<String, String> entry : CountriesCodes.map.entrySet()) {
            if (TextUtils.equals(country, entry.getValue())) {
                if ( TextUtils.isEmpty(codes[0]) ) {
                    codes[0] =  entry.getKey();
                } else {
                    codes[1] = entry.getKey();
                    return (codes[0].length() < codes[1].length()) ?
                            codes[0] : codes[1];
                }
            }
        }
        return "";
    }

}
