package codeview.apps.dndcallblocker.view.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ArrayAdapter;

import java.util.List;

import codeview.apps.dndcallblocker.R;
import codeview.apps.dndcallblocker.model.BlacklistModel;
import codeview.apps.dndcallblocker.view_model.BlacklistViewModel;

public class ShowBlacklistActivity extends BaseActivity {

    private FloatingActionButton fab;
    private View parentView;
    private BlacklistViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_blacklist);
        initViews();
        showActionBar();

        viewModel.getAllBlacklists().observe(this, new Observer<List<BlacklistModel>>() {
            @Override
            public void onChanged(@Nullable List<BlacklistModel> blacklistModels) {
                //update recyclerview adapter
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showListDialog();
            }
        });
    }

    private void initViews() {
        viewModel=ViewModelProviders.of(this).get(BlacklistViewModel.class);
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
                        Intent intent = new Intent(ShowBlacklistActivity.this, ChoseBlacklistActivity.class);
                        startActivity(intent);
                    }
                    break;
                    case 1: {
                        Intent intent = new Intent(ShowBlacklistActivity.this, ChoseBlacklistActivity.class);
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
