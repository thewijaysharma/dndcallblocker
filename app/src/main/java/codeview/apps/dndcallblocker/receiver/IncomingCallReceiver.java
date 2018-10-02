package codeview.apps.dndcallblocker.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Wijay on 2/10/2018.
 */

public class IncomingCallReceiver extends BroadcastReceiver {
    private final String TAG=IncomingCallReceiver.class.getName();
    @Override
    public void onReceive(Context context, Intent intent) {
        String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
        String number = intent.getExtras().getString(TelephonyManager.EXTRA_INCOMING_NUMBER);
        Log.d(TAG, "number : "+number+" state : "+state );
    }
}
