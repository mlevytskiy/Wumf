package io.wumf.wumf.adapter;

import android.content.Context;
import android.view.ViewGroup;

import io.realm.Realm;
import io.wumf.wumf.realmObject.RemoteApp;
import io.wumf.wumf.viewHolder.RemoteAppViewHolder;

/**
 * Created by max on 22.07.16.
 */
public class RemoteAppsAdapter extends  AnyRealmAdapter<RemoteApp, RemoteAppViewHolder> {

    public RemoteAppsAdapter(Context context, String placeId) {
        super(context, Realm.getDefaultInstance().where(RemoteApp.class).equalTo("regionId", placeId).findAll());
    }

    @Override
    public RemoteAppViewHolder onCreateRealmViewHolder(ViewGroup viewGroup, int i) {
        return new RemoteAppViewHolder(viewGroup);
    }

}
