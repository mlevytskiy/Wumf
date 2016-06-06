package io.wumf.wumf.otto.event;

import java.util.List;

/**
 * Created by max on 31.05.16.
 */

public class LoadAppsNotFullEvent { //only packages

    public final List<String> packages;

    public LoadAppsNotFullEvent(List<String> packages) {
        this.packages = packages;
    }

}
