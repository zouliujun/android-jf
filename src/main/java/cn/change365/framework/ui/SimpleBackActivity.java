package cn.change365.framework.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.change365.framework.R;
import cn.change365.framework.ui.base.BaseActivity;

/**
 * Created by Jack on 2015/8/25.
 */
public class SimpleBackActivity extends BaseActivity {

    private static final Logger log = LoggerFactory.getLogger(SimpleBackActivity.class);

    public static final String ARGS_FRAGMENT_CLASS = "fragment";
    public static final String ARGS_FRAGMENT_BUNDLE = "fragmentBundle";
    public static final String ARGS_TITLE = "title";
    public static final String ARGS_TAG = "tag";

    private Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_simple_back);

        Class clazz = (Class) getIntent().getSerializableExtra(ARGS_FRAGMENT_CLASS);
        Bundle bundle = getIntent().getBundleExtra(ARGS_FRAGMENT_BUNDLE);
        String title = getIntent().getStringExtra(ARGS_TITLE);
        String tag = getIntent().getStringExtra(ARGS_TAG);

        try {
            fragment = (Fragment) clazz.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        if(bundle != null){
            fragment.setArguments(bundle);
        }

        FragmentTransaction trans = getSupportFragmentManager()
                .beginTransaction();
        trans.replace(R.id.fragment, fragment, tag);
        trans.commitAllowingStateLoss();

        setActionBarTitle(title);

    }

    @Override
    protected boolean hasBackButton() {
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            if(fragment instanceof WebViewFragment){
                if(((WebViewFragment)fragment).onKeyDown(keyCode, event)){
                    return true;
                }
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}

