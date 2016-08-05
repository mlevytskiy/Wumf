package io.wumf.wumf.view;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.style.ReplacementSpan;

/**
 * Created by maks on 8/5/16.
 * email: m.levytskiy@gmail.com
 *
 * SpannableString spannableString = SpannableString.valueOf(getText());
 * startX=getLayout().getPrimaryHorizontal(spannableString.getSpanStart(span));
 *
 * width = textView.getPaint().measureText(text);
 */
public class RoundedBackgroundSpan extends ReplacementSpan {

    private float startX = 0f;
    private float endX = 0f;

    @Override
    public int getSize(Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
        return 0;
    }

    @Override
    public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint) {
        RectF rect = new RectF(startX, top, endX, bottom);
        paint.setColor(Color.CYAN);
        canvas.drawRoundRect(rect, 20, 20, paint);
    }

}
