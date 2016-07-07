package io.wumf.wumf.contacts;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Handler;
import android.provider.ContactsContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by max on 06.07.16.
 */
public enum ContactsModule {

    /* Sample
     ContactsModule.INSTANCE.sync(this);
        ContactsModule.INSTANCE.afterSync(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(App.this, "success size = " +
                        ContactsModule.INSTANCE.getContacts().size(), Toast.LENGTH_LONG).show();
            }
        });
     */

    INSTANCE;

    private Handler handler = new Handler();
    private Runnable afterSync;
    private List<Contact> contacts = new ArrayList<>();

    public void sync(final Context context) {
        AsyncTask.THREAD_POOL_EXECUTOR.execute(new Runnable() {
            @Override
            public void run() {
                Cursor phones = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null,null, null);
                while (phones.moveToNext()) {
                    String name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    contacts.add(new Contact(name, phoneNumber));
                }
                phones.close();
                handler.post(afterSync);
            }
        });
    }

    public void afterSync(Runnable value) {
        afterSync = value;
    }

    public List<Contact> getContacts() {
        return contacts;
    }

}
