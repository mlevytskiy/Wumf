package io.wumf.wumf.util;

import android.content.Intent;
import android.net.Uri;

/**
 * Created by max on 15.05.16.
 */
public class IntentApi {

    public static Intent uninstall(String packageName) {
        Uri packageURI = Uri.parse(String.format("package:%s", packageName));
        Intent uninstallIntent = new Intent(Intent.ACTION_DELETE, packageURI);
        return uninstallIntent;
    }

    public static Intent openGooglePlayPage(String packageName) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + packageName));
        return intent;
    }

}
