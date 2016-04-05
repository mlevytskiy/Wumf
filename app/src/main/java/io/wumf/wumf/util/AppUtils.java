package io.wumf.wumf.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.realm.RealmList;
import io.wumf.wumf.realmObject.App;
import io.wumf.wumf.realmObject.Event;
import io.wumf.wumf.realmObject.EventType;

/**
 * Created by max on 25.03.16.
 */
public class AppUtils {

    private Context context;
    private SaveIconUtils saveIconUtils;
    private FileGenerator fileGenerator;

    public AppUtils(Context context) {
        this.context = context;
        saveIconUtils = new SaveIconUtils(context);
        fileGenerator = new FileGenerator(context);
    }

    public List<App> loadAllAppsFromSystem() {
        PackageManager pm = context.getPackageManager();
        List<ResolveInfo> resolveInfos = getResolveInfos(pm);
        List<App> apps = resolveInfoToApp(pm, resolveInfos);
        sortByInstallDate(apps);
        setInFirstGroupFlag(apps);
        Collections.reverse(apps);
        return apps;
    }

    public App loadAppFromSystem(String packageName) {
        PackageManager pm = context.getPackageManager();
        Intent intent = new Intent();
        intent.setPackage(packageName);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        ResolveInfo resolveInfo = pm.resolveActivity(intent, 0);
        return resolveInfoToApp(pm, resolveInfo);
    }

    private App resolveInfoToApp(PackageManager pm, ResolveInfo resolveInfo) {
        App app = new App();
        app.setInstallDate(getInstallDate(pm, resolveInfo.activityInfo.packageName));
        app.setLabel(((String) resolveInfo.loadLabel(pm)));
        app.setPackageName(resolveInfo.activityInfo.packageName);
        app.setRemoved(false);
        app.setIconPath(loadAndSaveIconInFile(pm, resolveInfo));
        addFirstAddedEvent(app);
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

    private String loadAndSaveIconInFile(PackageManager pm, ResolveInfo resolveInfo) {
        Drawable drawable = resolveInfo.loadIcon(pm);
        File file = fileGenerator.generate(resolveInfo);
        saveIconUtils.saveInFile(file, drawable);
        return file.getAbsolutePath();
    }

    private Event getFirstAddedEvent(App app) {
        Event event = new Event();
        event.setTime(app.getInstallDate());
        event.setEventType(EventType.toInt(EventType.ADD));
        event.setApp(app);
        return event;
    }

    private void addFirstAddedEvent(App app) {
        Event event = getFirstAddedEvent(app);
        app.setEvents(new RealmList<Event>(event));
    }

    private void sortByInstallDate(List<App> apps) {
        Collections.sort(apps, new Comparator<App>() {
            @Override
            public int compare(App lhs, App rhs) {
                long lInstallDate = lhs.getInstallDate();
                long rInstallDate = rhs.getInstallDate();
                if (lInstallDate > rInstallDate) {
                    return 1;
                } else if (lInstallDate == rInstallDate) {
                    return 0;
                } else {
                    return -1;
                }
            }
        });
    }

    private void setInFirstGroupFlag(List<App> apps) {
        for (int i = 1; i < apps.size(); i++) {
            App app = apps.get(i);
            App previousApp = apps.get(i - 1);
            if (Math.abs(previousApp.getInstallDate() - app.getInstallDate()) < TimeUnit.MINUTES.toMillis(1)) {
                app.setInFirstGroup(true);
                previousApp.setInFirstGroup(true);
            } else {
                break;
            }
        }
    }

}
