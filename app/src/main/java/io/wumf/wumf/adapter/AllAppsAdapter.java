package io.wumf.wumf.adapter;

import android.content.Context;

import io.realm.Realm;
import io.wumf.wumf.adapter.common.AppsAdapter;
import io.wumf.wumf.realmObject.App;

/**
 * Created by max on 04.04.16.
 */
public class AllAppsAdapter extends AppsAdapter {

    public AllAppsAdapter(Context context) {
        super(context, Realm.getDefaultInstance().where(App.class).findAll());
    }

}
