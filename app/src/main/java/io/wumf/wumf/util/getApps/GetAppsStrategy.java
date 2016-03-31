package io.wumf.wumf.util.getApps;

import java.util.List;

import io.wumf.wumf.memory.App;

/**
 * Created by max on 31.03.16.
 */
public interface GetAppsStrategy {

    List<App> get();

    public void getAsync(Callback<List<App>> callback);

}
