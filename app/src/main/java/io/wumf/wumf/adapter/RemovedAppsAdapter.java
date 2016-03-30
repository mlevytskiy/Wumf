package io.wumf.wumf.adapter;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import io.wumf.wumf.R;
import io.wumf.wumf.memory.App;
import io.wumf.wumf.memory.AppsManager;
import io.wumf.wumf.util.AppUtils;

/**
 * Created by max on 28.03.16.
 */
public class RemovedAppsAdapter extends RecyclerView.Adapter<TimelineViewHolder> {

    private List<App> apps;

    public RemovedAppsAdapter(List<App> apps) {
        this.apps = apps;
        this.setHasStableIds(true);
    }

    public RemovedAppsAdapter(final Context context) {
        apps = new ArrayList<>();
        this.setHasStableIds(true);
        new AsyncTask<Void, Void, List<App>>() {

            private List<App> appsFromDatabase;
            private AppsManager appsManager;

            protected void onPreExecute() {
                appsManager = new AppsManager();
                RealmResults<App> realmResults = Realm.getDefaultInstance().where(App.class).equalTo("isRemoved", true).findAll();
                appsFromDatabase = realmResults.subList(0, realmResults.size());
            }

            @Override
            protected List<App> doInBackground(Void... params) {
                if (appsFromDatabase.isEmpty()) {
                    List<App> apps = new AppUtils(context).loadAllAppsFromSystem();
                    return apps;
                } else {
                    return appsFromDatabase;
                }
            }

            protected void onPostExecute(List<App> apps) {
                if (appsFromDatabase.isEmpty()) {
                    appsManager.saveAll(apps);
                } else {
                    //do nothing
                }
                RemovedAppsAdapter.this.apps.addAll(apps);
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
