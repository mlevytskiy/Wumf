package io.wumf.wumf.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.funakoshi.resolveInfoAsyncLoader.IconImageView;
import com.funakoshi.resolveInfoAsyncLoader.LabelTextView;

import io.wumf.wumf.R;
import io.wumf.wumf.memory.App;

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
        icon.setPackageName(app.getPackageName());
        label.setPackageName(app.getPackageName());
    }

}
