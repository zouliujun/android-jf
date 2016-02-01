package cn.change365.framework.ui.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Jack on 2015/7/1.
 */
public class BaseViewPagerAdapter extends FragmentStatePagerAdapter {

    private Context context;

    public BaseViewPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    public static class AdapterObj{
        public AdapterObj(String title, String tag, String fragment, Bundle bundle){
            this.title = title;
            this.fragment = fragment;
            this.bundle = bundle;
            this.tag = tag;
        }
        public String title;
        public String fragment;
        public Bundle bundle;
        public String tag;
    }

    private Fragment mCurrentFragment;

    public Fragment getCurrentFragment() {
        return mCurrentFragment;
    }

    private List<AdapterObj> data;

    public void setData(List<AdapterObj> data){
        this.data = data;
    }

    @Override
    public int getCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return data.get(position).title;
    }

    public String getPageTag(int position){
        return data.get(position).tag;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment f = Fragment.instantiate(context, data.get(position).fragment, data.get(position).bundle);
        return f;
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        if (getCurrentFragment() != object) {
            mCurrentFragment = ((Fragment) object);
        }
        super.setPrimaryItem(container, position, object);
    }
}
