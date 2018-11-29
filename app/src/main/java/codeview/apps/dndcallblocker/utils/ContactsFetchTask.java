package codeview.apps.dndcallblocker.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.provider.ContactsContract;

import java.util.ArrayList;
import java.util.List;

import codeview.apps.dndcallblocker.model.Contact;

/**
 * Created by keyss on 23/5/18.
 */

public class ContactsFetchTask extends AsyncTask<Context, Void, Void> {

    private GetContactsCallback callback;
    private List<Contact> contactList;

    public ContactsFetchTask(GetContactsCallback callback) {
        this.callback = callback;
        this.contactList = new ArrayList<>();
    }

    @Override
    protected Void doInBackground(Context... contexts) {

        ContentResolver resolver = contexts[0].getContentResolver();
        Cursor cursor = resolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, ContactsContract.Contacts.DISPLAY_NAME);
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                int hasPhone = cursor.getInt(cursor.getColumnIndex(
                        ContactsContract.Contacts.HAS_PHONE_NUMBER)); //returns >0 if the contact has a phone number
                String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));

                if (hasPhone > 0) {
                    Cursor pCur = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ? ", new String[]{id}, null);

                    while (pCur != null && pCur.moveToNext()) {
                        String phone = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                        boolean isUnique = true;
                        for (Contact c : contactList) {
                            if (c.getPhone().contains(phone))
                                isUnique = false;
                        }

                        if (isUnique) {

                            if (name == null || name.isEmpty()) {
                                contactList.add(new Contact("Unnamed", phone, false));
                            } else {
                                contactList.add(new Contact(name, phone, false));
                            }
                        }

                    }
                    pCur.close();

                }
            }
            cursor.close();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {

        callback.onContactsReceived(contactList);

        super.onPostExecute(aVoid);
    }


    public interface GetContactsCallback {
        void onContactsReceived(List<Contact> contactList);
    }
}
