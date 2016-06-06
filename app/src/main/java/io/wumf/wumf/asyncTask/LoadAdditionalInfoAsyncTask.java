package io.wumf.wumf.asyncTask;

import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.AsyncTask;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.realm.Realm;
import io.wumf.wumf.otto.BusProvider;
import io.wumf.wumf.otto.event.LoadAppsFinishEvent;
import io.wumf.wumf.realmObject.App;
import io.wumf.wumf.util.AppUtils;

/**
 * Created by max on 10.04.16.
 */
public class LoadAdditionalInfoAsyncTask extends AsyncTask<Void, List<App>, List<io.wumf.wumf.pojo.App>> {

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
    protected List<io.wumf.wumf.pojo.App> doInBackground(Void... params) {
        for (int i = 10; i < apps.size(); i = i + STEP) {
            publishProgress(utils.loadNext(apps, map, i, STEP));
        }

        List<io.wumf.wumf.pojo.App> list = new ArrayList<>();

        for (App app : apps) {
            list.add(createFromApp(app));
        }

        return list;
    }

    protected void onProgressUpdate(List<App>... progress) {
        Realm.getDefaultInstance().beginTransaction();
        Realm.getDefaultInstance().copyToRealmOrUpdate(progress[0]);
        Realm.getDefaultInstance().commitTransaction();
    }

    protected void onPostExecute(List<io.wumf.wumf.pojo.App> result) {
        BusProvider.getInstance().post(new LoadAppsFinishEvent(result));
    }

    private io.wumf.wumf.pojo.App createFromApp(App app) {
        io.wumf.wumf.pojo.App result = new io.wumf.wumf.pojo.App(Uri.fromFile(new File(app.getIconPath())),
                app.getLabel(), app.getPackageName());
        return result;
    }
}
