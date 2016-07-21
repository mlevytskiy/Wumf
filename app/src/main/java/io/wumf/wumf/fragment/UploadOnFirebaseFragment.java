package io.wumf.wumf.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import io.wumf.wumf.R;
import io.wumf.wumf.activity.FirebaseMainActivity;
import io.wumf.wumf.asyncTask.SyncContactsAsyncTask;

/**
 * Created by max on 01.07.16.
 */
public class UploadOnFirebaseFragment extends Fragment {

    private static final String TAG = UploadOnFirebaseFragment.class.getSimpleName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_upload_on_firebase, container, false);

        Button upload  = (Button) view.findViewById(R.id.upload);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "upload", Toast.LENGTH_LONG).show();
                new SyncContactsAsyncTask().execute();
//                RealmResults<App> realmResults = Realm.getDefaultInstance().where(App.class)
//                        .equalTo("systemApp", false).findAll();
//                List<App> apps = realmResults.subList(0, realmResults.size());
//                FirebaseAppsUtil.upload(apps);
            }
        });

        Button showFirebaseData = (Button) view.findViewById(R.id.showFirebaseData);
        showFirebaseData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), FirebaseMainActivity.class));
            }
        });

        Button loadFriendsData = (Button) view.findViewById(R.id.loadFriendsData);
        loadFriendsData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                FirebasePhonesUtil.load(WumfApp.instance.getFriends());
            }
        });

        return view;
    }
}
