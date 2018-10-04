package codeview.apps.dndcallblocker.receiver;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import codeview.apps.dndcallblocker.listener.PhoneCallStateListener;

/**
 * Created by Wijay on 2/10/2018.
 */

public class CallReceiver extends BroadcastReceiver {

    private final String TAG = CallReceiver.class.getName();

    @Override
    public void onReceive(Context context, Intent intent) {


        if (TelephonyManager.ACTION_PHONE_STATE_CHANGED.equals(intent.getAction()) &&
                intent.getStringExtra(TelephonyManager.EXTRA_STATE).equals(TelephonyManager.EXTRA_STATE_RINGING)) {

            String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
            String number = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
            Log.d(TAG, "number : " + number + " state : " + state);
            PhoneCallStateListener customPhoneListener = new PhoneCallStateListener(context);

            TelephonyManager telephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (telephony != null) {
                telephony.listen(customPhoneListener, PhoneStateListener.LISTEN_CALL_STATE);
            }else{
                Log.d(TAG, "onReceive: telephony manager is null");
            }
        }


    }

}
