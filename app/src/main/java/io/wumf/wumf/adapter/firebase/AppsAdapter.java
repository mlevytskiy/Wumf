package io.wumf.wumf.adapter.firebase;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import io.wumf.wumf.R;
import io.wumf.wumf.pojo.App;

/**
 * Created by max on 05.07.16.
 */
public class AppsAdapter extends BaseAdapter {

    private List<App> apps;

    public AppsAdapter(List<App> apps) {
        this.apps = apps;
    }

    @Override
    public int getCount() {
        return apps.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(parent.getContext(), R.layout.item_app, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.fill(apps.get(position));
        return convertView;
    }

    private static class ViewHolder {

        public ImageView icon;
        public TextView label;

        public ViewHolder(View item) {
            icon = (ImageView) item.findViewById(R.id.icon);
            label = (TextView) item.findViewById(R.id.label);
        }

        public void fill(App app) {
            Picasso.with(icon.getContext()).load(app.getRemoteIconPath()).into(icon);
            label.setText(app.name);
        }

    }

}
