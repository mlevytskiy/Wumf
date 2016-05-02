package io.wumf.wumf.viewHolder;

import android.view.ViewGroup;

import io.wumf.wumf.R;
import io.wumf.wumf.realmObject.App;
import io.wumf.wumf.view.IconImageView;
import io.wumf.wumf.view.LabelTextView;

/**
 * Created by max on 02.05.16.
 */
public class AppViewHolder extends AnyRealmViewHolder<App> {

    private IconImageView icon;
    private LabelTextView label;

    public AppViewHolder(ViewGroup parent) {
        super(parent, R.layout.viewholder_app);
        label = (LabelTextView) itemView.findViewById(R.id.label);
        icon = (IconImageView) itemView.findViewById(R.id.icon);
    }

    @Override
    public void bind(App item) {
        label.setApp(item);
        icon.setApp(item);
    }

}
