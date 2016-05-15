package io.wumf.wumf.adapter;

import android.content.Context;
import android.view.ViewGroup;

import io.realm.Realm;
import io.realm.Sort;
import io.wumf.wumf.realmObject.App;
import io.wumf.wumf.viewHolder.RemovedAppViewHolder;

/**
 * Created by max on 02.05.16.
 */
public class RemovedAppListAdapter extends AnyRealmAdapter<App, RemovedAppViewHolder> {

    public RemovedAppListAdapter(Context context) {
        super(context, Realm.getDefaultInstance().where(App.class)
                .equalTo("isRemoved", true).findAllSorted("installDate", Sort.DESCENDING));
    }

    @Override
    public RemovedAppViewHolder onCreateRealmViewHolder(ViewGroup viewGroup, int i) {
        return new RemovedAppViewHolder(viewGroup);
    }

}
