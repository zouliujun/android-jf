package cn.change365.framework.ui.base;

import android.app.Service;
import android.os.Handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Timer;
import java.util.TimerTask;

import cn.change365.framework.dto.SchedulePara;


/**
 * Created by Jack on 2015/10/31.
 */
public abstract class BaseService extends Service {

    private static final Logger log = LoggerFactory.getLogger(BaseService.class);

    protected Timer timer;
    private TimerTask task;
    //用于执行主线程任务
    protected Handler handler;
    //定时任务线程
    private Runnable taskThread;

    protected void startSchedule() {
        //确保多次调用时不会发生问题
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

    @Override
    public void onCreate() {
        super.onCreate();
        log.debug("onCreate " + getName());

        if (getSchedulePara().executeInCreateAndDestory) {
            startSchedule();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        log.debug("onDestroy " + getName());

        if (getSchedulePara().executeInCreateAndDestory) {
            stopSchedule();
        }
    }

    protected abstract String getName();
}
