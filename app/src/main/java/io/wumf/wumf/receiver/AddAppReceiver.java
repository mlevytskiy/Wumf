package io.wumf.wumf.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by max on 25.03.16.
 */
public class AddAppReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String packageName = intent.getData().getEncodedSchemeSpecificPart();
        Toast.makeText(context, "add app " + packageName, Toast.LENGTH_LONG).show();
    }

}
