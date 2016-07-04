package io.wumf.wumf.firebase.uploadDataPart1;

/**
 * Created by max on 04.07.16.
 */
public class CountDown {

    private volatile int count;
    private Runnable zeroListener;

    public CountDown(int size) {
        count = size;
    }

    public void countDown() {
        count = count - 1;
        if (count == 0) {
            zeroListener.run();
        }
    }

    public void setZeroListener(Runnable listener) {
        zeroListener = listener;
    }

}
