package io.wumf.wumf.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import io.wumf.wumf.memory.App;
import io.wumf.wumf.memory.AppsManager;

/**
 * Created by max on 25.03.16.
 */
public class RemoveAppReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String packageName = intent.getData().getEncodedSchemeSpecificPart();
        AppsManager appsManager = new AppsManager();
        App app = appsManager.find(packageName);
        appsManager.getRealm().beginTransaction();
        app.setRemoved(true);
        appsManager.getRealm().commitTransaction();
        Toast.makeText(context, "remove app " + packageName, Toast.LENGTH_LONG).show();
    }

}
