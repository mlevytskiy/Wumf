package io.wumf.wumf.asyncTask;

import android.content.Context;
import android.content.pm.ResolveInfo;
import android.os.AsyncTask;
import android.os.Handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.realm.Realm;
import io.wumf.wumf.otto.BusProvider;
import io.wumf.wumf.otto.event.LoadAppsNotFullEvent;
import io.wumf.wumf.realmObject.App;
import io.wumf.wumf.util.AppUtils;

/**
 * Created by max on 10.04.16.
 */
public abstract class LoadAllAppsAsyncTask extends AsyncTask<Void, Void, List<App>> {

    private AppUtils utils;
    private HashMap<App, ResolveInfo> map;

    public LoadAllAppsAsyncTask(Context context) {
        utils = new AppUtils(context);
        map = new HashMap<>();
    }

    @Override
    protected List<App> doInBackground(Void... params) {
        return utils.loadAllAppsFromSystem(map);
    }

    protected void onPostExecute(List<App> apps) {

        List<String> packages = new ArrayList<>();
        for (App app : apps) {
            packages.add(app.getPackageName());
        }
        BusProvider.getInstance().post(new LoadAppsNotFullEvent(packages));

        Realm.getDefaultInstance().beginTransaction();
        Realm.getDefaultInstance().copyToRealmOrUpdate(apps);
        Realm.getDefaultInstance().commitTransaction();
        startLoadAdditionalInfo(apps, map, utils);
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                dataIsReady();
            }
        });

    }

    protected abstract void dataIsReady();

    protected abstract void startLoadAdditionalInfo(List<App> apps, HashMap<App, ResolveInfo> map,
                                                    AppUtils utils);

}
