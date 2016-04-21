package io.wumf.wumf.view;

import android.content.Context;
import android.util.AttributeSet;

import com.pkmmte.view.CircularImageView;

import io.wumf.wumf.R;
import io.wumf.wumf.realmObject.EventType;

/**
 * Created by max on 10.04.16.
 */
public class AppEventTypeImageView extends CircularImageView {

    public AppEventTypeImageView(Context context) {
        super(context);
        init();
    }

    public AppEventTypeImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AppEventTypeImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void setState(EventType eventType) {
        if (eventType == EventType.ADD) {
            setImageResource(R.drawable.ic_add);
        } else if (eventType == EventType.REMOVE) {
            setImageResource(R.drawable.ic_remove);
        }
    }

    private void init() {
//        setBorderColor(Color.RED);
//        setBorderWidth(3);
//        setBackgroundColor(Color.YELLOW);
    }


}
