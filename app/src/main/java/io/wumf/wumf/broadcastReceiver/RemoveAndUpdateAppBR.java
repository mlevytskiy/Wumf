package io.wumf.wumf.broadcastReceiver;

import android.content.Context;
import android.content.Intent;

import io.realm.Realm;
import io.realm.RealmResults;
import io.wumf.wumf.broadcastReceiver.common.AppBroadcastReceiver;
import io.wumf.wumf.realmObject.App;
import io.wumf.wumf.realmObject.EventType;

/**
 * Created by max on 25.03.16.
 */
public class RemoveAndUpdateAppBR extends AppBroadcastReceiver {

    @Override
    public void onReceive(Context context, String packageName, RealmResults<App> apps, Realm realm, Intent intent) {
        if (apps.isEmpty()) {
            //do nothing
        } else {
            boolean isUpdate = (intent.getAction().equals(Intent.ACTION_PACKAGE_REPLACED) ||
                    intent.getBooleanExtra(Intent.EXTRA_REPLACING, false));
            realm.beginTransaction();
            for (int i = 0; i < apps.size(); i++) {
                updateOrRemove(apps.get(i), realm, isUpdate);
            }
            realm.commitTransaction();
        }
    }

    private void updateOrRemove(App app, Realm realm, boolean isUpdate) {
        if (isUpdate) {
            addUpdateAppEvent(app, realm);
        } else {
            addRemoveAppEvent(app, realm);
        }
    }

    private void addRemoveAppEvent(App app, Realm realm) {
        addSimpleEvent(app, realm, EventType.REMOVE);
        app.setRemoved(true);
    }

    private void addUpdateAppEvent(App app, Realm realm) {
        addSimpleEvent(app, realm, EventType.UPDATE);
    }

}
