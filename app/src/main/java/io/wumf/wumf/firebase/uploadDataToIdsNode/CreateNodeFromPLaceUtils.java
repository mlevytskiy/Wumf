package io.wumf.wumf.firebase.uploadDataToIdsNode;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import io.wumf.wumf.pojo.App;

/**
 * Created by max on 21.07.16.
 */
public class CreateNodeFromPLaceUtils {

    private static final DatabaseReference PHONES_REF = FirebaseDatabase.getInstance().getReference().child("places");

    public static String generateId(String country, String city) {
        return String.valueOf(country.hashCode()) + String.valueOf(city.hashCode());
    }

    public static void saveMyApps(List<App> apps) {

    }



}
