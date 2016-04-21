package io.wumf.wumf.viewHolder;

import android.support.v7.widget.CardView;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import io.realm.RealmViewHolder;
import io.wumf.wumf.R;
import io.wumf.wumf.otto.BusProvider;
import io.wumf.wumf.otto.event.OnAppItemClickEvent;
import io.wumf.wumf.realmObject.App;
import io.wumf.wumf.realmObject.Event;
import io.wumf.wumf.realmObject.EventType;
import io.wumf.wumf.view.IconImageView;
import io.wumf.wumf.view.LabelTextView;

/**
 * Created by max on 02.04.16.
 */
public class TimelineViewHolder extends RealmViewHolder {

    private IconImageView icon;
    private LabelTextView label;
    private TextView isRemovedTextView;
    private TextView data;
    private TextView action;
    private CardView cardView;
    private ImageView eventTypeImageView;

    public TimelineViewHolder(ViewGroup parent) {
        super(LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_timeline, parent, false));
        label = (LabelTextView) itemView.findViewById(R.id.label);
        icon = (IconImageView) itemView.findViewById(R.id.icon);
        isRemovedTextView = (TextView) itemView.findViewById(R.id.is_removed);
        data = (TextView) itemView.findViewById(R.id.data);
        action = (TextView) itemView.findViewById(R.id.action);
        eventTypeImageView = (ImageView) itemView.findViewById(R.id.app_event_type_image_view);
        cardView = (CardView) itemView.findViewById(R.id.card_view);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String appId = (String) v.getTag();
                BusProvider.getInstance().post(new OnAppItemClickEvent(appId));
            }
        });
    }

    public void bind(Event event) {
        EventType eventType = EventType.fromInt(event.getEventType());
        action.setText(eventType.toString());

        if (eventType == EventType.ADD) {
            eventTypeImageView.setImageResource(R.drawable.ic_add);
        } else if (eventType == EventType.REMOVE) {
            eventTypeImageView.setImageResource(R.drawable.ic_remove);
        }

        bind(event.getApp());
    }

    private void bind(App app) {
        icon.setApp(app);
        label.setApp(app);
        isRemovedTextView.setText(app.isInFirstGroup() ? "first" : "-" );
        Time time = new Time();
        time.set(app.getInstallDate());
        data.setText(time.format("%d.%m.%Y %H:%M:%S"));
        cardView.setTag(app.getLauncherActivity());
    }

}
