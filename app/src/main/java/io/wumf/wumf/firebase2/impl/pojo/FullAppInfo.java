package io.wumf.wumf.firebase2.impl.pojo;

/**
 * Created by max on 08.06.16.
 */
public class FullAppInfo {

    public final boolean hasIconOnFirebase;
    public final String name;
    public final String icon;
    public final SocialAppInfo socialAppInfo;

    public FullAppInfo(boolean hasIconOnFirebase, String name, String icon, SocialAppInfo socialAppInfo) {
        this.hasIconOnFirebase = hasIconOnFirebase;
        this.socialAppInfo = socialAppInfo;
        this.name = name;
        this.icon = icon;
    }

}
