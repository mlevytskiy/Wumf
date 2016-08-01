package io.wumf.wumf.firebase.pojo;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

import io.wumf.wumf.application.WumfApp;
import io.wumf.wumf.firebase.uploadDataToAppsNode.pojo.FirebaseApp;
import io.wumf.wumf.memory.Memory;

/**
 * Created by max on 28.06.16.
 */
public class App {

    public static final String ICON_IS_NOT_LOADING_YET = "?";

    public final String packageName;
    public final String name;
    public final String icon; //inner path when we start upload and firebase storage parth after
    private String publicIconPath;

    public App(String packageName, String name, String icon) {
        this.packageName = packageName;
        this.name = name;
        this.icon = icon;
    }

    public String getPublicIconPath() {
        return publicIconPath;
    }

    public void setPublicIconPath(String value) {
        this.publicIconPath = value;
    }

    public String getFirebaseKey() {
        return packageName.replace(".", " ");
    }

    public FirebaseApp getFirebaseValueDataPart1() {
        FirebaseApp firebaseApp = new FirebaseApp();
        firebaseApp.setName(name);
        if (TextUtils.isEmpty(publicIconPath)) {
            firebaseApp.setIcon(ICON_IS_NOT_LOADING_YET);
        } else {
            firebaseApp.setIcon(publicIconPath);
        }
        List<String> phones = new ArrayList<>();
        phones.add(WumfApp.instance.androidId);
        firebaseApp.setPhones(phones);
        return firebaseApp;
    }

    public io.wumf.wumf.firebase.uploadDataToPhonesNode.pojo.FirebaseApp getFirebaseValueDataPart2() {
        io.wumf.wumf.firebase.uploadDataToPhonesNode.pojo.FirebaseApp firebaseApp = new
                io.wumf.wumf.firebase.uploadDataToPhonesNode.pojo.FirebaseApp();
        firebaseApp.setName(name);
        firebaseApp.setIcon(publicIconPath);
        return firebaseApp;
    }

    public String toString() {
        return packageName;
    }

}
