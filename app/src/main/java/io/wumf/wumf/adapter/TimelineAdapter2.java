package io.wumf.wumf.adapter;

import android.content.Context;
import android.view.ViewGroup;

import io.realm.Realm;
import io.realm.RealmBasedRecyclerViewAdapter;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import io.wumf.wumf.realmObject.Event;
import io.wumf.wumf.viewHolder.TimelineViewHolder2;

/**
 * Created by max on 02.04.16.
 */
public class TimelineAdapter2 extends RealmBasedRecyclerViewAdapter<Event, TimelineViewHolder2> {

    public TimelineAdapter2(Context context) {
        this(context, Realm.getDefaultInstance().where(Event.class).findAll());
    }

    public TimelineAdapter2(Context context, RealmResults<Event> realmResults) {
        this(context, realmResults, true, false, null);
    }

    public TimelineAdapter2(Context context, RealmResults<Event> realmResults, boolean automaticUpdate, boolean animateResults, String animateExtraColumnName) {
        super(context, realmResults, automaticUpdate, animateResults, animateExtraColumnName);
        realmResults.addChangeListener(new RealmChangeListener() {
            @Override
            public void onChange() {
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public TimelineViewHolder2 onCreateRealmViewHolder(ViewGroup viewGroup, int i) {
        return new TimelineViewHolder2(viewGroup);
    }

    @Override
    public void onBindRealmViewHolder(TimelineViewHolder2 timelineViewHolder2, int i) {
        timelineViewHolder2.bind(realmResults.get(i));
    }
}
