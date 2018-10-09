package codeview.apps.dndcallblocker.application;

import android.app.Application;

import com.google.android.gms.ads.MobileAds;

import codeview.apps.dndcallblocker.R;
import codeview.apps.dndcallblocker.utils.PreferenceManager;

/**
 * Created by Wijay on 7/10/2018.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        PreferenceManager.init(this);
        MobileAds.initialize(this,getString(R.string.admob_id));
    }
}
