package io.wumf.wumf.firebase;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;

import java.util.List;

import io.wumf.wumf.pojo.App;
import io.wumf.wumf.pojo.FirebaseApp;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by max on 29.05.16.
 */
public class FirebaseUtil {

    public static void setMyInfo(String phoneNumber, MyDeviceInfo myDeviceInfo) {
        try {
            getMyBaseInfo(phoneNumber).setValue(myDeviceInfo);
        } catch (ItsPhoneAddedFirstTimeException e) {
            createMyBaseInfo(phoneNumber, myDeviceInfo);
        }
    }

    public static Observable<UploadTask.TaskSnapshot> setFullAppsInfo(List<App> apps) {

        return Observable.from(apps)
                .flatMap(new Func1<App, Observable<UploadTask.TaskSnapshot>>() {
                    @Override
                    public Observable<UploadTask.TaskSnapshot> call(App app) {
                        UploadTask uploadTask = FirebaseStorage.getInstance().getReference(app.packageName + ".webp").putFile(app.localIconPath);
                        RxUploadTask rxUploadTask = new RxUploadTask(uploadTask, "+38063767443", app.packageName, app.name);
                        return rxUploadTask.getObservable();
                    }
                });

    }

    public static void setAppValue(String phoneNumber, String appPackage, String iconUri, String name) {
        try {
            DatabaseReference ref = getMyBaseInfo(phoneNumber);
            FirebaseApp firebaseApp = new FirebaseApp(name, iconUri);
            String appFolder = appPackage.replace(".", " ");
            ref.child("apps").child(appFolder).setValue(firebaseApp);
        } catch (ItsPhoneAddedFirstTimeException e) {
            //do nothing
        }
    }

    private static DatabaseReference getRootRef() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference();
        return ref;
    }

    private static DatabaseReference getMyBaseInfo(final String phoneNumber) throws ItsPhoneAddedFirstTimeException {
        try {
            return getRootRef().child("phones").child(phoneNumber);
        } catch (Exception e) {
            throw new ItsPhoneAddedFirstTimeException();
        }
    }

    private static void createMyBaseInfo(final String phoneNumber, final MyDeviceInfo myDeviceInfo) {
        getRootRef().addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild("phones")) {
                    if (dataSnapshot.child("phones").hasChild(phoneNumber)) {
                        //do nothing
                    } else {
                        dataSnapshot.getRef().child("phones").child(phoneNumber).push().setValue(myDeviceInfo);
                    }
                } else {
                    dataSnapshot.getRef().child("phones").push().child(phoneNumber).push().setValue(myDeviceInfo);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
