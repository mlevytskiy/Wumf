package io.wumf.wumf.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import io.wumf.wumf.R;

/**
 * Created by max on 03.08.16.
 */
public class CustomTopBar extends LinearLayout {

    public CustomTopBar(Context context) {
        super(context);
        init(context);
    }

    public CustomTopBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CustomTopBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        setBackgroundColor(Color.WHITE);//Color.parseColor("#fffffcee"));
        setOrientation(HORIZONTAL);
        inflate(context, R.layout.view_custom_top_bar, this);
    }

}
