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
import io.wumf.wumf.firebase.uploadDataToAppsNode.FirebaseAppsUtil;
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

    private static final String TAG = MyLocationActivity.class.getSimpleName();

    private NiceSpinner countryView;
    private NiceSpinner cityView;

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

        countryView = (NiceSpinner) findViewById(R.id.country);
        countryView.attachDataSource(countries);
        countryView.setSelectedIndex(countries.indexOf(application.userCountry));

        Log.i("MainActivity", "app.userCountry=" + application.userCountry);

        cityView = (NiceSpinner) findViewById(R.id.city);
        final List<String> cities = application.map.get(application.userCountry);
        cityView.attachDataSource(cities);

        LocationApi locationApi = new LocationApi.Builder().build();
        locationApi.getLoc().enqueue(new Callback<Location>() {
            @Override
            public void onResponse(Call<Location> call, Response<Location> response) {
                application.userCity = response.body().getCity();
                Log.i(TAG, "userCity=" + application.userCity);
                if (TextUtils.isEmpty(application.userCity)) {
                    application.userCity = cities.get(0);
                    cityView.setSelectedIndex(0);
                } else {
                    cityView.setSelectedIndex(cities.indexOf(application.userCity));
                }
                FirebaseAppsUtil.upload(application.userCountry, application.userCity);
                FirebasePlaceUtils.uploadMyInfo(application.userCountry, application.userCity);
            }

            @Override
            public void onFailure(Call<Location> call, Throwable t) {
                //do nothing
            }

        });
    }

    public void onClickSearchApps(View view) {
        startActivity(new Intent(this, AppsActivity.class).putExtra(AppsActivity.PLACE_ID_KEY, getPickedPlaceId()));
    }

    private String getPickedPlaceId() {
        return FirebasePlaceUtils.generatePlaceId(countryView.getText().toString(), cityView.getText().toString());
    }

}
