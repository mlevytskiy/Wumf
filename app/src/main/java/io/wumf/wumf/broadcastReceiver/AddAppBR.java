package io.wumf.wumf.broadcastReceiver;

import android.content.Context;

import io.realm.Realm;
import io.realm.RealmResults;
import io.wumf.wumf.asyncTask.AddAppAsyncTask;
import io.wumf.wumf.asyncTask.UpdateAppAsyncTask;
import io.wumf.wumf.broadcastReceiver.common.AppBroadcastReceiver;
import io.wumf.wumf.realmObject.App;


/**
 * Created by max on 25.03.16.
 */
public class AddAppBR extends AppBroadcastReceiver {

    @Override
    public void onReceive(Context context, String packageName, RealmResults<App> apps, Realm realm) {
        if (apps.isEmpty()) {
            new AddAppAsyncTask(context).execute(packageName);
        } else {
            for (App app : apps) {
                new UpdateAppAsyncTask(context, app).execute();
            }
        }
    }

}
