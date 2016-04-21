package io.wumf.wumf.activity;

import android.os.Bundle;

import io.realm.Realm;
import io.wumf.wumf.R;
import io.wumf.wumf.activity.common.AnimationActivity;
import io.wumf.wumf.realmObject.App;
import io.wumf.wumf.view.IconImageView;
import io.wumf.wumf.view.LabelTextView;

/**
 * Created by max on 11.04.16.
 */
public class AppActivity extends AnimationActivity {

    public static final String APP_ID = "appPrimaryKey";
    private App app = null;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_app);
        String appPrimaryKey = getIntent().getStringExtra(APP_ID);
        app = Realm.getDefaultInstance().where(App.class).equalTo("launcherActivity", appPrimaryKey).findFirst();
        IconImageView iconImageView = (IconImageView) findViewById(R.id.icon);
        iconImageView.setApp(app);
        LabelTextView labelTextView = (LabelTextView) findViewById(R.id.label);
        labelTextView.setApp(app);
    }

}
