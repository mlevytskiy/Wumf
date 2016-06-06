package io.wumf.wumf.firebase;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.UploadTask;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by max on 03.06.16.
 */
public class RxUploadTask {

    private UploadTask uploadTask;
    private String phoneNumber;
    private String appPackage;
    private String name;

    public RxUploadTask(UploadTask uploadTask, String phoneNumber, String appPackage, String name) {
        this.uploadTask = uploadTask;
        this.phoneNumber = phoneNumber;
        this.appPackage = appPackage;
        this.name = name;
    }

    public Observable<UploadTask.TaskSnapshot> getObservable() {
        return Observable.create(new Observable.OnSubscribe<UploadTask.TaskSnapshot>() {

            @Override
            public void call(final Subscriber<? super UploadTask.TaskSnapshot> subscriber) {

                uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        FirebaseUtil.setAppValue(phoneNumber, appPackage, taskSnapshot.getDownloadUrl().toString(), name);
                        subscriber.onNext(taskSnapshot);
                        subscriber.onCompleted();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        subscriber.onError(e);
                    }
                });

            }
        });
    }

}
