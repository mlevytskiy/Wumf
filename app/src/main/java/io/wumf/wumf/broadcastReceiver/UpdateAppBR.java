package io.wumf.wumf.broadcastReceiver;

import android.content.Context;

import io.realm.Realm;
import io.realm.RealmResults;
import io.wumf.wumf.broadcastReceiver.common.AppBroadcastReceiver;
import io.wumf.wumf.realmObject.App;
import io.wumf.wumf.realmObject.EventType;

/**
 * Created by max on 28.03.16.
 */
public class UpdateAppBR extends AppBroadcastReceiver {

    @Override
    public void onReceive(Context context, String packageName, RealmResults<App> apps, Realm realm) {
        realm.beginTransaction();
        for (int i = 0; i < apps.size(); i++) {
            addUpdateAppEvent(apps.get(i), realm);
        }
        realm.commitTransaction();
    }

    private void addUpdateAppEvent(App app, Realm realm) {
        addSimpleEvent(app, realm, EventType.UPDATE);
    }

}
