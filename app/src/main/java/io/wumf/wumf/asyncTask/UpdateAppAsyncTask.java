package io.wumf.wumf.asyncTask;

import android.content.Context;
import android.os.AsyncTask;

import io.realm.Realm;
import io.wumf.wumf.pojo.BaseAppInfo;
import io.wumf.wumf.realmObject.App;
import io.wumf.wumf.realmObject.Event;
import io.wumf.wumf.realmObject.EventType;
import io.wumf.wumf.util.AppUtils;

/**
 * Created by max on 30.03.16.
 */
public class UpdateAppAsyncTask extends AsyncTask<Void, Void, BaseAppInfo> {

    private final Context context;
    private App app;
    private String packageName;

    public UpdateAppAsyncTask(Context context, App app) {
        this.context = context;
        this.app = app;
        packageName = app.getPackageName();
    }

    @Override
    protected BaseAppInfo doInBackground(Void... voids) {
        return new AppUtils(context).loadBaseAppInfoFromSystem(packageName);
    }

    protected void onPostExecute(BaseAppInfo appInfo) {
        int lastEventIndex = app.getEvents().size() - 1;
        lastEventIndex = lastEventIndex < 0 ? 0 : lastEventIndex;
        long lastEventTime = app.getEvents().get(lastEventIndex).getTime();
        if (lastEventTime == appInfo.getInstallDate()) {
            //do nothing
        } else {
            Realm realm = Realm.getDefaultInstance();
            realm.beginTransaction();
            Event event = new Event();
            event.setApp(app);
            event.setTime(appInfo.getInstallDate());
            event.setEventType(EventType.toInt(EventType.UPDATE));
            event.setTimeAndAppPrimaryKey(app.getLauncherActivity() + app.getInstallDate());
            app.getEvents().add(event);
            realm.copyToRealmOrUpdate(app);
            realm.commitTransaction();
        }
    }

}
