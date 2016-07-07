package io.wumf.wumf.otto.event;

import java.util.List;

import io.wumf.wumf.contacts.Contact;

/**
 * Created by max on 24.05.16.
 */
public class LoadContactsFinishedEvent {

    public final List<Contact> friends;

    public LoadContactsFinishedEvent(List<Contact> friends) {
        this.friends = friends;
    }

}
