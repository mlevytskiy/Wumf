package io.wumf.wumf.firebase.uploadDataToPhonesNode;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import io.wumf.wumf.firebase.pojo.App;
import io.wumf.wumf.memory.Memory;

/**
 * Created by max on 04.07.16.
 */
public class FirebasePhonesUtil {

    private static final String TAG = FirebasePhonesUtil.class.getSimpleName();

    private static final DatabaseReference PHONES_REF = FirebaseDatabase.getInstance().getReference().child("phones");

    public static void upload(List<App> apps) {
        final DatabaseReference ref = PHONES_REF.child(Memory.INSTANCE.getPhone());
        for (final App app : apps) {
            ref.child(app.getFirebaseKey()).addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (!dataSnapshot.exists()) {
                        ref.child(app.getFirebaseKey()).setValue(app.getFirebaseValueDataPart2());
                    } else {
                        //do nothing
                    }
                    ref.child(app.getFirebaseKey()).removeEventListener(this);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    //do nothing
                }
            });
        }
    }

//    public static void load(final List<Contact> friends) {
//        final Map<Contact, List<io.wumf.wumf.pojo.App>> map = new HashMap<>();
//        for (final Contact friend : friends) {
//            Log.i(TAG, "phone=" + friend.getPhone());
//            PHONES_REF.child(friend.getPhone()).addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(DataSnapshot dataSnapshot) {
//
//                    List<io.wumf.wumf.pojo.App> apps = new ArrayList<>();
//
//                    for (DataSnapshot value : dataSnapshot.getChildren()) {
//                        String packageName = toPackageName(value.getKey());
//                        FirebaseApp firebaseApp = value.getValue(FirebaseApp.class);
//                        io.wumf.wumf.pojo.App app = new io.wumf.wumf.pojo.App(null, firebaseApp.getName(), packageName);
//                        app.setRemoteIconPath(firebaseApp.getIcon());
//                        apps.add(app);
//                    }
//
//                    map.put(friend, apps);
//
//                    PHONES_REF.child(friend.getPhone()).removeEventListener(this);
//                    if (map.size() == friends.size()) {
//                        BusProvider.getInstance().post(new FirebaseLoadFriendsFinishedEvent(map));
//                    }
//
//                }
//
//                @Override
//                public void onCancelled(DatabaseError databaseError) {
//                    PHONES_REF.child(friend.getPhone()).removeEventListener(this);
//                    map.put(friend, null);
//                    if (map.size() == friends.size()) {
//                        BusProvider.getInstance().post(new FirebaseLoadFriendsFinishedEvent(map));
//                    }
//                }
//            });
//        }
//    }

    private static String toPackageName(String key) {
        return key.replace(" ", ".");
    }

}
