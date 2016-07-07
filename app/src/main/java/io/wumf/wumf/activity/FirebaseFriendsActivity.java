package io.wumf.wumf.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import io.wumf.wumf.R;
import io.wumf.wumf.adapter.firebase.FriendsAdapter;
import io.wumf.wumf.application.WumfApp;
import io.wumf.wumf.contacts.Contact;

/**
 * Created by max on 06.07.16.
 */
public class FirebaseFriendsActivity extends Activity {

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_firebase_friends);
        ListView listView = (ListView) findViewById(R.id.list_view);

        final FriendsAdapter adapter = new FriendsAdapter(WumfApp.instance.friendsFullInfo);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Contact friend = adapter.getItem(position);
                startActivity(new Intent(parent.getContext(), FirebaseAppsActivity2.class).putExtra(FirebaseAppsActivity2.FRIEND_KEY, friend));
            }
        });
    }

}
