package io.wumf.wumf.viewHolder;

import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import io.wumf.wumf.R;
import io.wumf.wumf.realmObject.RemoteApp;

/**
 * Created by max on 22.07.16.
 */
public class RemoteAppViewHolder extends AnyRealmViewHolder<RemoteApp> {

    private ImageView icon;
    private TextView label;

    public RemoteAppViewHolder(ViewGroup parent) {
        super(parent, R.layout.viewholder_app);
        icon = (ImageView) itemView.findViewById(R.id.icon);
        label = (TextView) itemView.findViewById(R.id.label);
    }

    @Override
    public void bind(RemoteApp item) {
        Picasso.with(itemView.getContext()).load(item.getIcon()).into(icon);
        label.setText(item.getName());
    }

}
