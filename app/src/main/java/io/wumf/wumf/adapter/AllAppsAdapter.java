package io.wumf.wumf.adapter;

import io.wumf.wumf.adapter.common.AppsAdapter;
import io.wumf.wumf.util.getApps.GetAllApps;

/**
 * Created by max on 28.03.16.
 */
public class AllAppsAdapter extends AppsAdapter {

    public AllAppsAdapter() {
        super(new GetAllApps());
    }

}
