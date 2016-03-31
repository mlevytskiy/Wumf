package io.wumf.wumf.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import java.util.List;

import io.realm.Realm;
import io.wumf.wumf.R;
import io.wumf.wumf.realmObject.App;
import io.wumf.wumf.util.AppUtils;

/**
 * Created by max on 30.03.16.
 */
public class SplashScreenActivity extends Activity {

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (Realm.getDefaultInstance().isEmpty()) {
            setContentView(R.layout.activity_splash);
            loadingData();
        } else {
            gotoMainActivity();
        }
    }

    private void gotoMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
    }

    private void loadingData() {
        new AsyncTask<Void, Void, List<App>>() {

            @Override
            protected List<App> doInBackground(Void... params) {
                    return new AppUtils(SplashScreenActivity.this).loadAllAppsFromSystem();
            }

            protected void onPostExecute(List<App> apps) {
                Realm.getDefaultInstance().beginTransaction();
                Realm.getDefaultInstance().copyToRealmOrUpdate(apps);
                Realm.getDefaultInstance().commitTransaction();
                gotoMainActivity();
            }
        }.execute();
    }

}
