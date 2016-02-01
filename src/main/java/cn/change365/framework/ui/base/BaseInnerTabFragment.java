package cn.change365.framework.ui.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import cn.change365.framework.R;


/**
 * Created by Jack on 2015/8/26.
 */

public abstract class BaseInnerTabFragment extends Fragment {

    private FragmentTabHost tabHost;

    protected List<TabInfo> tabInfos;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_inner_tab, container, false);
        tabHost = (FragmentTabHost) rootView.findViewById(R.id.tabHost);
        tabHost.setup(getActivity(), getChildFragmentManager(), R.id.tabContent);

        tabHost.getTabWidget().setDividerDrawable(R.drawable.inner_tab_divider);
        if (android.os.Build.VERSION.SDK_INT > 10) {
            tabHost.getTabWidget().setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        }

        if(tabInfos == null){
            tabInfos = getInfo();
            for(TabInfo info : tabInfos){
                View indicator = inflater.inflate(R.layout.inner_tab_indicator, null);
                TextView title = (TextView) indicator.findViewById(R.id.tab_title);
                title.setText(info.title);
                tabHost.addTab(tabHost.newTabSpec(info.tag).setIndicator(indicator),
                        info.fragment, info.args);
            }
        }
        return rootView;
    }

    protected abstract List<TabInfo> getInfo();

    protected class TabInfo{
        String tag;
        int title;
        Class<? extends Fragment> fragment;
        Bundle args;

        public TabInfo(String tag, int title, Class<? extends Fragment> fragment, Bundle args) {
            this.tag = tag;
            this.title = title;
            this.fragment = fragment;
            this.args = args;
        }
    }
}