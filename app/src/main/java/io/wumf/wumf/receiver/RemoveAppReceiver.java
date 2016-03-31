package io.wumf.wumf.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import io.realm.Realm;
import io.wumf.wumf.realmObject.App;
import io.wumf.wumf.util.FindApps;

/**
 * Created by max on 25.03.16.
 */
public class RemoveAppReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String packageName = intent.getData().getEncodedSchemeSpecificPart();
        App app = FindApps.findApp(packageName);
        if (app != null) {
            Realm.getDefaultInstance().beginTransaction();
            app.setRemoved(true);
            Realm.getDefaultInstance().commitTransaction();
        } else {
            // Todo@max: need do something
        }
        Toast.makeText(context, "remove app " + packageName, Toast.LENGTH_LONG).show();
    }

}
