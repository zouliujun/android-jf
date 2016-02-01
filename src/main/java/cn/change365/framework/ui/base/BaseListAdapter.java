package cn.change365.framework.ui.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import java.util.List;

/**
 * Created by Jack on 2015/7/1.
 */
public abstract class BaseListAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected List<T> mDataset;
    protected Context context;
    private Animation showListAnimation;

    public abstract class BaseListViewHolder extends RecyclerView.ViewHolder {


        public BaseListViewHolder(View v) {
            super(v);
        }

        public View getView(){
            return this.itemView;
        }

        public abstract void setData(T obj);

    }

    public BaseListAdapter(List<T> dataset, Context context) {
        this.context = context;
        mDataset = dataset;
        showListAnimation = getShowAnimation();
    }

    protected Animation getShowAnimation(){
        return AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
    }

    protected abstract BaseListViewHolder getViewHolder(ViewGroup parent, int viewType);

    public void setDataset(List<T> dataset){
        mDataset = dataset;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return getViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        T data = mDataset.get(position);
        BaseListViewHolder baseHolder = (BaseListViewHolder) holder;
        baseHolder.setData(data);
//        setAnimation(baseHolder.getView(), position);
    }

    private int lastPosition = -1;

    private void setAnimation(View viewToAnimate, int position)
    {
        if (position > lastPosition)
        {
            viewToAnimate.startAnimation(showListAnimation);
            lastPosition = position;
        }
    }

    @Override
    public int getItemCount() {
        return mDataset == null ? 0 : mDataset.size();
    }


}
