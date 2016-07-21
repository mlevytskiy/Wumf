package io.wumf.wumf.viewHolder;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import io.wumf.wumf.R;
import io.wumf.wumf.realmObject.Contact;

/**
 * Created by max on 07.07.16.
 */
public class ContactViewHolder extends AnyRealmViewHolder<Contact> {

    private TextView phone;
    private TextView iconIsEmpty;
    private ImageView icon;

    public ContactViewHolder(ViewGroup parent) {
        super(parent, R.layout.viewholder_contact);
        phone = (TextView) itemView.findViewById(R.id.phone);
        iconIsEmpty = (TextView) itemView.findViewById(R.id.iconIsEmpty);
        icon = (ImageView) itemView.findViewById(R.id.icon);
    }

    @Override
    public void bind(Contact item) {
        String uri = item.getBigIcon();
        iconIsEmpty.setText(String.valueOf(TextUtils.isEmpty(uri)));
        icon.setImageBitmap(TextUtils.isEmpty(uri) ? null :
                loadContactPhoto(itemView.getContext().getContentResolver(), item.getId()));
        phone.setText(item.getPhone());
    }

    private static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            return null;
        }
    }

    private static Bitmap loadContactPhoto(ContentResolver cr, long  id) {
        Uri uri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, id);
        InputStream input = ContactsContract.Contacts.openContactPhotoInputStream(cr, uri);
        if (input == null) {
            return null;
        }
        return BitmapFactory.decodeStream(input);
    }

}
