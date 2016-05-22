package io.wumf.wumf.util.packageSizeUtilImpl;

import android.content.pm.IPackageStatsObserver;
import android.content.pm.PackageStats;
import android.os.RemoteException;

/**
 * Created by max on 17.05.16.
 */
public abstract class PkgSizeObserver extends IPackageStatsObserver.Stub {

//    private PackageStats packageStats;

    @Override
    public void onGetStatsCompleted(PackageStats pStats, boolean succeeded)
            throws RemoteException {
//        packageStats = pStats;
        onGetSize(pStats.codeSize + pStats.dataSize);

    }

    public abstract void onGetSize(long size);

    public abstract void onGetApplicationSize(long size);

    public abstract void onGetCacheSize(long size);

    public abstract void onGetDataSize(long size);

    public abstract void onGetSDCardApp(long size);

    public abstract void onGetSDCardData(long size);

}
