package io.wumf.wumf.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import io.realm.Realm;
import io.wumf.wumf.R;
import io.wumf.wumf.realmObject.App;

/**
 * Created by max on 11.04.16.
 */
public class AppActivity extends AppCompatActivity {

    public static final String APP_ID = "appPrimaryKey";
    private App app = null;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_app);
        String appPrimaryKey = getIntent().getStringExtra(APP_ID);
        app = Realm.getDefaultInstance().where(App.class).equalTo("launcherActivity", appPrimaryKey).findFirst();
        Toast.makeText(this, "app name=" + app.getLabel(), Toast.LENGTH_LONG).show();
    }


}
