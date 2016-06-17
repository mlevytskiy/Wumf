package io.wumf.wumf.firebase2;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.List;
import java.util.Map;

import io.wumf.wumf.firebase.MyDeviceInfo;
import io.wumf.wumf.firebase.RxUploadTask;
import io.wumf.wumf.firebase2.impl.AppsNode;
import io.wumf.wumf.firebase2.impl.HasAppsListener;
import io.wumf.wumf.firebase2.impl.PhonesNode;
import io.wumf.wumf.firebase2.impl.pojo.FullAppInfo;
import io.wumf.wumf.pojo.App;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by max on 08.06.16.
 */
public class FirebaseApi {

    private static AppsNode appsNode = new AppsNode();
    private static PhonesNode phonesNode = new PhonesNode();

    public static void setFullMyInfo(String phoneNumber, MyDeviceInfo info, List<App> apps) {
        appsNode.hasApps(apps, new HasAppsListener() {
            @Override
            public void receive(Map<App, FullAppInfo> map) {
                for(Map.Entry<App, FullAppInfo> entry : map.entrySet()) {
                    if ( (entry.getValue() == null) || (!entry.getValue().hasIconOnFirebase) ) {
                        // залить картинку в сторедж и в AppsNode кинуть инфу об апке

                    } else {
                        // Обновить инфу о пользователе даной апке в AppsNode
                    }
                    //залить инфу в PhonesNode
                }
            }
        });
    }

    public static void setAppValue(String phoneNumber, String appPackage, String iconUri, String name) {
        appsNode.updateConcreteApp();
    }

    private static Observable<UploadTask.TaskSnapshot> setFullAppsInfo(List<App> apps) {

        return Observable.from(apps)
                .flatMap(new Func1<App, Observable<UploadTask.TaskSnapshot>>() {
                    @Override
                    public Observable<UploadTask.TaskSnapshot> call(App app) {
                        StorageReference storageReference = FirebaseStorage.getInstance().getReference(app.packageName + ".webp");
                        UploadTask uploadTask = storageReference.putFile(app.localIconPath);
                        RxUploadTask rxUploadTask = new RxUploadTask(uploadTask, "+38063767443", app.packageName, app.name);
                        return rxUploadTask.getObservable();
                    }
                });

    }

    public static boolean hasCache() {
        return false;
    }

    public static void sync() {

    }

}
