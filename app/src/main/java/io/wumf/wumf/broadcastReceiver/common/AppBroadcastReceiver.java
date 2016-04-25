package io.wumf.wumf.broadcastReceiver.common;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import io.realm.Realm;
import io.realm.RealmResults;
import io.wumf.wumf.realmObject.App;
import io.wumf.wumf.realmObject.Event;
import io.wumf.wumf.realmObject.EventType;

/**
 * Created by max on 21.04.16.
 */
public abstract class AppBroadcastReceiver extends BroadcastReceiver {

    @Override
    public final void onReceive(Context context, Intent intent) {
        String packageName = intent.getData().getEncodedSchemeSpecificPart();
        Realm realm = Realm.getDefaultInstance();
        if (TextUtils.equals(context.getApplicationContext().getPackageName(), packageName) || realm.isEmpty()) {
            //ignore
            return;
        }
        RealmResults<App> apps = realm.where(App.class).equalTo("packageName", packageName).findAll();
        onReceive(context, packageName, apps, realm, intent);
    }

    public abstract void onReceive(Context context, String packageName, RealmResults<App> apps, Realm realm, Intent intent);

    protected final Event addSimpleEvent(App app, Realm realm, EventType eventType) {
        long currentTime = System.currentTimeMillis();
        Event event = realm.createObject(Event.class);
        event.setApp(app);
        event.setTime(currentTime);
        event.setTimeAndAppPrimaryKey(app.getLauncherActivity() + currentTime);
        event.setEventType(EventType.toInt(eventType));
        return event;
    }

}
