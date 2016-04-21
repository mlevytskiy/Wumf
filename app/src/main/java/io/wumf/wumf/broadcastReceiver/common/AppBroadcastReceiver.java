package io.wumf.wumf.broadcastReceiver.common;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import io.realm.Realm;
import io.realm.RealmResults;
import io.wumf.wumf.realmObject.App;

/**
 * Created by max on 21.04.16.
 */
public abstract class AppBroadcastReceiver extends BroadcastReceiver {

    @Override
    public final void onReceive(Context context, Intent intent) {
        String packageName = intent.getData().getEncodedSchemeSpecificPart();
        if (TextUtils.equals(context.getApplicationContext().getPackageName(), packageName)) {
            //ignore
            return;
        }
        RealmResults<App> apps = Realm.getDefaultInstance().where(App.class).equalTo("packageName", packageName).findAll();
        onReceive(context, packageName, apps);
    }

    public abstract void onReceive(Context context, String packageName, RealmResults<App> apps);

}
