package io.wumf.wumf.adapter;

import android.content.Context;
import android.view.ViewGroup;

import io.realm.RealmBasedRecyclerViewAdapter;
import io.realm.RealmChangeListener;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.wumf.wumf.viewHolder.AnyRealmViewHolder;

/**
 * Created by max on 27.04.16.
 */
public abstract class AnyRealmAdapter<T extends RealmObject, VH extends AnyRealmViewHolder<T>> extends RealmBasedRecyclerViewAdapter<T, VH> {

    protected AnyRealmAdapter(Context context, RealmResults<T> realmResults) {
        this(context, realmResults, true, false, null);
    }

    private AnyRealmAdapter(Context context, final RealmResults<T> realmResults, boolean automaticUpdate, boolean animateResults, String animateExtraColumnName) {
        super(context, realmResults, automaticUpdate, animateResults, animateExtraColumnName);
        realmResults.addChangeListener(new RealmChangeListener() {
            @Override
            public void onChange() {
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public abstract VH onCreateRealmViewHolder(ViewGroup viewGroup, int i);

    @Override
    public void onBindRealmViewHolder(VH vh, int i) {
        vh.bind(realmResults.get(i));
    }

}
