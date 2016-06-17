package io.wumf.wumf.firebase2.impl;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.wumf.wumf.firebase2.impl.pojo.FullAppInfo;
import io.wumf.wumf.firebase2.impl.pojo.SocialAppInfo;
import io.wumf.wumf.mock.MockFactory;
import io.wumf.wumf.pojo.App;

/**
 * Created by max on 08.06.16.
 */
public class AppsNode extends AnyNode {

    protected DatabaseReference getRootRef() {
        return super.getRootRef().child("apps");
    }

    public void setApps(List<App> apps) { // на этом этапе тут есть вся инфа о ссылки на icon
        for (App app : apps) {
            FullAppInfo fullAppInfo = new FullAppInfo(true, app.name, app.getRemoteIconPath(), MockFactory.createSocialAppInfo());
            getRootRef().child(getAppKey(app)).setValue(fullAppInfo);
        }
    }

    public void updateConcreteApp(final App app, final String myPhoneNumber) {
        String appKey = getAppKey(app);
        getRootRef().child(appKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                FullAppInfo fullAppInfo = dataSnapshot.getValue(FullAppInfo.class);
                SocialAppInfo socialAppInfo = fullAppInfo.socialAppInfo;
                if ( !socialAppInfo.used.contains(myPhoneNumber) ) {
                    socialAppInfo.used.add(myPhoneNumber);
                } else {
                    //do nothing
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //do something
            }
        });
    }

    public void hasApps(final List<App> apps, final HasAppsListener hasAppsListener) { // на этом этапе тут нету инфо о ссылки на icon
        getRootRef().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<App, FullAppInfo> map = new HashMap<>();
                for (App app : apps) {
                    DataSnapshot child = dataSnapshot.child(getAppKey(app));
                    if (child.exists()) {
                        FullAppInfo fullAppInfo = child.getValue(FullAppInfo.class);
                        map.put(app, fullAppInfo);
                    } else {
                        //do nothing
                    }
                }
                hasAppsListener.receive(map);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //do something
            }
        });
    }

}
