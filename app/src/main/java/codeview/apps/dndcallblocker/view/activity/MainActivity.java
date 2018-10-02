package codeview.apps.dndcallblocker.view.activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import codeview.apps.dndcallblocker.R;

public class MainActivity extends BaseActivity {

    private Button dndButton;
    private View parentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        dndButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("Blocking mode enabled");
            }
        });
    }

    private void initViews() {
        dndButton=findViewById(R.id.dnd_button);
        parentView=findViewById(R.id.parent_layout);
    }
}
