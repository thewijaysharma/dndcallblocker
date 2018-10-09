package codeview.apps.dndcallblocker.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import codeview.apps.dndcallblocker.R;
import codeview.apps.dndcallblocker.view.adapter.ContactsAdapter;

public class ChoseBlacklistActivity extends BaseActivity {

    private RecyclerView recyclerView;
    private ContactsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chose_blacklist);
        initViews();
        showActionBar();

    }

    private void initViews() {
        recyclerView=findViewById(R.id.contact_list);
        adapter=new ContactsAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.recycler_divider));
        recyclerView.addItemDecoration(dividerItemDecoration);
    }
}
