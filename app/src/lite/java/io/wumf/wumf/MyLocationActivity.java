package io.wumf.wumf;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import org.angmarch.views.NiceSpinner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.wumf.wumf.activity.common.PrepareDataActivity;
import io.wumf.wumf.application.WumfApp;
import io.wumf.wumf.firebase.uploadDataToPlacesNode.FirebasePlaceUtils;
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

        final WumfApp application = (WumfApp) getApplication();
        List<String> countries = new ArrayList<>();
        for (Map.Entry<String, List<String>> entry : application.map.entrySet()) {
            String country = entry.getKey();
            if ( !TextUtils.isEmpty(country) ) {
                countries.add(country);
            }
        }

        NiceSpinner country = (NiceSpinner) findViewById(R.id.country);
        country.attachDataSource(countries);
        country.setSelectedIndex(countries.indexOf(application.userCountry));

        Log.i("MainActivity", "app.userCountry=" + application.userCountry);

        final NiceSpinner cityView = (NiceSpinner) findViewById(R.id.city);
        final List<String> cities = application.map.get(application.userCountry);
        cityView.attachDataSource(cities);

        LocationApi locationApi = new LocationApi.Builder().build();
        locationApi.getLoc().enqueue(new Callback<Location>() {
            @Override
            public void onResponse(Call<Location> call, Response<Location> response) {
                application.userCity = response.body().getCity();
                cityView.setSelectedIndex(cities.indexOf(application.userCity));
                FirebasePlaceUtils.uploadMyInfo(application.userCountry, application.userCity);
            }

            @Override
            public void onFailure(Call<Location> call, Throwable t) {
                //do nothing
            }

        });
    }

    public void onClickSearchApps(View view) {
        String city = WumfApp.instance.userCity;
        String country = WumfApp.instance.userCountry;
        String placeId = FirebasePlaceUtils.generatePlaceId(country, city);
        startActivity(new Intent(this, AppsActivity.class).putExtra(AppsActivity.PLACE_ID_KEY, placeId));
    }

}
