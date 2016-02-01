package cn.change365.framework.ui.base;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import butterknife.Bind;
import cn.change365.framework.R;
import cn.change365.framework.decoration.recyclerview.SimpleDividerItemDecoration;

/**
 * Created by Jack on 2015/7/1.
 */
public abstract class BaseListFragment extends BaseFragment {

    protected BaseListAdapter mAdapter;

    protected SwipeRefreshLayout mySwipeRefreshLayout;

    protected RecyclerView myRecyclerView;

    @Override
    public void onMyCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mySwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        myRecyclerView = (RecyclerView) view.findViewById(R.id.myRecyclerView);
        mySwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        refreshData();
                    }
                }
        );
        initList();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    private void initList() {
        myRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        myRecyclerView.setLayoutManager(mLayoutManager);
        myRecyclerView.setItemAnimator(new DefaultItemAnimator());
        myRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(Color.WHITE));
        // specify an adapter (see also next example)
        if(mAdapter == null) {
            mAdapter = getAdapter();
        }
        myRecyclerView.setAdapter(mAdapter);

        refreshData();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_list;
    }

    //获取数据Adapter
    protected abstract BaseListAdapter getAdapter();

    //刷新数据实现方法
    public abstract void refreshData();

    protected void stopRefresh(){
        if(mySwipeRefreshLayout != null){
            mySwipeRefreshLayout.setRefreshing(false);
        }
    }
}
