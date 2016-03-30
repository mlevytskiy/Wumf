package io.wumf.wumf.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.wumf.wumf.R;
import io.wumf.wumf.adapter.AllAppsAdapter;

/**
 * Created by max on 30.03.16.
 */
public class AllAppsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_apps, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycle_view);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1) {
            @Override
            public boolean supportsPredictiveItemAnimations() {
                return true;
            }
        });
        recyclerView.setAdapter(new AllAppsAdapter(getContext()));
        return view;
    }

}
