package io.wumf.wumf.mock;

import java.util.ArrayList;

import io.wumf.wumf.firebase2.impl.pojo.SocialAppInfo;

/**
 * Created by max on 09.06.16.
 */
public class MockFactory {

    public static SocialAppInfo createSocialAppInfo() {
        return new SocialAppInfo(new ArrayList<String>(), new ArrayList<String>(), new ArrayList<String>());
    }

}
