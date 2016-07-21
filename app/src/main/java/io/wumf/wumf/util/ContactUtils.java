package io.wumf.wumf.util;

import android.text.TextUtils;

import com.github.tamir7.contacts.Contact;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by max on 07.07.16.
 */
public class ContactUtils {

    public static void syncWithRealm(List<Contact> contacts,
                                     List<io.wumf.wumf.realmObject.Contact> realmContacts,
                                     List<io.wumf.wumf.realmObject.Contact> newOrChangedRealmContacts) {

        for (Contact contact : contacts) {
            io.wumf.wumf.realmObject.Contact realmContact = getRealmContact(contact, realmContacts);
            if (realmContact == null) {
                io.wumf.wumf.realmObject.Contact newRealmContact = create(contact);
                if (newRealmContact != null) {
                    newOrChangedRealmContacts.add(newRealmContact);
                } else {
                    //do nothing
                }
            } else {
                io.wumf.wumf.realmObject.Contact newRealmContact = create(contact);
                if (newRealmContact != null) {
                    String url = realmContact.getBigIcon();
                    if (TextUtils.isEmpty(url) && !contact.getPhotoUris().isEmpty()) {
                        newOrChangedRealmContacts.add(newRealmContact);
                    } else {
                        //do nothing
                    }
                } else {
                    realmContacts.add(realmContact); //need remove from realm
                }
            }
        }

    }

    private static io.wumf.wumf.realmObject.Contact create(Contact contact) {
        if (contact.getPhoneNumbers().isEmpty()) {
            return null;
        }

        io.wumf.wumf.realmObject.Contact realmContact = new io.wumf.wumf.realmObject.Contact();
        realmContact.setId(contact.getId());
        realmContact.setPhone(contact.getPhoneNumbers().get(0).getNormalizedNumber());
        realmContact.setBigIcon( contact.getPhotoUris().isEmpty() ? null : contact.getPhotoUris().get(0) );
        realmContact.setName(contact.getDisplayNames().get(0));

        return realmContact;
    }

    private static io.wumf.wumf.realmObject.Contact getRealmContact(Contact contact, List<io.wumf.wumf.realmObject.Contact> realmContacts) {

        for (io.wumf.wumf.realmObject.Contact realmContact : new ArrayList<>(realmContacts)) {
            if (realmContact.getId() == contact.getId()) {
                realmContacts.remove(realmContact);
                return realmContact;
            }
        }

        return null;

    }

}
