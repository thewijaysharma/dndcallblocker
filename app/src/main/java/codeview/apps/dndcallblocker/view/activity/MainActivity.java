package codeview.apps.dndcallblocker.view.activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import codeview.apps.dndcallblocker.R;
import codeview.apps.dndcallblocker.utils.PreferenceManager;

public class MainActivity extends BaseActivity {

    private Button dndButton;
    private View parentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initControl();

        boolean isBlockingEnabled=PreferenceManager.read(PreferenceManager.ENABLE_DND,false);
        if(isBlockingEnabled){
            dndButton.setText("Enable DND");
        }else {
            dndButton.setText("Disable DND");
        }
        dndButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("Blocking mode enabled");
            }
        });
    }

    private void initControl() {
        PreferenceManager.init(this);
        dndButton=findViewById(R.id.dnd_button);
        parentView=findViewById(R.id.parent_layout);
    }
}
