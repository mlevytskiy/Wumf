package io.wumf.wumf.util;

import io.realm.Realm;
import io.wumf.wumf.realmObject.App;

/**
 * Created by max on 31.03.16.
 */
public class FindApps {

    public static App findApp(String packageName) {
        return Realm.getDefaultInstance().where(App.class).equalTo("packageName", packageName).findFirst();
    }

}
