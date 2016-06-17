package io.wumf.wumf.firebase2.impl;

import java.util.Map;

import io.wumf.wumf.firebase2.impl.pojo.FullAppInfo;
import io.wumf.wumf.pojo.App;

/**
 * Created by max on 08.06.16.
 */
public interface HasAppsListener {

    void receive(Map<App, FullAppInfo> map);

}
