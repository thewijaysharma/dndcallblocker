package codeview.apps.dndcallblocker.view.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputFilter;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.List;

import codeview.apps.dndcallblocker.R;
import codeview.apps.dndcallblocker.model.LogModel;
import codeview.apps.dndcallblocker.utils.AppUtils;
import codeview.apps.dndcallblocker.utils.PreferenceManager;
import codeview.apps.dndcallblocker.view.adapter.CallLogsAdapter;
import codeview.apps.dndcallblocker.view_model.LogsViewModel;

public class MainActivity extends BaseActivity {

    private ImageButton dndButton;
    private TextView blockingStatus;
    private View parentView;
    private View bottomSheetLayout;
    private BottomSheetBehavior sheetBehavior;
    private ImageView arrowUpDown;
    private View sheetTopLayout;
    private RecyclerView logsRecycler;
    private AdView adView;
    private ImageButton proButton;
    private View createBlacklist;
    private Switch blockWithSmsSwitch, showNotifSwitch, blockSMSSwitch;
    private boolean isBlockingEnabled;
    private LogsViewModel logsViewModel;
    private CallLogsAdapter callLogsAdapter;
    private TextView blockCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initControl();
        setViews();
        loadAds();

        dndButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleBlockingMode();
            }
        });

        sheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_HIDDEN:
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED: {
                    }
                    break;
                    case BottomSheetBehavior.STATE_COLLAPSED: {
                    }
                    break;
                    case BottomSheetBehavior.STATE_DRAGGING:
                        break;
                    case BottomSheetBehavior.STATE_SETTLING:
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                arrowUpDown.setRotation(slideOffset * 180);

            }
        });

        showNotifSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isPressed()) {
                    if (isChecked) {
                        PreferenceManager.write(PreferenceManager.IS_SHOW_NOTIF_ON, true);
                    } else {
                        PreferenceManager.write(PreferenceManager.IS_SHOW_NOTIF_ON, false);
                    }
                }
            }
        });

        blockSMSSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isPressed()) {
                    if (isChecked) {
                        PreferenceManager.write(PreferenceManager.IS_BLOCK_SMS_ON, true);
                    } else {
                        PreferenceManager.write(PreferenceManager.IS_BLOCK_SMS_ON, false);
                    }
                }
            }
        });

        blockWithSmsSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isPressed()) {
                    if (isChecked) {
                        showDialogForMessage();
                    } else {
                        PreferenceManager.write(PreferenceManager.IS_BLOCK_WITH_SMS_ON, false);
                    }
                }
            }
        });


        createBlacklist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ShowBlacklistActivity.class);
                startActivity(intent);
            }
        });

        proButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, UpgradeToProActivity.class);
                startActivity(intent);
            }
        });

        sheetTopLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleBottomSheet();
            }
        });
    }

    private void setViews() {
        if (isBlockingEnabled) {
            dndButton.setImageResource(R.drawable.green_power_button);
            blockingStatus.setText("Blocking mode is enabled");
        } else {
            dndButton.setImageResource(R.drawable.red_power_button);
            blockingStatus.setText("Blocking mode is disabled");
        }

        if (PreferenceManager.read(PreferenceManager.IS_BLOCK_WITH_SMS_ON, false)) {
            blockWithSmsSwitch.setChecked(true);
        }
        if (PreferenceManager.read(PreferenceManager.IS_BLOCK_SMS_ON, false)) {
            blockSMSSwitch.setChecked(true);
        }
        if (PreferenceManager.read(PreferenceManager.IS_SHOW_NOTIF_ON, false)) {
            showNotifSwitch.setChecked(true);
        }

        logsViewModel.getAllLogs().observe(this, new Observer<List<LogModel>>() {
            @Override
            public void onChanged(@Nullable List<LogModel> logModels) {
                if (logModels.size() == 0) {
                    bottomSheetLayout.setVisibility(View.GONE);
                } else {
                    bottomSheetLayout.setVisibility(View.VISIBLE);
                    blockCount.setText("Total calls blocked : " + String.valueOf(logModels.size()));
                }
                callLogsAdapter.setLogs(logModels);
            }
        });
    }

    private void loadAds() {
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
    }

    private void initControl() {
        PreferenceManager.init(getApplicationContext());
        isBlockingEnabled = PreferenceManager.read(PreferenceManager.IS_DND_ENABLED, false);
        logsViewModel = ViewModelProviders.of(this).get(LogsViewModel.class);
        bottomSheetLayout = findViewById(R.id.bottom_sheet);
        sheetBehavior = BottomSheetBehavior.from(bottomSheetLayout);
        sheetTopLayout = findViewById(R.id.sheet_top_layout);
        arrowUpDown = findViewById(R.id.arrow_up_down);
        dndButton = findViewById(R.id.dnd_button);
        blockSMSSwitch = findViewById(R.id.block_incoming_sms_switch);
        blockWithSmsSwitch = findViewById(R.id.block_with_sms_switch);
        showNotifSwitch = findViewById(R.id.show_notif_switch);
        parentView = findViewById(R.id.parent_layout);
        proButton = findViewById(R.id.pro_button);
        blockingStatus = findViewById(R.id.blocking_text);
        adView = findViewById(R.id.main_banner_ad);
        blockCount = findViewById(R.id.block_count);
        createBlacklist = findViewById(R.id.create_blacklist);

        callLogsAdapter = new CallLogsAdapter();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        logsRecycler = findViewById(R.id.call_logs_recycler);
        logsRecycler.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, layoutManager.getOrientation());
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.recycler_divider));
        logsRecycler.addItemDecoration(dividerItemDecoration);
        logsRecycler.setAdapter(callLogsAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void toggleBlockingMode() {
        AppUtils.vibratePhone(this);
        if (isBlockingEnabled) {
            isBlockingEnabled = false;
            dndButton.setImageResource(R.drawable.red_power_button);
            blockingStatus.setText("Blocking mode is disabled");
            PreferenceManager.write(PreferenceManager.IS_DND_ENABLED, false);
        } else {
            isBlockingEnabled = true;
            dndButton.setImageResource(R.drawable.green_power_button);
            blockingStatus.setText("Blocking mode is enabled");
            PreferenceManager.write(PreferenceManager.IS_DND_ENABLED, true);
        }
    }

    private void showDialogForMessage() {
        String smsToSend = PreferenceManager.read(PreferenceManager.SMS_TO_SEND, "");
        final EditText edittext = new EditText(this);
        edittext.setText(smsToSend);
        edittext.setHint("I am busy right now. Will call you back soon");
        edittext.setTextColor(getResources().getColor(R.color.colorPrimary));
        edittext.setHintTextColor(getResources().getColor(R.color.veryLightGray));
        InputFilter[] filterArray = new InputFilter[1];
        filterArray[0] = new InputFilter.LengthFilter(160);
        edittext.setFilters(filterArray);
        edittext.setSelection(smsToSend.length());

        final AlertDialog dialog = new AlertDialog.Builder(this, R.style.AlertDialogStyle)
                .setView(edittext)
                .setTitle("Enter Message")
                .setPositiveButton(android.R.string.ok, null) //Set to null. We override the onclick
                .setNegativeButton(android.R.string.cancel, null)
                .create();

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {

            @Override
            public void onShow(DialogInterface dialogInterface) {

                Button posButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                Button negButton = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);

                posButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String customMsg = edittext.getText().toString();
                        if (customMsg.isEmpty()) {
                            showToast("Message cannot be empty");
                        } else {
                            PreferenceManager.write(PreferenceManager.SMS_TO_SEND, customMsg);
                            PreferenceManager.write(PreferenceManager.IS_BLOCK_WITH_SMS_ON, true);
                            dialog.dismiss();
                        }
                    }
                });

                negButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        blockWithSmsSwitch.setChecked(false);
                        dialog.dismiss();
                    }
                });
            }
        });
        dialog.show();
    }

    public void toggleBottomSheet() {
        if (sheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
            sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        } else {
            sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }
    }
}
