package io.wumf.wumf.asyncTask;

import android.content.Context;
import android.os.AsyncTask;

import io.realm.Realm;
import io.wumf.wumf.realmObject.App;
import io.wumf.wumf.util.AppUtils;

/**
 * Created by max on 30.03.16.
 */
public class AddAppAsyncTask extends AsyncTask<String, Void, App> {

    private final Context context;

    public AddAppAsyncTask(Context context) {
        this.context = context;
    }

    @Override
    protected App doInBackground(String... params) {
        return new AppUtils(context).loadAppFromSystem(params[0]);
    }

    protected void onPostExecute(App app) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(app);
        realm.commitTransaction();
    }
}
