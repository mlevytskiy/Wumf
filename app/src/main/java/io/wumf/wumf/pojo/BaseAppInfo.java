package io.wumf.wumf.pojo;

/**
 * Created by max on 07.04.16.
 */
public class BaseAppInfo {

    private String launcherActivity;
    private String packageName;
    private long installDate;

    public String getLauncherActivity() {
        return launcherActivity;
    }

    public void setLauncherActivity(String launcherActivity) {
        this.launcherActivity = launcherActivity;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public long getInstallDate() {
        return installDate;
    }

    public void setInstallDate(long installDate) {
        this.installDate = installDate;
    }
}
