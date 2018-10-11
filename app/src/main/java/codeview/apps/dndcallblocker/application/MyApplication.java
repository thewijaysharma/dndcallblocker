package codeview.apps.dndcallblocker.application;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.IntentFilter;
import android.os.Build;

import com.google.android.gms.ads.MobileAds;

import codeview.apps.dndcallblocker.R;
import codeview.apps.dndcallblocker.database.BlacklistDatabase;
import codeview.apps.dndcallblocker.receiver.CallReceiver;
import codeview.apps.dndcallblocker.utils.AppConstants;
import codeview.apps.dndcallblocker.utils.PreferenceManager;

/**
 * Created by Wijay on 7/10/2018.
 */

public class MyApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        PreferenceManager.init(this);
        registerBroadcast();
        createNotificationChannels();
        MobileAds.initialize(this,getString(R.string.admob_id));
    }

    private void createNotificationChannels() {
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel notificationChannel=new NotificationChannel(AppConstants.BLOCKED_NOTIF_CHANNEL,
                    "Calls and SMS Blocked",NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.setDescription("These notifications are generated when a call or SMS is blocked.");

            NotificationManager manager=getSystemService(NotificationManager.class);
            if (manager != null) {
                manager.createNotificationChannel(notificationChannel);
            }
        }
    }

    private void registerBroadcast() {
        CallReceiver callReceiver=new CallReceiver();
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction("android.intent.action.PHONE_STATE");
        intentFilter.addAction("android.provider.Telephony.SMS_RECEIVED");
        intentFilter.setPriority(AppConstants.PRIORITY);
        registerReceiver(callReceiver,intentFilter);
    }
}
