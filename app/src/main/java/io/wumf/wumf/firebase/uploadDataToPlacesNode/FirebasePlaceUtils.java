package io.wumf.wumf.firebase.uploadDataToPlacesNode;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import io.wumf.wumf.application.WumfApp;

/**
 * Created by max on 21.07.16.
 */
public class FirebasePlaceUtils {

    private static final DatabaseReference PHONES_REF = FirebaseDatabase.getInstance().getReference().child("places");

    private static String generatePlaceId(String country, String city) {
        return String.valueOf(country.hashCode()) + String.valueOf(city.hashCode());
    }

    public static void uploadMyInfo(String country, String city) {
        String placeId = generatePlaceId(country, city);
        PHONES_REF.child(placeId).child(WumfApp.instance.androidId).push();
    }

}
