package io.wumf.wumf.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import io.wumf.wumf.R;
import io.wumf.wumf.memory.App;

/**
 * Created by max on 28.03.16.
 */
public class TimelineViewHolder extends RecyclerView.ViewHolder {

    private TextView textView;

    public TimelineViewHolder(View itemView) {
        super(itemView);
        textView = (TextView) itemView.findViewById(R.id.text_view);
    }

    public void bindApp(App app) {
        textView.setText(app.getPackageName());
    }

}
