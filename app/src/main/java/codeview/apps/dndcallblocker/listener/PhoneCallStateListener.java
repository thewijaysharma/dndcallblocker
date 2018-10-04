package codeview.apps.dndcallblocker.listener;

import android.content.Context;
import android.media.AudioManager;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import com.android.internal.telephony.ITelephony;

import java.lang.reflect.Method;

public class PhoneCallStateListener extends PhoneStateListener {

    private Context context;

    public PhoneCallStateListener(Context context){
        this.context = context;
    }

    @Override
    public void onCallStateChanged(int state, String incomingNumber) {

        switch (state) {

            case TelephonyManager.CALL_STATE_RINGING:
                Toast.makeText(context,"Call is coming",Toast.LENGTH_LONG).show();
                String block_number = "9729042027";
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
                    ITelephony telephonyService;
                    //Checking incoming call number
                    System.out.println("Call "+block_number);

                    if (incomingNumber.contains(block_number)) {
                        //telephonyService.silenceRinger();//Security exception problem
                        telephonyService = (ITelephony) method.invoke(telephonyManager);
                        telephonyService.silenceRinger();
                        telephonyService.endCall();
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
}

