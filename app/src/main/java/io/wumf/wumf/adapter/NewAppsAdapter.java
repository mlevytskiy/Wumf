package io.wumf.wumf.adapter;

import io.wumf.wumf.adapter.common.AppsAdapter;
import io.wumf.wumf.util.getApps.GetNewApps;

/**
 * Created by max on 28.03.16.
 */
public class NewAppsAdapter extends AppsAdapter {

    public NewAppsAdapter(long lastDateFilter) {
        super(new GetNewApps(lastDateFilter));
    }

}
