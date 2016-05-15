package io.wumf.wumf.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import co.moonmonkeylabs.realmrecyclerview.RealmRecyclerView;
import io.wumf.wumf.R;
import io.wumf.wumf.adapter.RemovedAppListAdapter;

/**
 * Created by max on 15.05.16.
 */
public class RemovedAppListFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_app_list, container, false);
        final RealmRecyclerView recyclerView = (RealmRecyclerView) view.findViewById(R.id.realm_recycler_view);
        recyclerView.setAdapter(new RemovedAppListAdapter(getContext()));
        return view;
    }

}
