package io.wumf.wumf.realmObject;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by max on 04.07.16.
 */
public class Friend extends RealmObject {

    @PrimaryKey
    private String phoneNumber;
    private RealmList<FriendApp> apps;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public RealmList<FriendApp> getApps() {
        return apps;
    }

    public void setApps(RealmList<FriendApp> apps) {
        this.apps = apps;
    }

}
