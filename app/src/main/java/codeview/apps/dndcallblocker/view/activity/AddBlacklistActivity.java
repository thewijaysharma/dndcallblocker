package codeview.apps.dndcallblocker.view.activity;
import android.os.Bundle;

import codeview.apps.dndcallblocker.R;

public class AddBlacklistActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_blacklist);
        showActionBar();
    }
}
