package io.wumf.wumf.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import co.moonmonkeylabs.realmrecyclerview.RealmRecyclerView;
import io.wumf.wumf.R;
import io.wumf.wumf.adapter.AllAppsAdapter;

/**
 * Created by max on 30.03.16.
 */
public class AllAppsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_apps, container, false);
        RealmRecyclerView recyclerView = (RealmRecyclerView) view.findViewById(R.id.realm_recycler_view);
        recyclerView.setAdapter(new AllAppsAdapter(getContext()));
        return view;
    }

}
