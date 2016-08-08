package io.wumf.wumf.view;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.util.AttributeSet;

import io.wumf.wumf.R;

/**
 * Created by maks on 8/8/16.
 * email: m.levytskiy@gmail.com
 */
public class UsersCountTextView extends com.github.omadahealth.typefaceview.TypefaceTextView {

    public UsersCountTextView(Context context) {
        super(context);
    }

    public UsersCountTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public UsersCountTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setCount(int count) {
        SpannableStringBuilder stringBuilder = new SpannableStringBuilder();
        stringBuilder.append(" ");
        Drawable dr = getResources().getDrawable(R.drawable.ic_person);
        dr.setBounds(0, 0, getLineHeight(), getLineHeight());
        dr.setColorFilter(getCurrentTextColor(), PorterDuff.Mode.MULTIPLY);
        ImageSpan imageSpan = new ImageSpan(dr);
        stringBuilder.setSpan( imageSpan, 0, 1, SpannableStringBuilder.SPAN_INCLUSIVE_EXCLUSIVE );
        stringBuilder.append(String.valueOf(count));
        setText(stringBuilder);
    }

}
