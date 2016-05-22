package io.wumf.wumf.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    public List<App> loadAllAppsFromSystem(HashMap<App, ResolveInfo> map) {
        List<ResolveInfo> resolveInfos = getResolveInfos();
        List<App> apps = resolveInfoToApp(resolveInfos);
        for (int i = 0; i < apps.size(); i++) {
            map.put(apps.get(i), resolveInfos.get(i));
        }
        sortByInstallDate(apps);
        setInFirstGroupFlag(apps);
        Collections.reverse(apps);
        addLabelAndIcon(apps, map, 10);
        return apps;
    }

    public List<App> loadNext(List<App> apps, HashMap<App, ResolveInfo> map, int startAppIndex, int step) {
        List<App> result = new ArrayList<>();
        int end = Math.min(startAppIndex + step, apps.size());
        for (int i = startAppIndex; i < end; i++) {
            result.add(apps.get(i));
        }
        addLabelAndIcon(result, map, step+1);
        return result;
    }

    private void addLabelAndIcon(List<App> apps, Map<App, ResolveInfo> map, int firstElementsCount) {
        int index = 0;
        for (App app : apps) {
            index++;
            app.setLabel(((String) map.get(app).loadLabel(pm)));
            app.setIconPath(loadAndSaveIconInFile(pm, map.get(app)));
            if (index == firstElementsCount) {
                return;
            }
        }
    }

    public BaseAppInfo loadBaseAppInfoFromSystem(String packageName) {
        return resolveInfoToBaseAppInfo(loadResolveInfo(packageName));
    }

    public App loadAppFromSystem(String packageName) {
        ResolveInfo resolveInfo = loadResolveInfo(packageName);
        App app = resolveInfoToApp(resolveInfo);
        loadInfoFromAppInfo(app);
        List<App> apps = new ArrayList<>();
        apps.add(app);
        Map<App, ResolveInfo> map = new HashMap<App, ResolveInfo>();
        map.put(app, resolveInfo);
        addLabelAndIcon(apps, map, 1);
        return app;
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
        app.setPackageName(resolveInfo.activityInfo.packageName);
        app.setLauncherActivity(resolveInfo.activityInfo.name);
        app.setRemoved(false);
        addFirstAddedEvent(app);
        return app;
    }

    private void loadInfoFromAppInfo(App app) {
        try {
            ApplicationInfo applicationInfo = pm.getApplicationInfo(app.getPackageName(), 0);
            loadInfoFromApplicationInfo(applicationInfo, app);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    private App loadInfoFromApplicationInfo(ApplicationInfo applicationInfo, App app) {
        app.setApkFilePath(applicationInfo.publicSourceDir);
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
        event.setTimeAndAppPrimaryKey(app.getLauncherActivity() + app.getInstallDate());
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
