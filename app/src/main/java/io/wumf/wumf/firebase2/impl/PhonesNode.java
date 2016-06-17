package io.wumf.wumf.firebase2.impl;

import com.google.firebase.database.DatabaseReference;

import java.util.Map;

import io.wumf.wumf.firebase.MyDeviceInfo;
import io.wumf.wumf.pojo.FirebaseApp;

/**
 * Created by max on 08.06.16.
 */
public class PhonesNode extends AnyNode {

    protected DatabaseReference getRootRef() {
        return super.getRootRef().child("phones");
    }

    public void setMyInfo(String phoneNumber, Map<String, FirebaseApp> appsMap, MyDeviceInfo myDeviceInfo) {
        getRootRef().child(phoneNumber).push().setValue(myDeviceInfo);
        for (Map.Entry<String, FirebaseApp> entry : appsMap.entrySet()) {
            getRootRef().child(phoneNumber).child(entry.getKey()).push().setValue(entry.getValue());
        }
    }

}
