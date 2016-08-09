package io.wumf.wumf;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import org.angmarch.views.NiceSpinner;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Pattern;

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
    private List<String> cities;

    @Override
    protected void onCreateAfterDataPreparation(Bundle savedInstanceState) {
        setContentView(R.layout.activity_my_location);

        final WumfApp application = (WumfApp) getApplication();
        final List<String> countries = getCountries();

        countryView = (NiceSpinner) findViewById(R.id.country);
        countryView.attachDataSource(countries);

        countryView.addOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                try {
                    cities = Arrays.asList(findCities(countries.get(position)));
                    if (cities.isEmpty()) {
                        Toast.makeText(MyLocationActivity.this, "happened some error", Toast.LENGTH_LONG).show();
                    } else {
                        cityView.attachDataSource(cities);
                    }
                } catch (IOException e) {
                    Log.e(TAG, e.getMessage());
                }

            }

        });

        cityView = (NiceSpinner) findViewById(R.id.city);
        if (TextUtils.isEmpty(application.userCountry)) {
            //do nothing
        } else {
            countryView.setSelectedIndex(countries.indexOf(application.userCountry));

            try {
                cities = Arrays.asList(findCities(application.userCountry));
                cityView.attachDataSource(cities);
            } catch (IOException e) {
                Log.e(TAG, e.getMessage());
            }

        }

        LocationApi locationApi = new LocationApi.Builder().build();
        locationApi.getLoc().enqueue(new Callback<Location>() {
            @Override
            public void onResponse(Call<Location> call, Response<Location> response) {
                application.userCity = response.body().getCity();
                if (TextUtils.isEmpty(application.userCountry)) {
                    application.userCountry = CountriesCodes.map.get(response.body().getCountry());
                    countryView.setSelectedIndex(countries.indexOf(application.userCountry));

                    try {
                        cities = Arrays.asList( findCities(application.userCountry) );
                        cityView.attachDataSource(cities);
                    } catch (IOException e) {
                        Log.e(TAG, e.getMessage());
                    }

                } else {
                    //do nothing
                }
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
        String[] strs = getPickedPlaceId();
        startActivity(new Intent(this, AppsActivity.class)
                .putExtra(AppsActivity.PLACE_ID_KEY, strs[2])
                .putExtra(AppsActivity.COUNTRY_KEY, strs[0])
                .putExtra(AppsActivity.CITY_KEY, strs[1]));
    }

    private List<String> getCountries() {
        return new ArrayList<>(new HashSet<>(CountriesCodes.map.values()));
    }

    private String[] getPickedPlaceId() {
        String[] strs = new String[3];
        strs[0] = countryView.getText().toString();
        strs[1] = cityView.getText().toString();
        strs[2] = FirebasePlaceUtils.generatePlaceId(strs[0], strs[1]);
        return strs;
    }

    private String[] findCities(String country) throws IOException {
        String[] files = getAssets().list("");
        for (String fileName : files) {
            if (TextUtils.equals(fileName, country)) {
                String citiesStr = readAssetsFileAsString(fileName);
                if (TextUtils.isEmpty(citiesStr)) {
                    return new String[0];
                }
                if (citiesStr.contains("|")) {
                    String[] result = citiesStr.split(Pattern.quote("|"));
                    return result;
                } else {
                    return new String[] { citiesStr };
                }
            }
        }
        return new String[0];
    }

    private String readAssetsFileAsString(String fileName) { //only for small text file
        AssetManager assetManager = getAssets();
        InputStream input;
        String text = null;

        try {
            input = assetManager.open(fileName);

            int size = input.available();
            byte[] buffer = new byte[size];
            input.read(buffer);
            input.close();

            // byte buffer into a string
            text = new String(buffer);

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return text;
    }
}
