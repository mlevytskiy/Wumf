package io.wumf.wumf.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

import java.util.ArrayList;
import java.util.List;

import io.wumf.wumf.memory.App;

/**
 * Created by max on 25.03.16.
 */
public class AppUtils {

    private Context context;

    public AppUtils(Context context) {
        this.context = context;
    }

    public List<App> loadAllAppsFromSystem() {
        PackageManager pm = context.getPackageManager();
        List<ResolveInfo> resolveInfos = getResolveInfos(pm);
        return resolveInfoToApp(pm, resolveInfos);
    }

    public App loadAppFromSystem(String packageName) {
        return null;
    }

    private App resolveInfoToApp(PackageManager pm, ResolveInfo resolveInfo) {
        App app = new App();
        app.setInstallDate(getInstallDate(pm, resolveInfo.activityInfo.packageName));
        app.setPackageName(resolveInfo.activityInfo.packageName);
        app.setRemoved(false);
        return app;
    }

    private List<App> resolveInfoToApp(PackageManager pm, List<ResolveInfo> list) {
        List<App> result = new ArrayList<>();
        for (ResolveInfo resolveInfo : list) {
            result.add(resolveInfoToApp(pm, resolveInfo));
        }
        return result;
    }

    private List<ResolveInfo> getResolveInfos(PackageManager pm) {
        final Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        final List<android.content.pm.ResolveInfo> appList = pm.queryIntentActivities(mainIntent, PackageManager.GET_META_DATA);
        return appList;
    }

    private long getInstallDate(PackageManager pm, String packageName) {
        try {
            PackageInfo packageInfo = pm.getPackageInfo(packageName, 0);
            return packageInfo.firstInstallTime;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

}
