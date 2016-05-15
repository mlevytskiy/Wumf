package io.wumf.wumf.otto.event;

/**
 * Created by max on 15.05.16.
 */
public class OpenPageOnGooglePlayEvent {

    public final String packageName;

    public OpenPageOnGooglePlayEvent(String packageName) {
        this.packageName = packageName;
    }

}
