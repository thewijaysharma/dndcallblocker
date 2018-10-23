package codeview.apps.dndcallblocker.utils;

import android.app.Activity;
import android.app.Notification;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Locale;
import java.util.stream.Stream;

import codeview.apps.dndcallblocker.R;

public class AppUtils {


    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        if (imm != null)
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public interface TwoButtonAlertListener {

        void onPositiveClicked();

        void onNegativeClicked();

    }

    public static void createTwoButtonAlertWithEvent(String message, String title, String positiveText, String negativeText, Context context, final TwoButtonAlertListener callback) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AlertDialogStyle);
        builder.setMessage(message)
                .setTitle(title)
                .setCancelable(false)
                .setPositiveButton(positiveText, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        callback.onPositiveClicked();
                    }
                })
                .setNegativeButton(negativeText, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        callback.onNegativeClicked();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public static void vibratePhone(Context context){
        Vibrator vibe = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        if (vibe != null) {
            vibe.vibrate(90);
        }
    }

    public static void showNotification(Context context,String title, String description,String channelID){
        if(PreferenceManager.read(PreferenceManager.IS_SHOW_NOTIF_ON,false)){
            NotificationManagerCompat notifManagerCompat=NotificationManagerCompat.from(context);
            Notification notification=new NotificationCompat.Builder(context,channelID)
                    .setSmallIcon(R.drawable.ic_do_not_disturb)
                    .setContentTitle(title)
                    .setContentText(description)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setCategory(NotificationCompat.CATEGORY_CALL)
                    .build();
            notifManagerCompat.notify(1,notification);
        }
    }

    public static String getCurrentTime(){
        DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm", Locale.US);
        return df.format(Calendar.getInstance().getTime());
    }
}
