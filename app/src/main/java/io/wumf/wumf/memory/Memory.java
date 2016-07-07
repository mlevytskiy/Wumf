package io.wumf.wumf.memory;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by max on 05.07.16.
 */
public enum Memory {

    INSTANCE;

    private static final String REPOSITORY_NAME = "memory";
    private static final String PHONE_KEY = "phone";

    private SharedPreferences sharedPreferences;

    public void init(Context context) {
        sharedPreferences = context.getSharedPreferences(REPOSITORY_NAME, Context.MODE_PRIVATE);
    }

    public void setPhone(String value) {
        sharedPreferences.edit().putString(PHONE_KEY, value).apply();
    }

    public String getPhone() {
        return sharedPreferences.getString(PHONE_KEY, "");
    }

}
