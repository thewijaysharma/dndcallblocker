package codeview.apps.dndcallblocker.view.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ArrayAdapter;

import codeview.apps.dndcallblocker.R;

public class AddBlacklistActivity extends BaseActivity {

    private FloatingActionButton fab;
    private View parentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_blacklist);
        initViews();
        showActionBar();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showListDialog();
            }
        });
    }

    private void initViews() {
        fab = findViewById(R.id.add_blacklist_fab);
        parentView = findViewById(R.id.parent_layout);
    }

    private void showListDialog() {
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(this);
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.select_dialog_singlechoice);
        arrayAdapter.add("Select from Call logs");  //index 0
        arrayAdapter.add("Select from Contacts"); //index 1
        arrayAdapter.add("Add new blacklist number (Pro)"); //index 2

        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int index) {
                switch (index) {
                    case 0: {
                        Intent intent = new Intent(AddBlacklistActivity.this, ChoseBlacklistActivity.class);
                        startActivity(intent);
                    }
                    break;
                    case 1: {
                        Intent intent = new Intent(AddBlacklistActivity.this, ChoseBlacklistActivity.class);
                        startActivity(intent);
                    }

                    break;
                    case 2:
                        showSnackbar("This feature is only available in Pro version.", parentView);
                        break;
                }
            }
        });
        builderSingle.show();
    }
}
