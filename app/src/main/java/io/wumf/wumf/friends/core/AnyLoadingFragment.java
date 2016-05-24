package io.wumf.wumf.friends.core;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import io.wumf.wumf.R;

/**
 * Created by max on 30.05.15.
 */
public abstract class AnyLoadingFragment extends AnyFragment {

    private TextView textView;
    private Button checkInternet;
    protected LoadingState state;

    protected abstract void load();
    protected abstract void canceled();

    protected final void changeState(LoadingState state, String desc) {
        this.state = state;
        if (state == LoadingState.error) {
            checkInternet.setVisibility(View.VISIBLE);
            checkInternet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                }
            });
        } else {
            checkInternet.setVisibility(View.INVISIBLE);
        }
        textView.setText(state.text);
    }

    protected final void changeState(LoadingState state) {
        changeState(state, null);
    }

    public void onStart() {
            super.onStart();
            changeState(LoadingState.loading);
            load();
    }

    public void onStartWithShowHomeUp() {
        super.onStart();
        ((HomeUp) getActivity()).show();
        changeState(LoadingState.loading);
        load();
    }

    public void onStartWithHideHomeUp() {
        super.onStart();
        ((HomeUp) getActivity()).hide();
        changeState(LoadingState.loading);
        load();
    }

    public void onStop() {
        changeState(LoadingState.canceled);
        canceled();
        super.onStop();
    }

    public void startFragment(AnyFragment fragment) {
        back();
        super.startFragment(fragment);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_loading, container, false);
        textView = (TextView) view.findViewById(R.id.text_view);
        checkInternet = (Button) view.findViewById(R.id.check_internet);
        return view;
    }

}
