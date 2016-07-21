package io.wumf.wumf;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import org.angmarch.views.NiceSpinner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.wumf.wumf.activity.common.PrepareDataActivity;
import io.wumf.wumf.application.WumfApp;
import io.wumf.wumf.rest.LocationApi;
import io.wumf.wumf.rest.pojo.Location;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by max on 17.07.16.
 */
public class MyLocationActivity extends PrepareDataActivity {

    @Override
    protected void onCreateAfterDataPreparation(Bundle savedInstanceState) {
        setContentView(R.layout.activity_my_location);

        WumfApp app = (WumfApp) getApplication();
        List<String> countries = new ArrayList<>();
        for (Map.Entry<String, List<String>> entry : app.map.entrySet()) {
            String country = entry.getKey();
            if ( !TextUtils.isEmpty(country) ) {
                countries.add(country);
            }
        }

        NiceSpinner country = (NiceSpinner) findViewById(R.id.country);
        country.attachDataSource(countries);
        country.setSelectedIndex(countries.indexOf(app.userCountry));

        Log.i("MainActivity", "app.userCountry=" + app.userCountry);

        final NiceSpinner cityView = (NiceSpinner) findViewById(R.id.city);
        final List<String> cities = app.map.get(app.userCountry);
        cityView.attachDataSource(cities);

        LocationApi locationApi = new LocationApi.Builder().build();
        locationApi.getLoc().enqueue(new Callback<Location>() {
            @Override
            public void onResponse(Call<Location> call, Response<Location> response) {
                cityView.setSelectedIndex(cities.indexOf(response.body().getCity()));
            }

            @Override
            public void onFailure(Call<Location> call, Throwable t) {
                //do nothing
            }

        });
    }

}
