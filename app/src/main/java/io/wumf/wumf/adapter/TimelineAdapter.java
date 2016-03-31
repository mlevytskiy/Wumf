package io.wumf.wumf.adapter;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.wumf.wumf.R;
import io.wumf.wumf.realmObject.App;
import io.wumf.wumf.util.AppUtils;
import io.wumf.wumf.util.getApps.GetAppsStrategy;
import io.wumf.wumf.viewHolder.TimelineViewHolder;

/**
 * Created by max on 28.03.16.
 */
public class TimelineAdapter extends RecyclerView.Adapter<TimelineViewHolder> {

    private List<App> apps;

    public TimelineAdapter(List<App> apps) {
        this.apps = apps;
        this.setHasStableIds(true);
    }

    public TimelineAdapter(final Context context) {
        apps = new ArrayList<>();
        this.setHasStableIds(true);
        new AsyncTask<Void, Void, List<App>>() {

            private List<App> appsFromDatabase;
            private GetAppsStrategy getAppsStrategy;

            protected void onPreExecute() {
                appsFromDatabase = getAppsStrategy.get();
            }

            @Override
            protected List<App> doInBackground(Void... params) {
                if (appsFromDatabase.isEmpty()) {
                    return new AppUtils(context).loadAllAppsFromSystem();
                } else {
                    return appsFromDatabase;
                }
            }

            protected void onPostExecute(List<App> apps) {
                if (appsFromDatabase.isEmpty()) {
                    Realm.getDefaultInstance().beginTransaction();
                    Realm.getDefaultInstance().copyToRealmOrUpdate(apps);
                    Realm.getDefaultInstance().commitTransaction();
                } else {
                    //do nothing
                }
                TimelineAdapter.this.apps.addAll(apps);
                notifyDataSetChanged();
            }
        }.execute();
    }

    @Override
    public TimelineViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TimelineViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_timeline, parent, false));
    }

    @Override
    public void onBindViewHolder(TimelineViewHolder holder, int position) {
        holder.bindApp(apps.get(position));
    }

    @Override
    public int getItemCount() {
        return apps.size();
    }

    public long getItemId(int position) {
        return apps.get(position).hashCode();
    }

}
