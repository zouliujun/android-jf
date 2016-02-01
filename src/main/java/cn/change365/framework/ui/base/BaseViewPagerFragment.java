package cn.change365.framework.ui.base;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import cn.change365.framework.R;
import cn.change365.framework.event.TabChangeEvent;
import cn.change365.framework.ui.MyViewPager;
import de.greenrobot.event.EventBus;

public abstract class BaseViewPagerFragment extends BaseFragment implements ActionBar.TabListener {

	protected MyViewPager mViewPager;

	protected BaseViewPagerAdapter mPagerAdapter;

	private Map<String, View> tabs;

	@Override
	public void onMyCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mViewPager = (MyViewPager)view.findViewById(R.id.pager);
		if(dataIsReady()){
			init();
		}else{
			Toast.makeText(getActivity(), R.string.error_load_data, Toast.LENGTH_SHORT).show();
		}
	}

	protected boolean dataIsReady(){
		return true;
	}

	protected boolean isInit;

	protected void init(){
		if(mPagerAdapter == null) {
			mPagerAdapter = getPagerAdapter();
		}

		mViewPager.setPagingEnabled(enableViewPager());

		mViewPager.setOffscreenPageLimit(viewPagerLimit());
		mViewPager.setAdapter(mPagerAdapter);

		mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				actionBar.setSelectedNavigationItem(position);
			}

		});

		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		tabs = new HashMap<>();
		for (int i = 0; i < mPagerAdapter.getCount(); i++) {
			String title = mPagerAdapter.getPageTitle(i).toString();
			View tv = renderTabView(getActivity(), title);
			ActionBar.Tab tab = actionBar.newTab()
//					.setText(mPagerAdapter.getPageTitle(i))

					.setCustomView(tv)
					.setTag(mPagerAdapter.getPageTag(i))
					.setTabListener(this);
			tabs.put(mPagerAdapter.getPageTag(i), tv);
			actionBar.addTab(tab);
		}

		isInit = true;
	}

	protected View getTabCustomViewByTag(String tag){
		if(tabs == null || tabs.isEmpty()){
			return null;
		}
		return tabs.get(tag);
	}

	private static View renderTabView(Context context, String title) {
		TextView view = (TextView) LayoutInflater.from(context).inflate(R.layout.action_bar_tab_indicator, null);
		// We need to manually set the LayoutParams here because we don't have a view root
		view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
		view.setText(title);
		return view;
	}

	protected abstract BaseViewPagerAdapter getPagerAdapter();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}

	@Override
	protected String getName() {
		return "BaseViewPagerFragment";
	}

	@Override
	protected void onMyDestoryView() {
		actionBar.removeAllTabs();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
	}

	private Menu menu;

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		this.menu = menu;
		int menuId = getOptionsMenu(0);
		if(menuId > 0){
			inflater.inflate(menuId, menu);
		}

	}

	protected abstract int getOptionsMenu(int position);

	@Override
	protected int getLayoutId() {
		return R.layout.fragment_viewpager;
	}

	@Override
	public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
		mViewPager.setCurrentItem(tab.getPosition());

		if(menu != null){
			menu.clear();
			int menuId = getOptionsMenu(tab.getPosition());
			if(menuId > 0){
				getActivity().getMenuInflater().inflate(menuId, menu);
			}
		}

		EventBus.getDefault().post(new TabChangeEvent(TabChangeEvent.TYPE_SELECT, tab.getTag().toString()));
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
		EventBus.getDefault().post(new TabChangeEvent(TabChangeEvent.TYPE_UNSELECT, tab.getTag().toString()));
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
		EventBus.getDefault().post(new TabChangeEvent(TabChangeEvent.TYPE_RESELECT, tab.getTag().toString()));
	}

	protected boolean enableViewPager(){
		return true;
	}

	protected int viewPagerLimit(){
		return 1;
	}

	public String getCurrentTabTag(){
		ActionBar.Tab selectedTab = actionBar.getSelectedTab();
		if(selectedTab != null){
			return selectedTab.getTag().toString();
		}else{
			return "";
		}
	}
}



