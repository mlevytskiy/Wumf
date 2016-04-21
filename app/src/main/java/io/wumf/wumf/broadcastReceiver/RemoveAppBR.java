package io.wumf.wumf.broadcastReceiver;

import android.content.Context;

import io.realm.Realm;
import io.realm.RealmResults;
import io.wumf.wumf.broadcastReceiver.common.AppBroadcastReceiver;
import io.wumf.wumf.realmObject.App;
import io.wumf.wumf.realmObject.Event;
import io.wumf.wumf.realmObject.EventType;

/**
 * Created by max on 25.03.16.
 */
public class RemoveAppBR extends AppBroadcastReceiver {

    @Override
    public void onReceive(Context context, String packageName, RealmResults<App> apps) {
        if (apps.isEmpty()) {
            //do nothing
        } else {
            Realm realm = Realm.getDefaultInstance();
            realm.beginTransaction();
            for (int i = 0; i < apps.size(); i++) {
                addRemoveAppEvent(apps.get(i), realm);
            }
            realm.commitTransaction();
        }
    }

    private void addRemoveAppEvent(App app, Realm realm) {
        long currentTime = System.currentTimeMillis();
        Event event = realm.createObject(Event.class);
        event.setApp(app);
        event.setTime(currentTime);
        event.setTimeAndAppPrimaryKey(app.getLauncherActivity() + currentTime);
        event.setEventType(EventType.toInt(EventType.REMOVE));
    }

}
