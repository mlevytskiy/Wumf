package io.wumf.wumf.adapter.common;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import io.wumf.wumf.R;
import io.wumf.wumf.memory.App;
import io.wumf.wumf.util.getApps.Callback;
import io.wumf.wumf.util.getApps.GetAppsStrategy;
import io.wumf.wumf.viewHolder.AppViewHolder;

/**
 * Created by max on 31.03.16.
 */
public abstract class AppsAdapter extends RecyclerView.Adapter<AppViewHolder> {

    private List<App> apps;

    public AppsAdapter(GetAppsStrategy getAppsStrategy) {
        apps = new ArrayList<>();
        getAppsStrategy.getAsync(new Callback<List<App>>() {
            @Override
            public void receive(List<App> list) {
                apps.addAll(list);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public AppViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AppViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_app, parent, false));
    }

    @Override
    public void onBindViewHolder(AppViewHolder holder, int position) {
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
