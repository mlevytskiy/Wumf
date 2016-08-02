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

import io.wumf.wumf.application.WumfApp;
import io.wumf.wumf.firebase.pojo.App;
import io.wumf.wumf.firebase.uploadDataToAppsNode.pojo.FirebaseApp;
import io.wumf.wumf.firebase.uploadDataToPlacesNode.FirebasePlaceUtils;
import io.wumf.wumf.otto.BusProvider;
import io.wumf.wumf.otto.event.FirebaseLoadAppsFinishedEvent;
import io.wumf.wumf.otto.event.FirebaseUploadStartedEvent;
import io.wumf.wumf.otto.event.UploadDataPart1FinishedEvent;

/**
 * Created by max on 28.06.16.
 */
public class FirebaseAppsUtil {

    private static final String TAG = FirebaseAppsUtil.class.getSimpleName();

    private static boolean success = true;
    private static CountDown countDown;

    private static boolean isCityLoaded = false;
    private static boolean isAppsLoaded = false;

    private static String city;
    private static String country;

    private static List<App> myApps;

    private static void uploadAppsAndPlace(final List<App> apps, String country, String city) {
        BusProvider.getInstance().post(new FirebaseUploadStartedEvent());
        String placeId = FirebasePlaceUtils.generatePlaceId(country, city);
        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("apps").child(placeId);
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

                        if (firebaseApp.getPhones().contains(WumfApp.instance.androidId)) {
                            //do nothing
                        } else {
                            int size = firebaseApp.getPhones().size();
                            ref.child(app.getFirebaseKey()).child("phones").child(String.valueOf(size)).setValue(WumfApp.instance.androidId);
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

    public static void upload(String country, String city) {
        isCityLoaded = true;
        if (isAppsLoaded) {
            uploadAppsAndPlace(myApps, country, city);
        } else {
            FirebaseAppsUtil.country = country;
            FirebaseAppsUtil.city = city;
        }
    }

    public static void upload(List<io.wumf.wumf.realmObject.App> apps) {
        isAppsLoaded = true;
        List<App> newApps = new ArrayList<>();
        for (io.wumf.wumf.realmObject.App app : apps) {
            if (app.isSystemApp()) {
                //do nothing
            } else {
                newApps.add(new App(app.getPackageName(), app.getLabel(), app.getIconPath(), app.getUsersCount()));
            }
        }
        if (isCityLoaded) {
            uploadAppsAndPlace(newApps, FirebaseAppsUtil.country, FirebaseAppsUtil.city);
        } else {
            myApps = newApps;
        }
    }

    public static void loadAppsByPlace(String placeId) {
        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("apps").child(placeId);
        ref.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<App> apps = new ArrayList<App>();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    String key = postSnapshot.getKey();
                    io.wumf.wumf.firebase.uploadDataToPhonesNode.pojo.FirebaseApp firebaseApp = postSnapshot.getValue(io.wumf.wumf.firebase.uploadDataToPhonesNode.pojo.FirebaseApp.class);
                    apps.add( new App(key.replace(" ", "."), firebaseApp.getName(), firebaseApp.getIcon(), firebaseApp.getPhones().size()) );
                }
                BusProvider.getInstance().post(new FirebaseLoadAppsFinishedEvent(apps));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
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
