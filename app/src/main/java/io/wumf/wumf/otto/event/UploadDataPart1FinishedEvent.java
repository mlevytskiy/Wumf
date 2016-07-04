package io.wumf.wumf.otto.event;

import java.util.List;

import io.wumf.wumf.firebase.pojo.App;

/**
 * Created by max on 04.07.16.
 */
public class UploadDataPart1FinishedEvent {

    public final List<App> apps;
    public final boolean success;

    public UploadDataPart1FinishedEvent(List<App> apps, boolean success) {
        this.apps = apps;
        this.success = success;
    }

}
