package io.wumf.wumf.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import io.wumf.wumf.R;

/**
 * Created by max on 06.08.16.
 */
public class ClickableTextView extends com.github.omadahealth.typefaceview.TypefaceTextView {

    private RoundedBackgroundSpan roundedBackgroundSpan;
    private boolean isPress = false;

    public ClickableTextView(Context context) {
        super(context);
        init();
    }

    public ClickableTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ClickableTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setTextSize(20);
        setTextColor(Color.BLACK);
        setTextIsSelectable(false);
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "test", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void setCustomText(String str) {
        str = " " + str + " ";

        SpannableStringBuilder stringBuilder = new SpannableStringBuilder();

        roundedBackgroundSpan = new RoundedBackgroundSpan(Color.WHITE,
                getResources().getDimension(R.dimen.round_corner));
        float countrySize = getPaint().measureText(str);
        float spaceSize = getPaint().measureText(" ");
        roundedBackgroundSpan.addRect(spaceSize/2, countrySize + spaceSize/2 + spaceSize);

        stringBuilder.append(" ");
        stringBuilder.setSpan(roundedBackgroundSpan, 0, 1, SpannableStringBuilder.SPAN_EXCLUSIVE_EXCLUSIVE);

        stringBuilder.append(" ");
        stringBuilder.append(str);
        stringBuilder.append(" ");

        setText(stringBuilder);
        setMovementMethod(null);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        boolean isPressTmp;
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                isPressTmp = true;
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                isPressTmp = false;
                break;
            default:
                isPressTmp = isPress;
                break;
        }
        if (isPress != isPressTmp) {
            isPress = isPressTmp;
            changeRoundBackgroundSpanColor();
        }
        return super.onTouchEvent(event);
    }

    private void changeRoundBackgroundSpanColor() {
        roundedBackgroundSpan.setColor(isPress ? Color.BLACK : Color.WHITE);
        setTextColor(isPress ? Color.WHITE : Color.BLACK);
        invalidate();
    }

}
