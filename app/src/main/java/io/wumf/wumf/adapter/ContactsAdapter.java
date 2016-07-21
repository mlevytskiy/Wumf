package io.wumf.wumf.adapter;

import android.content.Context;
import android.view.ViewGroup;

import io.realm.Realm;
import io.wumf.wumf.realmObject.Contact;
import io.wumf.wumf.viewHolder.ContactViewHolder;

/**
 * Created by max on 07.07.16.
 */
public class ContactsAdapter extends AnyRealmAdapter<Contact, ContactViewHolder> {

    public ContactsAdapter(Context context) {
        super(context, Realm.getDefaultInstance().where(Contact.class).findAll());
    }

    @Override
    public ContactViewHolder onCreateRealmViewHolder(ViewGroup viewGroup, int i) {
        return new ContactViewHolder(viewGroup);
    }

}
