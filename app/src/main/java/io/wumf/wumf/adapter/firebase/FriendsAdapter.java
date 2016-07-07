package io.wumf.wumf.adapter.firebase;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.wumf.wumf.R;
import io.wumf.wumf.contacts.Contact;
import io.wumf.wumf.pojo.App;

/**
 * Created by max on 05.07.16.
 */
public class FriendsAdapter extends BaseAdapter {

    private List<Contact> friends = new ArrayList<>();

    public FriendsAdapter(Map<Contact, List<App>> map) {
        for (Map.Entry<Contact, List<App>> entry : map.entrySet()) {
            if ( (entry.getValue() != null) && (!entry.getValue().isEmpty()) ) {
                friends.add(entry.getKey());
            }
        }
    }

    @Override
    public int getCount() {
        return friends.size();
    }

    @Override
    public Contact getItem(int position) {
        return friends.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(parent.getContext(), R.layout.item_friend, null);
            viewHolder = new ViewHolder();
            viewHolder.textView = (TextView) convertView.findViewById(R.id.friend_phone);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.textView.setText(friends.get(position).getPhone());

        return convertView;
    }

    private static class ViewHolder {
        public TextView textView;
    }

}
