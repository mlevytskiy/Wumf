package io.wumf.wumf.realmObject;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by max on 04.07.16.
 */
public class FriendApp extends RealmObject {

    @PrimaryKey
    private String packageName;
    private String icon;
    private String label;
    private RealmList<Friend> whose;

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public RealmList<Friend> getWhose() {
        return whose;
    }

    public void setWhose(RealmList<Friend> whose) {
        this.whose = whose;
    }
}
