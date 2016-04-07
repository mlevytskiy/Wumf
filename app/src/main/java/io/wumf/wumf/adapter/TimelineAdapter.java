package io.wumf.wumf.adapter;

import android.content.Context;
import android.view.ViewGroup;

import io.realm.Realm;
import io.realm.RealmBasedRecyclerViewAdapter;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import io.wumf.wumf.realmObject.Event;
import io.wumf.wumf.viewHolder.TimelineViewHolder;

/**
 * Created by max on 02.04.16.
 */
public class TimelineAdapter extends RealmBasedRecyclerViewAdapter<Event, TimelineViewHolder> {

    private static final int VIEW_TYPE_NORMAL = 0;
    private static final int VIEW_TYPE_MULTIPLE_CARDS = 1;
    private static final int VIEW_TYPE_EMPTY = 3;

    public TimelineAdapter(Context context) {
        this(context, Realm.getDefaultInstance().where(Event.class).findAll());
    }

    public TimelineAdapter(Context context, RealmResults<Event> realmResults) {
        this(context, realmResults, true, false, null);
    }

    public TimelineAdapter(Context context, RealmResults<Event> realmResults, boolean automaticUpdate, boolean animateResults, String animateExtraColumnName) {
        super(context, realmResults, automaticUpdate, animateResults, animateExtraColumnName);
        realmResults.addChangeListener(new RealmChangeListener() {
            @Override
            public void onChange() {
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public TimelineViewHolder onCreateRealmViewHolder(ViewGroup viewGroup, int i) {
        return new TimelineViewHolder(viewGroup);
    }

    @Override
    public void onBindRealmViewHolder(TimelineViewHolder timelineViewHolder, int i) {
        timelineViewHolder.bind(realmResults.get(i));
    }

    @Override
    public int getItemViewType(int position) {
        return position % 2 * 2;
    }

}
