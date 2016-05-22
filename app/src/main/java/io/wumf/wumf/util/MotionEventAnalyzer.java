package io.wumf.wumf.util;

import android.view.MotionEvent;

/**
 * Created by max on 16.05.16.
 */
public class MotionEventAnalyzer {

    private static final int MIN_DISTANCE = 150;
    private float x1,x2;
    private boolean isLeftSwipe;
    private boolean isRightSwipe;

    public void setMotionEvent(MotionEvent event) {
        isLeftSwipe = false;
        isRightSwipe = false;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                x1 = event.getX();
                break;
            case MotionEvent.ACTION_UP:
                x2 = event.getX();
                float deltaX = x2 - x1;
                if (Math.abs(deltaX) > MIN_DISTANCE) {
                    isRightSwipe = (deltaX > 0);
                    isLeftSwipe = (deltaX < 0);
                } else {
                    // consider as something else - a screen tap for example
                }
                break;
        }
    }

    public boolean isLeftSwipe() {
        return isLeftSwipe;
    }

    public boolean isRightSwipe() {
        return isRightSwipe;
    }
}
