package io.wumf.wumf.rest;

import io.wumf.wumf.rest.pojo.Location;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by max on 21.07.16.
 */
public interface LocationApi {

    @GET("/json/")
    Call<Location> getLoc();

    class Builder extends ApiBuilder<LocationApi> {

        public LocationApi build() {
            return build(LocationApi.class);
        }

    }
}
