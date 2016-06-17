package io.wumf.wumf.firebase2.impl;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import io.wumf.wumf.pojo.App;

/**
 * Created by max on 08.06.16.
 */
class AnyNode {

    protected DatabaseReference getRootRef() {
        return FirebaseDatabase.getInstance().getReference();
    }

    protected String getAppKey(App app) {
        return app.packageName.replace(".", " ");
    }

}
