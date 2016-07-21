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

        NiceSpinner city = (NiceSpinner) findViewById(R.id.city);
        city.attachDataSource(app.map.get(app.userCountry));
    }

}
