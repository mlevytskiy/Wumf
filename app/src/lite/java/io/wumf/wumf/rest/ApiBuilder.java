package io.wumf.wumf.rest;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by max on 31.03.16.
 */
public class ApiBuilder<T> {

    public static final String URL = "http://ipinfo.io/";

    private T build(Class<T> api, String url) {
        return new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(api);
    }

    protected T build(Class<T> api) {
        return build(api, URL);
    }

}
