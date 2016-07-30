package io.wumf.wumf;

import android.os.Bundle;

import com.squareup.otto.Subscribe;

import co.moonmonkeylabs.realmrecyclerview.RealmRecyclerView;
import io.realm.Realm;
import io.wumf.wumf.activity.common.AnimationActivity;
import io.wumf.wumf.adapter.RemoteAppsAdapter;
import io.wumf.wumf.application.WumfApp;
import io.wumf.wumf.firebase.pojo.App;
import io.wumf.wumf.firebase.uploadDataToPlacesNode.FirebasePlaceUtils;
import io.wumf.wumf.otto.BusProvider;
import io.wumf.wumf.otto.event.FirebaseLoadAppsFinishedEvent;
import io.wumf.wumf.realmObject.RemoteApp;

/**
 * Created by max on 22.07.16.
 */
public class AppsActivity extends AnimationActivity {

    public static String PLACE_ID_KEY = "regionIdKey";
    private String placeId;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_apps);

        WumfApp application = (WumfApp) getApplication();
        FirebasePlaceUtils.loadAppsByPlace(application.userCountry, application.userCity);
        placeId = getIntent().getExtras().getString(PLACE_ID_KEY);

        RealmRecyclerView realmRecyclerView = (RealmRecyclerView) findViewById(R.id.realm_recycler_view);
        realmRecyclerView.setAdapter(new RemoteAppsAdapter(this, placeId));

    }

    public void onStart() {
        super.onStart();
        BusProvider.getInstance().register(this);

    }

    public void onStop() {
        super.onStop();
        BusProvider.getInstance().unregister(this);

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
            Realm.getDefaultInstance().copyToRealmOrUpdate(remoteApp);
        }
        Realm.getDefaultInstance().commitTransaction();
    }

}
