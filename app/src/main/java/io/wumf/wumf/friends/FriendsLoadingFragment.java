package io.wumf.wumf.friends;

import java.util.List;

import io.wumf.wumf.application.WumfApp;
import io.wumf.wumf.friends.core.AnyLoadingFragment;
import io.wumf.wumf.friends.core.LoadingState;

/**
 * Created by max on 20.06.15.
 */
public class FriendsLoadingFragment extends AnyLoadingFragment {

    public void onStart() {
        super.onStartWithShowHomeUp();
        List<Friend> friends = WumfApp.instance.getFriends();
        load(friends);
    }

    @Override
    protected void load() {
        //doNothing
    }

    private void save(final List<Friend> friends) {
        //save in memory
    }

    private void load(final List<Friend> friends) {
        //load from internet more info
    }

    @Override
    protected void canceled() {
        changeState(LoadingState.canceled);
    }

}
