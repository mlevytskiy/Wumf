package io.wumf.wumf.otto.event;

import java.util.List;
import java.util.Map;

import io.wumf.wumf.contacts.Contact;
import io.wumf.wumf.pojo.App;

/**
 * Created by max on 05.07.16.
 */
public class FirebaseLoadFriendsFinishedEvent {

    public final Map<Contact, List<App>> map;

    public FirebaseLoadFriendsFinishedEvent(Map<Contact, List<App>> map) {
        this.map = map;
    }

}
