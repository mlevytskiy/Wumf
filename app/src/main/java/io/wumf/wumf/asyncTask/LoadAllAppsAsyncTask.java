package io.wumf.wumf.asyncTask;

import android.content.Context;
import android.content.pm.ResolveInfo;
import android.os.AsyncTask;

import java.util.HashMap;
import java.util.List;

import io.realm.Realm;
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
        Realm.getDefaultInstance().beginTransaction();
        Realm.getDefaultInstance().copyToRealmOrUpdate(apps);
        Realm.getDefaultInstance().commitTransaction();
        gotoMainActivity();
        startLoadAdditionalInfo(apps, map, utils);
    }

    protected abstract void gotoMainActivity();

    protected abstract void startLoadAdditionalInfo(List<App> apps, HashMap<App, ResolveInfo> map,
                                                    AppUtils utils);

}
