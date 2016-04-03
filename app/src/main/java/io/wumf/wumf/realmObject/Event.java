package io.wumf.wumf.realmObject;

import io.realm.RealmObject;

/**
 * Created by max on 31.03.16.
 */
public class Event extends RealmObject {

    private int eventType;
    private long time;
    private App app;

    public int getEventType() {
        return eventType;
    }

    public void setEventType(int eventType) {
        this.eventType = eventType;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public App getApp() {
        return app;
    }

    public void setApp(App app) {
        this.app = app;
    }
}
