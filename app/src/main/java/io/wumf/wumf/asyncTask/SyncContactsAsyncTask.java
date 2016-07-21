package io.wumf.wumf.asyncTask;

import android.os.AsyncTask;
import android.widget.Toast;

import com.github.tamir7.contacts.Contact;
import com.github.tamir7.contacts.Contacts;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import io.wumf.wumf.application.WumfApp;
import io.wumf.wumf.util.ContactUtils;

/**
 * Created by max on 07.07.16.
 */
public class SyncContactsAsyncTask extends AsyncTask<Void, Void, List<Contact>> {

    private List<io.wumf.wumf.realmObject.Contact> realmContacts;
    private RealmResults<io.wumf.wumf.realmObject.Contact> realmResults;
    private List<io.wumf.wumf.realmObject.Contact> newOrChangedRealmObjects = new ArrayList<>();

    protected void onPreExecute() {
        realmResults = Realm.getDefaultInstance().where(io.wumf.wumf.realmObject.Contact.class).findAll();
        realmContacts = new ArrayList<>(realmResults);
    }

    @Override
    protected List<Contact> doInBackground(Void... params) {
        return Contacts.getQuery()
                .include(Contact.Field.DisplayName, Contact.Field.Email, Contact.Field.PhotoUri, Contact.Field.PhoneNumber)
                .find();
    }

    @Override
    protected void onPostExecute(List<Contact> contacts) {
        super.onPostExecute(contacts);

        ContactUtils.syncWithRealm(contacts, realmContacts, newOrChangedRealmObjects);

        Realm.getDefaultInstance().beginTransaction();
        if (!realmContacts.isEmpty()) {
            for (io.wumf.wumf.realmObject.Contact contact : realmContacts) {
                contact.removeFromRealm();
            }
        }
        Realm.getDefaultInstance().copyToRealmOrUpdate(newOrChangedRealmObjects);
        Realm.getDefaultInstance().commitTransaction();

        int size = Realm.getDefaultInstance().where(io.wumf.wumf.realmObject.Contact.class).findAll().size();

        Toast.makeText(WumfApp.instance, "contacts size=" + size, Toast.LENGTH_LONG).show();

    }

}
