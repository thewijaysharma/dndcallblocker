package codeview.apps.dndcallblocker.view.activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import codeview.apps.dndcallblocker.R;
import codeview.apps.dndcallblocker.utils.PreferenceManager;
import codeview.apps.dndcallblocker.view.adapter.CallLogsAdapter;

public class MainActivity extends BaseActivity {

    private ImageButton dndButton;
    private TextView blockingStatus;
    private View parentView;
    private boolean isBlockingEnabled;
    private View bottomSheetLayout;
    private BottomSheetBehavior sheetBehavior;
    private ImageView arrowUpDown;
    private View sheetTopLayout;
    private RecyclerView logsRecycler;
    private Animation startRotateAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initControl();

        if(isBlockingEnabled){
            dndButton.setImageResource(R.drawable.green_power_button);
            blockingStatus.setText("Blocking mode is enabled");
        }else {
            dndButton.setImageResource(R.drawable.red_power_button);
            blockingStatus.setText("Blocking mode is disabled");
        }

        dndButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isBlockingEnabled){
                    isBlockingEnabled=false;
                    dndButton.setImageResource(R.drawable.red_power_button);
                    blockingStatus.setText("Blocking mode is disabled");
                    PreferenceManager.write(PreferenceManager.ENABLE_DND,false);
                }else {
                    isBlockingEnabled=true;
                    dndButton.setImageResource(R.drawable.green_power_button);
                    blockingStatus.setText("Blocking mode is enabled");
                    PreferenceManager.write(PreferenceManager.ENABLE_DND,true);
                }
            }
        });


        /**
         * bottom sheet state change listener
         * we are changing button text when sheet changed state
         * */
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

            }
        });

        sheetTopLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleBottomSheet();
            }
        });
    }

    private void initControl() {
        PreferenceManager.init(getApplicationContext());
        isBlockingEnabled=PreferenceManager.read(PreferenceManager.ENABLE_DND,false);
        bottomSheetLayout=findViewById(R.id.bottom_sheet);
        sheetBehavior =BottomSheetBehavior.from(bottomSheetLayout);
        sheetTopLayout=findViewById(R.id.sheet_top_layout);
        arrowUpDown=findViewById(R.id.arrow_up_down);
        dndButton=findViewById(R.id.dnd_button);
        parentView=findViewById(R.id.parent_layout);
        blockingStatus=findViewById(R.id.blocking_text);
        startRotateAnimation= AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_animation);

        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        logsRecycler=findViewById(R.id.call_logs_recycler);
        logsRecycler.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, layoutManager.getOrientation());
        logsRecycler.addItemDecoration(dividerItemDecoration);
        logsRecycler.setAdapter(new CallLogsAdapter());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void toggleBottomSheet() {
        arrowUpDown.startAnimation(startRotateAnimation);
        if (sheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
            sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        } else {
            sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }
    }
}
