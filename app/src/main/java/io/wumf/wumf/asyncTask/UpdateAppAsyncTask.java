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
public class UpdateAppAsyncTask extends AsyncTask<App, Void, BaseAppInfo> {

    private final Context context;
    private App app;

    public UpdateAppAsyncTask(Context context) {
        this.context = context;
    }

    @Override
    protected BaseAppInfo doInBackground(App... params) {
        this.app = params[0];
        return new AppUtils(context).loadBaseAppInfoFromSystem(app.getPackageName());
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
            app.getEvents().add(event);
            realm.copyToRealmOrUpdate(app);
            realm.commitTransaction();
        }
    }

}
