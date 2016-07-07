package io.wumf.wumf.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import io.wumf.wumf.R;
import io.wumf.wumf.activity.common.AnimationActivity;

/**
 * Created by max on 04.07.16.
 */
public class FirebaseAppsActivity extends AnimationActivity {

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_firebase_apps);
    }

    public void onClickForEachFriend(View view) {
        Toast.makeText(this, "onClickForEachFriend", Toast.LENGTH_LONG).show();
    }

    public void onClickAllApps(View view) {
        startActivity(new Intent(this, FirebaseAppsActivity2.class));
    }

}
