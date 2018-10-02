package codeview.apps.dndcallblocker.view.activity;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void showToast(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

    protected void showSnackbar(String msg, View parentView){
        Snackbar snackbar = Snackbar.make(parentView, msg, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

}
