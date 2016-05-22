package io.wumf.wumf.util;

import android.content.pm.IPackageStatsObserver;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Process;
import android.util.Log;

import java.lang.reflect.Method;

import io.wumf.wumf.util.packageSizeUtilImpl.PkgSizeObserver;

/**
 * Created by max on 17.05.16.
 */
public class PackageSizeUtil {

    private static final String TAG = PackageSizeUtil.class.getSimpleName();

    public static void queryPackageSize(PackageManager pm, String pkgName, PkgSizeObserver observer) throws Exception {
        PackageInfo info = pm.getPackageInfo(pkgName, 0);
        if (pkgName != null) {
            try {
                Method getPackageSizeInfo = pm.getClass().getDeclaredMethod(
                        "getPackageSizeInfo", String.class, int.class,
                        IPackageStatsObserver.class);
                getPackageSizeInfo.invoke(pm, pkgName,
                        Process.myUid() / 100000, observer);
            } catch (Exception ex) {
                Log.e(TAG, "NoSuchMethodException");
                ex.printStackTrace();
                throw ex;
            }
        }
    }

}
