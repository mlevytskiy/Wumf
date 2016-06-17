package io.wumf.wumf.firebase2.impl.pojo;

import java.util.List;

/**
 * Created by max on 08.06.16.
 */
public class SocialAppInfo {

    public final List<String> used;
    public final List<String> liked;
    public final List<String> removed;

    public SocialAppInfo(List<String> used, List<String> liked, List<String> removed) {
        this.used = used;
        this.liked = liked;
        this.removed = removed;
    }

}
