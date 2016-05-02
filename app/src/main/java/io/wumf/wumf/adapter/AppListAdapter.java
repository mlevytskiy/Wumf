package io.wumf.wumf.adapter;

import android.content.Context;
import android.view.ViewGroup;

import io.realm.Realm;
import io.realm.Sort;
import io.wumf.wumf.realmObject.App;
import io.wumf.wumf.viewHolder.AppViewHolder;

/**
 * Created by max on 02.05.16.
 */
public class AppListAdapter extends AnyRealmAdapter<App, AppViewHolder> {

    public AppListAdapter(Context context) {
        super(context, Realm.getDefaultInstance().where(App.class).findAllSortedAsync("installDate", Sort.DESCENDING));
    }

    @Override
    public AppViewHolder onCreateRealmViewHolder(ViewGroup viewGroup, int i) {
        return new AppViewHolder(viewGroup);
    }

}
