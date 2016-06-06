package io.wumf.wumf.util;

import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;

import java.util.HashMap;
import java.util.Map;

import io.wumf.wumf.util.phoneNumberDetectorImpl.PhoneNumberProvider;

/**
 * Created by max on 26.05.16.
 */
public class PhoneNumberDetector {

    public static Map<PhoneNumberProvider, String> getPhones(Context context) {
        Map<PhoneNumberProvider, String> phones = new HashMap<>();
        if (ContextCompat.checkSelfPermission(context,
                Manifest.permission.GET_ACCOUNTS)
                != PackageManager.PERMISSION_GRANTED) {
            System.out.print("");
        } else {
            detectPhoneNumberByAccounts(phones, context);
            detectPhoneNumberBySim1(phones, context);
            detectPhoneNumberBySim2(phones, context);
        }
        return phones;
    }

    private static void detectPhoneNumberByAccounts(Map<PhoneNumberProvider, String> phones, Context context) {
        Account[] accounts = AccountManager.get(context).getAccounts();
        for (Account ac : accounts) {
            switch (ac.type) {
                case "com.viber.voip" :
                    phones.put(PhoneNumberProvider.VIBER, ac.name);
                    break;
            }
        }

    }

    private static void detectPhoneNumberBySim1(Map<PhoneNumberProvider, String> phones, Context context) {
        TelephonyManager tMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        phones.put(PhoneNumberProvider.SIM_1, tMgr.getLine1Number());
    }

    private static void detectPhoneNumberBySim2(Map<PhoneNumberProvider, String> phones, Context context) {
        //do nothing
    }


}
