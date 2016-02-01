package cn.change365.framework.ui.base;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.ButterKnife;
import cn.change365.framework.R;
import cn.change365.framework.dto.SchedulePara;
import cn.change365.framework.utils.UIHelper;

/**
 * Created by Jack on 2015/7/1.
 */
public abstract class BaseFragment extends Fragment {
    protected View view;
    protected ActionBar actionBar;
    private ProgressDialog waitDialog;
    protected Timer timer;
    private TimerTask task;
    //用于执行主线程任务
    protected Handler handler;
    //定时任务线程
    private Runnable taskThread;

    private static final Logger log = LoggerFactory.getLogger(BaseFragment.class);

    protected LayoutInflater inflater;

    private Boolean isDestory;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        log.debug("onCreateView " + getName());

        isDestory = false;
        this.inflater = inflater;

        view = customCreateView(inflater, container, savedInstanceState);

        ButterKnife.bind(this, view);

        actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();

        handler = new Handler();

        onMyCreateView(inflater, container, savedInstanceState);

        if (getSchedulePara().executeInCreateAndDestory) {
            startSchedule();
        }
        return view;
    }

    protected View customCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(getLayoutId(), container, false);
    }

    @Override
    public void onDestroyView() {
        log.debug("onDestroyView " + getName());
        super.onDestroyView();
        isDestory = true;
        if (getSchedulePara().executeInCreateAndDestory) {
            stopSchedule();
        }

        onMyDestoryView();

        ButterKnife.unbind(this);

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        log.trace("onSaveInstanceState " + getName());
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        log.debug("onCreate " + getName());
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        log.debug("onDestroy " + getName());
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        log.debug("onResume " + getName());
    }

    @Override
    public void onPause() {
        super.onPause();
        log.debug("onPause " + getName());
    }

    @Override
    public void onStart() {
        super.onStart();
        log.debug("onStart " + getName());

        if (getSchedulePara().executeInStartAndStop) {
            startSchedule();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        log.debug("onStop " + getName());

        if (getSchedulePara().executeInStartAndStop) {
            stopSchedule();
        }
    }

    protected void startSchedule() {
        stopSchedule();

        if (!scheduleCondition()) {
            return;
        }

        log.debug(getName() + " start timer delay={} period={}", getSchedulePara().startTime, getSchedulePara().periodTime);

        timer = new Timer();

        task = new TimerTask() {
            @Override
            public void run() {
                runScheduleOnce();
            }
        };
        timer.schedule(task, getSchedulePara().startTime, getSchedulePara().periodTime);
    }

    protected void stopSchedule() {
        if (task != null) {
            log.debug(getName() + " stop timer");
            task.cancel();
            task = null;
        }
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    //执行定时任务一次
    protected void runScheduleOnce() {
        if (getSchedulePara().executeInMain) {
            if (taskThread == null) {
                taskThread = new Runnable() {
                    @Override
                    public void run() {
                        mySchedule();
                    }
                };
            }
            handler.post(taskThread);
        } else {
            mySchedule();
        }
    }

    //定时任务条件
    protected boolean scheduleCondition() {
        return false;
    }

    //执行定时任务
    protected void mySchedule() {
    }

    //获取定时任务参数
    protected SchedulePara getSchedulePara() {
        return new SchedulePara();
    }

    protected abstract int getLayoutId();

    protected void onMyCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    }

    protected void onMyDestoryView() {
    }

    //子fragment显示名字
    protected abstract String getName();

    protected void showWaitDialog(int msg, boolean cancelable) {
        if(getActivity() == null){
            return;
        }
        if (waitDialog == null) {
            waitDialog = UIHelper.getWaitDialog(getActivity(), msg);
        } else {
            waitDialog.setMessage(getActivity().getString(msg));
        }
        waitDialog.setCancelable(cancelable);
        waitDialog.show();
    }

    protected void showWaitDialog(boolean cancelable){
        showWaitDialog(R.string.info_data_is_loading, cancelable);
    }

    protected void hideWaitDialog() {
        if (waitDialog != null && waitDialog.isShowing()) {
            waitDialog.dismiss();
        }
    }

    protected void showInfo(int msg){
        if(getActivity() == null){
            return;
        }
        UIHelper.showInfo(getActivity(), msg);
    }

    protected void showMessageDialog(int msgId, DialogInterface.OnClickListener okAction){
        if(getActivity() == null){
            return;
        }
        UIHelper.showMessageDialog(getActivity(), msgId, okAction);
    }

    protected void showMessageDialog(int msgId){
        if(getActivity() == null){
            return;
        }
        UIHelper.showMessageDialog(getActivity(), msgId);
    }

    //判断fragment是否还没被销毁，用于异步方法返回时判断是否进行操作
    protected boolean isValid(){
        if(isDestory != null && !isDestory){
            return true;
        }else{
            return false;
        }
    }
}
