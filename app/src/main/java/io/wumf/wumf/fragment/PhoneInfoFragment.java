package io.wumf.wumf.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Map;

import io.wumf.wumf.R;
import io.wumf.wumf.application.WumfApp;
import io.wumf.wumf.util.CpuInfo;
import io.wumf.wumf.util.DeviceName;
import io.wumf.wumf.util.phoneNumberDetectorImpl.PhoneNumberProvider;

/**
 * Created by max on 28.03.16.
 */
public class PhoneInfoFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_phone_info, container, false);
        final TextView phoneModel = (TextView) view.findViewById(R.id.phone_model);
        final TextView phoneName = (TextView) view.findViewById(R.id.phone_name);
        final TextView manufactureTextView = (TextView) view.findViewById(R.id.manufacture);
        TextView phoneNumber = (TextView) view.findViewById(R.id.phone_number);
        phoneNumber.setText(phonesToString(WumfApp.instance.phones));

        DeviceName.with(getContext()).request(new DeviceName.Callback() {

            @Override
            public void onFinished(DeviceName.DeviceInfo info, Exception error) {
                String manufacturer = info.manufacturer;  // "Samsung"
                String name = info.marketName;            // "Galaxy S7 Edge"
                String model = info.model;                // "SAMSUNG-SM-G935A"
                String codename = info.codename;          // "hero2lte"
                String deviceName = info.getName();       // "Galaxy S7 Edge"
                manufactureTextView.setText(manufacturer);
                phoneName.setText(name);
                phoneModel.setText(model);

            }
        });
        TextView cpuInfo = (TextView) view.findViewById(R.id.cpu_info);
        cpuInfo.setText(CpuInfo.getInfo());
        return view;
    }

    private String phonesToString(Map<PhoneNumberProvider, String> map) {
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<PhoneNumberProvider, String> entry : map.entrySet()) {
            builder.append(entry.getKey());
            builder.append(':');
            builder.append(entry.getValue());
            builder.append('\n');
        }
        return builder.toString();
    }

}
