package codeview.apps.dndcallblocker.view.activity;

import android.Manifest;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.CursorLoader;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.CallLog;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import codeview.apps.dndcallblocker.R;
import codeview.apps.dndcallblocker.model.BlacklistModel;
import codeview.apps.dndcallblocker.model.Contact;
import codeview.apps.dndcallblocker.utils.ContactsFetchTask;
import codeview.apps.dndcallblocker.view.adapter.ContactsAdapter;
import codeview.apps.dndcallblocker.view_model.BlacklistViewModel;

public class ChoseBlacklistActivity extends BaseActivity implements ContactsFetchTask.GetContactsCallback {

    private RecyclerView recyclerView;
    private ContactsAdapter adapter;
    private ProgressBar progressBar;
    private final int CALL_LOG_LOADER=1;
    private final int CONTACTS_LOADER=2;
    private boolean isSaved = true;
    private List<Contact> contacts;
    private BlacklistViewModel blacklistViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chose_blacklist);
        initControl();
        showActionBar();
        new ContactsFetchTask(this).execute(this);

    }

//    private void getCallDetails() {
//
//        StringBuffer sb = new StringBuffer();
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }
//
//        Cursor managedCursor = getContentResolver().query(CallLog.Calls.CONTENT_URI, null,
//                null, null, null);
//        int number = managedCursor.getColumnIndex(CallLog.Calls.NUMBER);
//        int type = managedCursor.getColumnIndex(CallLog.Calls.TYPE);
//        int date = managedCursor.getColumnIndex(CallLog.Calls.DATE);
//        int duration = managedCursor.getColumnIndex(CallLog.Calls.DURATION);
//        sb.append("Call Details :");
//        while (managedCursor.moveToNext()) {
//            String phNumber = managedCursor.getString(number);
//            String callType = managedCursor.getString(type);
//            String callDate = managedCursor.getString(date);
//            Date callDayTime = new Date(Long.valueOf(callDate));
//            String callDuration = managedCursor.getString(duration);
//            String dir = null;
//            int dircode = Integer.parseInt(callType);
//            switch (dircode) {
//                case CallLog.Calls.OUTGOING_TYPE:
//                    dir = "OUTGOING";
//                    break;
//
//                case CallLog.Calls.INCOMING_TYPE:
//                    dir = "INCOMING";
//                    break;
//
//                case CallLog.Calls.MISSED_TYPE:
//                    dir = "MISSED";
//                    break;
//            }
//            sb.append("\nPhone Number:--- " + phNumber + " \nCall Type:--- "
//                    + dir + " \nCall Date:--- " + callDayTime
//                    + " \nCall duration in sec :--- " + callDuration);
//            sb.append("\n----------------------------------");
//        }
//        managedCursor.close();
//
//    }

    private void initControl() {
//        getLoaderManager().initLoader(CALL_LOG_LOADER,null,ChoseBlacklistActivity.this);
        recyclerView=findViewById(R.id.contact_list);
        progressBar=findViewById(R.id.progress_bar);
        contacts=new ArrayList<>();
        adapter=new ContactsAdapter(contacts);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.recycler_divider));
        recyclerView.addItemDecoration(dividerItemDecoration);
        blacklistViewModel= ViewModelProviders.of(this).get(BlacklistViewModel.class);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.save_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.save){
            for(Contact contact:contacts){
                if(contact.isBlacklisted()){
                    blacklistViewModel.insertBlacklist(new BlacklistModel(contact.getName(),contact.getPhone()));
                }
            }
            showToast("Blacklist saved");
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

//    @Override
//    public android.content.Loader<Cursor> onCreateLoader(int id, Bundle args) {
//        if(id==CALL_LOG_LOADER){
//            Calendar today=Calendar.getInstance();
//            Calendar oneWeekAgo = Calendar.getInstance();
//            oneWeekAgo.add(Calendar.DAY_OF_YEAR, -7);
//
//            String desc = android.provider.CallLog.Calls.DATE + " DESC limit 500";
//
//            return new CursorLoader(this,CallLog.Calls.CONTENT_URI, null, android.provider.CallLog.Calls.DATE + " BETWEEN ? AND ?",
//                    new String[]{String.valueOf(today.getTimeInMillis()),String.valueOf(oneWeekAgo.getTimeInMillis())},desc);
//
//        }
//        return null;
//    }
//
//    @Override
//    public void onLoadFinished(android.content.Loader<Cursor> loader, Cursor data) {
//        if(data!=null && data.getCount()>0){
//            Log.d("ChoseBlacklistActivity",""+data.getColumnIndex(CallLog.Calls.NUMBER)+data.getColumnIndex(CallLog.Calls.DATE)+data.getColumnIndex(CallLog.Calls.CACHED_NAME));
//        }
//    }
//
//    @Override
//    public void onLoaderReset(android.content.Loader<Cursor> loader) {
//
//    }

    @Override
    public void onContactsReceived(List<Contact> contactList) {
        progressBar.setVisibility(View.GONE);
        this.contacts.clear();
        this.contacts.addAll(contactList);
        adapter.notifyDataSetChanged();
    }
}
