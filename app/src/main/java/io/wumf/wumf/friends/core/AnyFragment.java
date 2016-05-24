package io.wumf.wumf.friends.core;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

/**
 * Created by max on 30.05.15.
 */
public class AnyFragment extends Fragment {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public static AnyFragment current;

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        current = this;
    }

    public void onStartWithShowHomeUp() {
        super.onStart();
        ((HomeUp) getActivity()).show();
    }

    public void updateText() {

    }

    public void onStartWithHideHomeUp() {
        super.onStart();
        ((HomeUp) getActivity()).hide();
    }

    protected final void back() {
        getActivity().getSupportFragmentManager().popBackStack();
    }

    public void startFragment(AnyFragment fragment) {
        //do nothing
    }

    protected FragmentTransaction getFragmentTransaction(AnyFragment fragment) {
        return null;
    }

    protected final String getMyPhone() {
        return null;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    public void onCreateOptionsMenuEmpty(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return true;
    }

}
