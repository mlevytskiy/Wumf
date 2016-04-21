package io.wumf.wumf.util.prepareData;

import android.content.Context;
import android.content.pm.ResolveInfo;

import java.util.HashMap;
import java.util.List;

import io.realm.Realm;
import io.wumf.wumf.asyncTask.LoadAdditionalInfoAsyncTask;
import io.wumf.wumf.asyncTask.LoadAllAppsAsyncTask;
import io.wumf.wumf.realmObject.App;
import io.wumf.wumf.util.AppUtils;

/**
 * Created by max on 15.04.16.
 */
public class PrepareDataUtil {

    public static void prepare(Context context, PrepareDataCallback callback) {
        if (realmIsEmpty()) {
            loadingData(context, callback);
        } else {
            callback.dataIsReady();
        }
    }

    private static void loadingData(Context context, final PrepareDataCallback callback) {
        new LoadAllAppsAsyncTask(context) {

            @Override
            protected void dataIsReady() {
                callback.dataIsReady();
            }

            @Override
            protected void startLoadAdditionalInfo(List<App> apps, HashMap<App, ResolveInfo> map,
                                                   AppUtils utils) {
                new LoadAdditionalInfoAsyncTask(utils, map, apps).execute();
            }

        }.execute();
    }

    private static boolean realmIsEmpty() {
        return Realm.getDefaultInstance().isEmpty();
    }

}
