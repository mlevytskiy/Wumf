package io.wumf.wumf.firebase.uploadDataToAppsNode;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.wumf.wumf.firebase.pojo.App;
import io.wumf.wumf.firebase.uploadDataToAppsNode.pojo.FirebaseApp;
import io.wumf.wumf.otto.BusProvider;
import io.wumf.wumf.otto.event.FirebaseUploadStartedEvent;
import io.wumf.wumf.otto.event.UploadDataPart1FinishedEvent;

/**
 * Created by max on 28.06.16.
 */
public class FirebaseAppsUtil {

    private static final String TAG = FirebaseAppsUtil.class.getSimpleName();

    private static boolean success = true;
    private static CountDown countDown;

    private static void uploadTestData2(final List<App> apps) {
        BusProvider.getInstance().post(new FirebaseUploadStartedEvent());
        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("apps");
        countDown = new CountDown(apps.size());

        countDown.setZeroListener(new Runnable() {
            @Override
            public void run() {
                BusProvider.getInstance().post(new UploadDataPart1FinishedEvent(apps, success));
            }
        });

        for (final App app : apps) {
            ref.child(app.getFirebaseKey()).addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (!dataSnapshot.exists()) {
                        ref.child(app.getFirebaseKey()).setValue(app.getFirebaseValueDataPart1());
                        uploadIcon(ref, app);
                    } else {
                        countDown.countDown();
                        //check need upload icon or no

                        //check need add my phone or no.
                        FirebaseApp firebaseApp = dataSnapshot.getValue(FirebaseApp.class);
                        app.setPublicIconPath(firebaseApp.getIcon());
                        String myPhone = app.getFirebaseValueDataPart1().getPhones().get(0);
                        if (firebaseApp.getPhones().contains(myPhone)) {
                            //do nothing
                        } else {
                            int size = firebaseApp.getPhones().size();
                            ref.child(app.getFirebaseKey()).child("phones").child(String.valueOf(size)).setValue(myPhone);
                        }
                    }
                    ref.child(app.getFirebaseKey()).removeEventListener(this);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    success = false;
                    countDown.countDown();
                }
            });
        }
    }

    public static void upload(List<io.wumf.wumf.realmObject.App> apps) {
        List<App> newApps = new ArrayList<>();
        for (io.wumf.wumf.realmObject.App app : apps) {
            if (app.isSystemApp()) {
                //do nothing
            } else {
                newApps.add(new App(app.getPackageName(), app.getLabel(), app.getIconPath()));
            }
        }

        uploadTestData2(newApps);
    }

    private static void uploadIcon(final DatabaseReference ref, final App app) {
        StorageReference storageReference = FirebaseStorage.getInstance().getReference(app.packageName + ".webp");
        UploadTask uploadTask = storageReference.putFile(Uri.fromFile(new File(app.icon)));
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                success = false;
                countDown.countDown();
                Log.e(TAG, "", e);
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Log.i(TAG, "onSuccess");
                countDown.countDown();
                app.setPublicIconPath(taskSnapshot.getDownloadUrl().toString());
                ref.child(app.getFirebaseKey()).child("icon").setValue(app.getFirebaseValueDataPart1().getIcon());
            }
        });

    }

}
