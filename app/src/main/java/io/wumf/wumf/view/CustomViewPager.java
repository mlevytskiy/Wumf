package io.wumf.wumf.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

import io.wumf.wumf.util.MotionEventAnalyzer;

/**
 * Created by max on 15.05.16.
 */
public class CustomViewPager extends ViewPager {

    private boolean paginationToLeft = true;
    private boolean paginationToRight = false;
    private MotionEventAnalyzer motionEventAnalyzer;

    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        motionEventAnalyzer = new MotionEventAnalyzer();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        motionEventAnalyzer.setMotionEvent(event);
        if (motionEventAnalyzer.isLeftSwipe() && !paginationToLeft) {
            return false;
        }

        if (motionEventAnalyzer.isRightSwipe() && !paginationToRight) {
            return false;
        }

        return false;
    }

    public void setPaginationToRight(boolean enabled) {
        paginationToRight = enabled;
    }

    public void setPaginationToLeft(boolean enabled) {
        paginationToLeft = enabled;
    }
}
