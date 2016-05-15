package io.wumf.wumf.viewHolder;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import io.wumf.wumf.R;
import io.wumf.wumf.otto.BusProvider;
import io.wumf.wumf.otto.event.OpenPageOnGooglePlayEvent;
import io.wumf.wumf.realmObject.App;
import io.wumf.wumf.view.IconImageView;
import io.wumf.wumf.view.LabelTextView;

/**
 * Created by max on 02.05.16.
 */
public class RemovedAppViewHolder extends AnyRealmViewHolder<App> {

    private IconImageView icon;
    private LabelTextView label;
    private ImageButton googlePlay;

    public RemovedAppViewHolder(ViewGroup parent) {
        super(parent, R.layout.viewholder_removed_app);
        label = (LabelTextView) itemView.findViewById(R.id.label);
        icon = (IconImageView) itemView.findViewById(R.id.icon);
        googlePlay = (ImageButton) itemView.findViewById(R.id.google_play);
        googlePlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String packageName = (String) v.getTag();
                BusProvider.getInstance().post(new OpenPageOnGooglePlayEvent(packageName));
            }
        });
    }

    @Override
    public void bind(App item) {
        label.setApp(item);
        icon.setApp(item);
        googlePlay.setTag(item.getPackageName());
    }

}
