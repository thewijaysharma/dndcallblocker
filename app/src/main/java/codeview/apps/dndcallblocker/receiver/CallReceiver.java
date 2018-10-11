package codeview.apps.dndcallblocker.receiver;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;
import android.util.Log;

import codeview.apps.dndcallblocker.listener.PhoneCallStateListener;
import codeview.apps.dndcallblocker.utils.AppConstants;
import codeview.apps.dndcallblocker.utils.AppUtils;
import codeview.apps.dndcallblocker.utils.PreferenceManager;

/**
 * Created by Wijay on 2/10/2018.
 */

public class CallReceiver extends BroadcastReceiver {

    private final String TAG = CallReceiver.class.getName();

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.d(TAG, "onReceive is called");

        if (TelephonyManager.ACTION_PHONE_STATE_CHANGED.equals(intent.getAction()) &&
                intent.getStringExtra(TelephonyManager.EXTRA_STATE).equals(TelephonyManager.EXTRA_STATE_RINGING)) {
            Log.d(TAG, "device is ringing");

            String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
            String number = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
            Log.d(TAG, "number : " + number + " state : " + state);
            PhoneCallStateListener customPhoneListener = new PhoneCallStateListener(context);

            TelephonyManager telephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (telephony != null) {
                telephony.listen(customPhoneListener, PhoneStateListener.LISTEN_CALL_STATE);
            } else {
                Log.d(TAG, "onReceive: telephony manager is null");
            }
        } else if (intent.getAction() != null && intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")) {
            boolean isDndEnabled = PreferenceManager.read(PreferenceManager.IS_DND_ENABLED, false);
            boolean isBlockSmsOn = PreferenceManager.read(PreferenceManager.IS_BLOCK_SMS_ON, false);
            Bundle bundle = intent.getExtras();
            String phoneNumber= "";
            if (bundle != null) {
                final Object[] pdusObj = (Object[]) bundle.get("pdus");

                if (pdusObj != null) {
                    for (Object aPdusObj : pdusObj) {
                        SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) aPdusObj);
                        phoneNumber = currentMessage.getDisplayOriginatingAddress();
                    }
                }
            }



            if (isDndEnabled && isBlockSmsOn) {
                abortBroadcast();
                AppUtils.showNotification(context, "DND Mode", "SMS blocked from "+phoneNumber, AppConstants.BLOCKED_NOTIF_CHANNEL);
            }
        }


    }

}
