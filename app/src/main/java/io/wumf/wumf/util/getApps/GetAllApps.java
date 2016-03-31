package io.wumf.wumf.util.getApps;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.wumf.wumf.memory.App;

/**
 * Created by max on 31.03.16.
 */
public class GetAllApps implements GetAppsStrategy {

    @Override
    public List<App> get() {
        RealmResults<App> results = getRealmQuery().findAll();
        return results.subList(0, results.size());
    }

    @Override
    public void getAsync(final Callback<List<App>> callback) {
        final RealmResults<App> results = getRealmQuery().findAllAsync();
        results.addChangeListener(new RealmChangeListener() {
            @Override
            public void onChange() {
                results.removeChangeListener(this);
                callback.receive(results.subList(0, results.size()));
            }
        });
    }

    private RealmQuery<App> getRealmQuery() {
        return Realm.getDefaultInstance().where(App.class);
    }

}
