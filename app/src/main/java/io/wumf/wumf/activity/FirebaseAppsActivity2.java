package io.wumf.wumf.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.GridView;

import java.util.List;
import java.util.Map;

import io.wumf.wumf.R;
import io.wumf.wumf.activity.common.AnimationActivity;
import io.wumf.wumf.adapter.firebase.AppsAdapter;
import io.wumf.wumf.application.WumfApp;
import io.wumf.wumf.contacts.Contact;
import io.wumf.wumf.pojo.App;

/**
 * Created by max on 05.07.16.
 */
public class FirebaseAppsActivity2 extends AnimationActivity {

    public static final String FRIEND_KEY = "friend_key";

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_firebase_apps2);
        GridView gridView = (GridView) findViewById(R.id.grid_view);
        Contact contact = getIntent().getExtras().getParcelable(FRIEND_KEY);
        gridView.setAdapter( new AppsAdapter(getValue(contact)) );
    }

    private List<App> getValue(Contact contact) {
        for (Map.Entry<Contact, List<App>> entry : WumfApp.instance.friendsFullInfo.entrySet()) {
            if (TextUtils.equals(entry.getKey().getPhone(), contact.getPhone())) {
                return entry.getValue();
            }
        }
        return null;
    }

}
