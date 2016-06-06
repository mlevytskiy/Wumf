package io.wumf.wumf.otto.event;

import java.util.List;

import io.wumf.wumf.pojo.App;

/**
 * Created by max on 31.05.16.
 */

public class LoadAppsFinishEvent {

    public final List<App> apps;

    public LoadAppsFinishEvent(List<App> apps) {
        this.apps = apps;
    }

}
