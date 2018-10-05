package codeview.apps.dndcallblocker.listener;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.media.AudioManager;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.telephony.PhoneStateListener;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import com.android.internal.telephony.ITelephony;

import java.lang.reflect.Method;

import codeview.apps.dndcallblocker.R;
import codeview.apps.dndcallblocker.utils.AppConstants;
import codeview.apps.dndcallblocker.utils.PreferenceManager;

public class PhoneCallStateListener extends PhoneStateListener {

    private Context context;
    private static final String TAG=PhoneStateListener.class.getName();

    public PhoneCallStateListener(Context context){
        this.context = context;
    }

    @Override
    public void onCallStateChanged(int state, String incomingNumber) {
        Log.d(TAG, "Call state changed");

        switch (state) {

            case TelephonyManager.CALL_STATE_RINGING:
                Toast.makeText(context,"Call is coming",Toast.LENGTH_LONG).show();
//                String block_number = "9729042027";
                AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
                //Turn ON the mute
                if (audioManager != null) {
                    audioManager.setStreamMute(AudioManager.STREAM_RING, true);
                }

                TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                try {
                    Class telephonyMgrClass = Class.forName(telephonyManager.getClass().getName());
                    Method method = telephonyMgrClass.getDeclaredMethod("getITelephony");
                    method.setAccessible(true);
                    ITelephony telephonyService= (ITelephony) method.invoke(telephonyManager);
                    PreferenceManager.init(context);
                    boolean isBlockingOn=PreferenceManager.read(PreferenceManager.ENABLE_DND,false);
                    if(isBlockingOn){
                        telephonyService.silenceRinger();
                        telephonyService.endCall();
                        sendSMS(incomingNumber,"I am busy right now. Will call you back soon.");
                    }

                } catch (Exception e) {
                    Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();
                }
                //Turn OFF the mute
                if (audioManager != null) {
                    audioManager.setStreamMute(AudioManager.STREAM_RING, false);
                }
                break;

        }
        super.onCallStateChanged(state, incomingNumber);
    }

    private void sendSMS(String phoneNo, String msg) {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNo, null, msg, null, null);
            Log.d(TAG,"Sms sent");
        } catch (Exception ex) {
            Log.d(TAG,"Sms sending failed");
            ex.printStackTrace();
        }
    }

    private void showNotifications(String channelId,String textTitle, String textContent){
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, channelId)
                .setContentTitle(textTitle)
                .setContentText(textContent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = context.getString(R.string.calls_blocked);
            String description = context.getString(R.string.calls_blocked_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(AppConstants.CALLS_NOTIF_CHANNEL, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}

