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
import io.wumf.wumf.pojo.BaseAppInfo;
import io.wumf.wumf.realmObject.App;
import io.wumf.wumf.realmObject.Event;
import io.wumf.wumf.realmObject.EventType;

/**
 * Created by max on 25.03.16.
 */
public class AppUtils {

    private PackageManager pm;
    private SaveIconUtils saveIconUtils;
    private FileGenerator fileGenerator;

    public AppUtils(Context context) {
        pm = context.getPackageManager();
        saveIconUtils = new SaveIconUtils(context);
        fileGenerator = new FileGenerator(context);
    }

    public List<App> loadAllAppsFromSystem() {
        List<ResolveInfo> resolveInfos = getResolveInfos();
        List<App> apps = resolveInfoToApp(resolveInfos);
        sortByInstallDate(apps);
        setInFirstGroupFlag(apps);
        Collections.reverse(apps);
        return apps;
    }

    public BaseAppInfo loadBaseAppInfoFromSystem(String packageName) {
        return resolveInfoToBaseAppInfo(loadResolveInfo(packageName));
    }

    public App loadAppFromSystem(String packageName) {
        return resolveInfoToApp(loadResolveInfo(packageName));
    }

    private ResolveInfo loadResolveInfo(String packageName) {
        Intent intent = new Intent();
        intent.setPackage(packageName);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        return pm.resolveActivity(intent, 0);
    }

    private BaseAppInfo resolveInfoToBaseAppInfo(ResolveInfo resolveInfo) {
        BaseAppInfo baseAppInfo = new BaseAppInfo();
        baseAppInfo.setLauncherActivity(resolveInfo.activityInfo.name);
        baseAppInfo.setInstallDate(getInstallDate(resolveInfo.activityInfo.packageName));
        baseAppInfo.setPackageName(resolveInfo.activityInfo.packageName);
        return baseAppInfo;
    }

    private App resolveInfoToApp(ResolveInfo resolveInfo) {
        App app = new App();
        app.setInstallDate(getInstallDate(resolveInfo.activityInfo.packageName));
        app.setLabel(((String) resolveInfo.loadLabel(pm)));
        app.setPackageName(resolveInfo.activityInfo.packageName);
        app.setLauncherActivity(resolveInfo.activityInfo.name);
        app.setRemoved(false);
        app.setIconPath(loadAndSaveIconInFile(pm, resolveInfo));
        addFirstAddedEvent(app);
        return app;
    }

    private List<App> resolveInfoToApp(List<ResolveInfo> list) {
        List<App> result = new ArrayList<>();
        for (ResolveInfo resolveInfo : list) {
            result.add(resolveInfoToApp(resolveInfo));
        }
        return result;
    }

    private List<ResolveInfo> getResolveInfos() {
        final Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        final List<android.content.pm.ResolveInfo> appList = pm.queryIntentActivities(mainIntent, PackageManager.GET_META_DATA);
        return appList;
    }

    private long getInstallDate(String packageName) {
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
