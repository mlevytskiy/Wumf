package io.wumf.wumf.util;

import android.content.Context;

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
        return null;
    }

    public App loadAppFromSystem(String packageName) {
        return null;
    }

}
