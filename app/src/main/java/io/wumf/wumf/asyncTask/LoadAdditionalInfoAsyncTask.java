package io.wumf.wumf.asyncTask;

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
public class LoadAdditionalInfoAsyncTask extends AsyncTask<Void, List<App>, Void> {

    private static final int STEP = 10;
    private AppUtils utils;
    private HashMap<App, ResolveInfo> map;
    private List<App> apps;

    public LoadAdditionalInfoAsyncTask(AppUtils utils, HashMap<App, ResolveInfo> map, List<App> apps) {
        this.utils = utils;
        this.map = map;
        this.apps = apps;
    }

    @Override
    protected Void doInBackground(Void... params) {
        for (int i = 10; i < apps.size(); i = i + STEP) {
            publishProgress(utils.loadNext(apps, map, i, STEP));
        }
        return null;
    }

    protected void onProgressUpdate(List<App>... progress) {
        Realm.getDefaultInstance().beginTransaction();
        Realm.getDefaultInstance().copyToRealmOrUpdate(progress[0]);
        Realm.getDefaultInstance().commitTransaction();
    }
}
