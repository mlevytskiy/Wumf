package io.wumf.wumf.activity.common;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import io.wumf.wumf.R;

/**
 * Created by max on 21.04.16.
 */
public class AnimationActivity extends AppCompatActivity {

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        overridePendingTransition(R.anim.activity_open_translate, R.anim.activity_close_scale);
    }

    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.activity_open_scale,R.anim.activity_close_translate);
    }

}
