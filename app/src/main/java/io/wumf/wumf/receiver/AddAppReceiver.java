package io.wumf.wumf.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import io.realm.Realm;
import io.realm.RealmResults;
import io.wumf.wumf.asyncTask.AddAppAsyncTask;
import io.wumf.wumf.asyncTask.UpdateAppAsyncTask;
import io.wumf.wumf.realmObject.App;


/**
 * Created by max on 25.03.16.
 */
public class AddAppReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String packageName = intent.getData().getEncodedSchemeSpecificPart();
        if (TextUtils.equals(context.getApplicationContext().getPackageName(), packageName)) {
            //ignore
            return;
        }
        RealmResults<App> apps = Realm.getDefaultInstance().where(App.class).equalTo("packageName", packageName).findAll();
        if (apps.isEmpty()) {
            new AddAppAsyncTask(context).execute(packageName);
        } else {
            for (App app : apps) {
                new UpdateAppAsyncTask(context, app).execute();
            }
        }
    }

}
