package io.wumf.wumf.friends.preloading;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v4.content.CursorLoader;

import java.util.ArrayList;

import io.wumf.wumf.application.WumfApp;
import io.wumf.wumf.friends.Friend;
import io.wumf.wumf.util.Util;

/**
 * Created by max on 10.08.15.
 */
public class FriendsLoader extends CursorLoader {

    public FriendsLoader(Context context) {
        super(context, ContactsQuery.CONTENT_URI, ContactsQuery.PROJECTION,
                ContactsQuery.SELECTION, null, ContactsQuery.SORT_ORDER);
    }

    public static void onLoadFinished(android.support.v4.content.Loader<Cursor> loader,
                                      final Cursor data) {
        ArrayList<Friend> friends = new ArrayList<>();
        while (data.moveToNext()) {
            Friend friend = new Friend();
            friend.setPhone(Util.prepareNumber(data.getString(data.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))));
            friend.setUrl(data.getString(ContactsQuery.PHOTO_THUMBNAIL_DATA));
            friend.setName(data.getString(ContactsQuery.DISPLAY_NAME));
            friends.add(friend);
        }

        WumfApp.instance.setFriends(friends);

        //save(friends, memoryCommunicator);
    }

}
