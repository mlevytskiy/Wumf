package io.wumf.wumf.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.Date;

import co.moonmonkeylabs.realmrecyclerview.RealmRecyclerView;
import io.wumf.wumf.R;
import io.wumf.wumf.adapter.NewAppsAdapter;

/**
 * Created by max on 30.03.16.
 */
public class NewAppsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_apps, container, false);
        RealmRecyclerView recyclerView = (RealmRecyclerView) view.findViewById(R.id.realm_recycler_view);
        Spinner spinner = (Spinner) view.findViewById(R.id.spinner);
        fillSpinner(spinner, recyclerView);

        return view;
    }

    private void fillSpinner(Spinner spinner, final RealmRecyclerView recyclerView) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.new_apps_date));
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Date date = new Date();
                if (position == 0) {
                    date.setDate((date.getDate() - 1));
                } else if (position == 1) {
                    date.setDate((date.getDate() - 7));
                } else {
                    date.setMonth((date.getMonth() - 1));
                }
                recyclerView.setAdapter(new NewAppsAdapter(getContext(), date.getTime()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

}
