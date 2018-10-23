package codeview.apps.dndcallblocker.view.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import codeview.apps.dndcallblocker.R;
import codeview.apps.dndcallblocker.model.BlacklistModel;
import codeview.apps.dndcallblocker.utils.PreferenceManager;
import codeview.apps.dndcallblocker.view.adapter.BlacklistAdapter;
import codeview.apps.dndcallblocker.view_model.BlacklistViewModel;

public class ShowBlacklistActivity extends BaseActivity {

    private FloatingActionButton fab;
    private View parentView;
    private BlacklistViewModel viewModel;
    private TextView noListPlacehoder;
    private RecyclerView recyclerView;
    private BlacklistAdapter adapter;
    private List<BlacklistModel> blacklistModels;
    private View filter;
    private Switch blockAllSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_blacklist);
        initViews();
        setViews();
        showActionBar();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showListDialog();
            }
        });

        blockAllSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(buttonView.isPressed()){
                    if(isChecked){
                        PreferenceManager.write(PreferenceManager.IS_BLOCK_ALL_ENABLED,true);
                        filter.setVisibility(View.VISIBLE);
                    }else {
                        PreferenceManager.write(PreferenceManager.IS_BLOCK_ALL_ENABLED,false);
                        filter.setVisibility(View.GONE);

                    }
                }
            }
        });

    }

    private void setViews() {
        if(PreferenceManager.read(PreferenceManager.IS_BLOCK_ALL_ENABLED,true)){
            blockAllSwitch.setChecked(true);
            filter.setVisibility(View.VISIBLE);
        }else {
            blockAllSwitch.setChecked(false);
            filter.setVisibility(View.GONE);
        }

        viewModel.getAllBlacklists().observe(this, new Observer<List<BlacklistModel>>() {
            @Override
            public void onChanged(@Nullable List<BlacklistModel> blacklistModelList) {
                if(blacklistModelList.size()>0){
                    noListPlacehoder.setVisibility(View.GONE);

                    blacklistModels.clear();
                    blacklistModels.addAll(blacklistModelList);
                    adapter.notifyDataSetChanged();
                }else {
                    noListPlacehoder.setVisibility(View.VISIBLE);
                }

            }
        });

    }

    private void initViews() {
        viewModel=ViewModelProviders.of(this).get(BlacklistViewModel.class);
        fab = findViewById(R.id.add_blacklist_fab);
        blockAllSwitch=findViewById(R.id.block_all);
        parentView = findViewById(R.id.parent_layout);
        noListPlacehoder=findViewById(R.id.no_blacklist_placeholder);
        recyclerView=findViewById(R.id.blacklist_recycler);
        filter=findViewById(R.id.blacklist_filter);
        blacklistModels=new ArrayList<>();
        adapter=new BlacklistAdapter(blacklistModels);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.recycler_divider));
        recyclerView.addItemDecoration(dividerItemDecoration);
    }

    private void showListDialog() {
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(this);
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.select_dialog_singlechoice);
//        arrayAdapter.add("Select from Call logs");  //index 0
        arrayAdapter.add("Select from Contacts"); //index 1
        arrayAdapter.add("Add a new blacklist number"); //index 2

        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int index) {
                switch (index) {
                    case 0: {
                        Intent intent = new Intent(ShowBlacklistActivity.this, ChoseBlacklistActivity.class);
                        startActivity(intent);
                    }
                    break;
                    case 1:
                        addBlacklistDialog();
                    break;
                }
            }
        });
        builderSingle.show();
    }

    private void addBlacklistDialog() {
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setMinimumWidth(300);

        final EditText numberEt = new EditText(this);
        numberEt.setHint("Phone number");
        numberEt.setMaxLines(1);
        numberEt.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        numberEt.setTextColor(getResources().getColor(R.color.colorPrimary));
        numberEt.setHintTextColor(getResources().getColor(R.color.veryLightGray));
        layout.addView(numberEt);

        final EditText nameEt = new EditText(this);
        nameEt.setMaxLines(1);
        nameEt.setHint("Name (Optional)");
        nameEt.setInputType(InputType.TYPE_CLASS_TEXT);
        nameEt.setTextColor(getResources().getColor(R.color.colorPrimary));
        nameEt.setHintTextColor(getResources().getColor(R.color.veryLightGray));
        layout.addView(nameEt);

        final AlertDialog dialog = new AlertDialog.Builder(this,R.style.AlertDialogStyle)
                .setView(layout)
                .setTitle("Add new blacklist")
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
                        String number = numberEt.getText().toString();
                        String name=nameEt.getText().toString().isEmpty()?"Unnamed":nameEt.getText().toString();
                        if(number.isEmpty()){
                            showToast("Number cannot be empty");
                        }else {
                            viewModel.insertBlacklist(new BlacklistModel(name,number));
                            dialog.dismiss();
                        }
                    }
                });

                negButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
        });

        dialog.show();
    }

}
