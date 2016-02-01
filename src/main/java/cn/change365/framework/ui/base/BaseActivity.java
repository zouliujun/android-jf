package cn.change365.framework.ui.base;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

/**
 * Created by Jack on 2015/8/25.
 */
public class BaseActivity extends AppCompatActivity {

    protected ActionBar mActionBar;
    protected Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActionBar = getSupportActionBar();
        initActionBar(mActionBar);
        handler = new Handler();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    protected int getActionBarTitle() {
        return 0;
    }

    protected void setActionBarTitle(String title){
        mActionBar.setTitle(title);
    }

    protected boolean hasBackButton() {
        return false;
    }

    protected void initActionBar(ActionBar actionBar) {
        if (actionBar == null)
            return;
        if (hasBackButton()) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }
//        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE);
        actionBar.setDisplayUseLogoEnabled(false);
        int titleRes = getActionBarTitle();
        if(titleRes != 0){
            actionBar.setTitle(titleRes);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
