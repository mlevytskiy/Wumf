package io.wumf.wumf.adapter;

import android.content.Context;
import android.view.ViewGroup;

import io.realm.Realm;
import io.realm.Sort;
import io.wumf.wumf.realmObject.Event;
import io.wumf.wumf.viewHolder.TimelineViewHolder;

/**
 * Created by max on 02.04.16.
 */
public class TimelineAdapter extends AnyRealmAdapter<Event, TimelineViewHolder> {

    public TimelineAdapter(Context context) {
        super(context, Realm.getDefaultInstance().where(Event.class).findAllSorted("time", Sort.DESCENDING));
    }

    @Override
    public TimelineViewHolder onCreateRealmViewHolder(ViewGroup viewGroup, int i) {
        return new TimelineViewHolder(viewGroup);
    }

}
