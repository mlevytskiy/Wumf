package io.wumf.wumf.util.getApps;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.wumf.wumf.realmObject.App;

/**
 * Created by max on 31.03.16.
 */
public class GetNewApps implements GetAppsStrategy {

    private long lastDateFilter;

    public GetNewApps(long lastDateFilter) {
        this.lastDateFilter = lastDateFilter;
    }

    @Override
    public List<App> get() {
        RealmResults<App> realmResults = getRealmQuery().findAll();
        return realmResults.subList(0, realmResults.size());
    }

    @Override
    public void getAsync(final Callback<List<App>> callback) {
        final RealmResults<App> realmResults = getRealmQuery().findAllAsync();
        realmResults.addChangeListener(new RealmChangeListener() {
            @Override
            public void onChange() {
                realmResults.removeChangeListener(this);
                callback.receive(realmResults.subList(0, realmResults.size()));
            }
        });
    }

    private RealmQuery<App> getRealmQuery() {
        return Realm.getDefaultInstance().where(App.class).greaterThan("installDate", lastDateFilter);
    }

}
