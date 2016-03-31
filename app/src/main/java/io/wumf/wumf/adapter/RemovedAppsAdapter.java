package io.wumf.wumf.adapter;

import io.wumf.wumf.adapter.common.AppsAdapter;
import io.wumf.wumf.util.getApps.GetRemovedApps;

/**
 * Created by max on 28.03.16.
 */
public class RemovedAppsAdapter extends AppsAdapter {

    public RemovedAppsAdapter() {
        super(new GetRemovedApps());
    }
}
