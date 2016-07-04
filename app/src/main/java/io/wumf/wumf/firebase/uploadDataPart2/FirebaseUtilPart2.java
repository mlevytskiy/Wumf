package io.wumf.wumf.firebase.uploadDataPart2;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import io.wumf.wumf.firebase.pojo.App;

/**
 * Created by max on 04.07.16.
 */
public class FirebaseUtilPart2 {

    public static void upload(List<App> apps) {
        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("phones").child("+380637674440");
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

}
