package io.wumf.wumf.memory;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by max on 25.03.16.
 */
public class App extends RealmObject {

    @PrimaryKey
    private String packageName;
    private boolean isRemoved;
    private long installDate;
    private String iconPath;
    private String label;

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public boolean isRemoved() {
        return isRemoved;
    }

    public void setRemoved(boolean removed) {
        isRemoved = removed;
    }

    public long getInstallDate() {
        return installDate;
    }

    public void setInstallDate(long installDate) {
        this.installDate = installDate;
    }

    public String getIconPath() {
        return iconPath;
    }

    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
