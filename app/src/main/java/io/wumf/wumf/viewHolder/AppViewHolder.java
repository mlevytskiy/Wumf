package io.wumf.wumf.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.text.format.Time;
import android.view.View;
import android.widget.TextView;

import io.wumf.wumf.R;
import io.wumf.wumf.realmObject.App;
import io.wumf.wumf.view.IconImageView;
import io.wumf.wumf.view.LabelTextView;

/**
 * Created by max on 28.03.16.
 */
public class AppViewHolder extends RecyclerView.ViewHolder {

    private IconImageView icon;
    private LabelTextView label;
    private TextView isRemovedTextView;
    private TextView data;

    public AppViewHolder(View itemView) {
        super(itemView);
        label = (LabelTextView) itemView.findViewById(R.id.label);
        icon = (IconImageView) itemView.findViewById(R.id.icon);
        isRemovedTextView = (TextView) itemView.findViewById(R.id.is_removed);
        data = (TextView) itemView.findViewById(R.id.data);
    }

    public void bindApp(App app) {
        icon.setApp(app);
        label.setApp(app);
        isRemovedTextView.setText(app.isRemoved() ? "removed" : "existing" );
        Time time = new Time();
        time.set(app.getInstallDate());
        data.setText(time.format("%d.%m.%Y %H:%M:%S"));
    }

}
