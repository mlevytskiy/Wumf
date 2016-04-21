package io.wumf.wumf.activity.common;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import io.wumf.wumf.util.prepareData.PrepareDataCallback;
import io.wumf.wumf.util.prepareData.PrepareDataUtil;

/**
 * Created by max on 15.04.16.
 * //prepare data before show items
 */
public abstract class PrepareDataActivity extends AnimationActivity {

    @Override
    protected final void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PrepareDataUtil.prepare(this, new PrepareDataCallback() {
            @Override
            public void dataIsReady() {
                onCreateAfterDataPreparation(savedInstanceState);
            }
        });
    }

    protected abstract void onCreateAfterDataPreparation(Bundle savedInstanceState);

}
