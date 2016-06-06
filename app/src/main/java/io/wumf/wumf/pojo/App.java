package io.wumf.wumf.pojo;

import android.net.Uri;

/**
 * Created by max on 02.06.16.
 */
public class App {

    public final Uri localIconPath;
    public final String name;
    public final String packageName;
    private String remoteIconPath;

    public App(Uri icon, String name, String packageName) {
        this.localIconPath = icon;
        this.name = name;
        this.packageName = packageName;
    }

    public String getRemoteIconPath() {
        return remoteIconPath;
    }

    public void setRemoteIconPath(String remoteIconPath) {
        this.remoteIconPath = remoteIconPath;
    }

}
