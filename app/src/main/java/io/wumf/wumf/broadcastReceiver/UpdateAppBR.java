package io.wumf.wumf.broadcastReceiver;

import android.content.Context;

import io.realm.RealmResults;
import io.wumf.wumf.broadcastReceiver.common.AppBroadcastReceiver;
import io.wumf.wumf.realmObject.App;

/**
 * Created by max on 28.03.16.
 */
public class UpdateAppBR extends AppBroadcastReceiver {

    @Override
    public void onReceive(Context context, String packageName, RealmResults<App> apps) {

    }

}
