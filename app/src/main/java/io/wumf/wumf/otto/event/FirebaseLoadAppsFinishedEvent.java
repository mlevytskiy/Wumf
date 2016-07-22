package io.wumf.wumf.otto.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.wumf.wumf.firebase.pojo.App;

/**
 * Created by max on 22.07.16.
 */
public class FirebaseLoadAppsFinishedEvent {

    public List<App> apps = new ArrayList<>();

    public FirebaseLoadAppsFinishedEvent(Map<String, List<App>> appsMap) {

        Map<String, App> packagesMap = new HashMap<>();

        for (Map.Entry<String, List<App>> entry : appsMap.entrySet()) {
            for (App app : entry.getValue()) {
                packagesMap.put(app.packageName, app);
            }
        }

        for (Map.Entry<String, App> entry : packagesMap.entrySet()) {
            apps.add(entry.getValue());
        }

    }
}
