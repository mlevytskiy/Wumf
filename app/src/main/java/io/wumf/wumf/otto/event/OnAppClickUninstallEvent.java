package io.wumf.wumf.otto.event;

/**
 * Created by max on 15.05.16.
 */
public class OnAppClickUninstallEvent {

    public final String packageName;

    public OnAppClickUninstallEvent(String packageName) {
        this.packageName = packageName;
    }

}
