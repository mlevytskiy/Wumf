package io.wumf.wumf.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import io.wumf.wumf.R;
import io.wumf.wumf.activity.common.AnimationActivity;

/**
 * Created by max on 04.07.16.
 */
public class FirebaseMainActivity extends AnimationActivity {

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_firebase_main);
    }

    public void onClickApps(View view) {
        Toast.makeText(this, "onClickApps", Toast.LENGTH_LONG).show();
    }

    public void onClickPhones(View view) {
        Toast.makeText(this, "onClickPhones", Toast.LENGTH_LONG).show();
    }

}
