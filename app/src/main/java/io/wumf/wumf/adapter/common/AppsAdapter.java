package io.wumf.wumf.adapter.common;

import android.content.Context;
import android.view.ViewGroup;

import io.realm.RealmBasedRecyclerViewAdapter;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import io.wumf.wumf.realmObject.App;
import io.wumf.wumf.viewHolder.AppViewHolder;

/**
 * Created by max on 04.04.16.
 */
public abstract class AppsAdapter extends RealmBasedRecyclerViewAdapter<App, AppViewHolder> {

    public AppsAdapter(Context context, RealmResults<App> realmResults) {
        this(context, realmResults, true, false, null);
    }

    public AppsAdapter(Context context, RealmResults<App> realmResults, boolean automaticUpdate, boolean animateResults, String animateExtraColumnName) {
        super(context, realmResults, automaticUpdate, animateResults, animateExtraColumnName);
        realmResults.addChangeListener(new RealmChangeListener() {
            @Override
            public void onChange() {
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public AppViewHolder onCreateRealmViewHolder(ViewGroup viewGroup, int i) {
        return new AppViewHolder(viewGroup);
    }

    @Override
    public void onBindRealmViewHolder(AppViewHolder appViewHolder, int i) {
        appViewHolder.bind(realmResults.get(i));
    }
}
