package io.wumf.wumf.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import io.wumf.wumf.R;
import io.wumf.wumf.memory.App;
import io.wumf.wumf.view.IconImageView;
import io.wumf.wumf.view.LabelTextView;

/**
 * Created by max on 28.03.16.
 */
public class TimelineViewHolder extends RecyclerView.ViewHolder {

    private IconImageView icon;
    private LabelTextView label;

    public TimelineViewHolder(View itemView) {
        super(itemView);
        label = (LabelTextView) itemView.findViewById(R.id.label);
        icon = (IconImageView) itemView.findViewById(R.id.icon);
    }

    public void bindApp(App app) {
        icon.setApp(app);
        label.setApp(app);
    }

}
