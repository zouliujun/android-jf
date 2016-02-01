package cn.change365.framework.dto;

/**
 * Created by Jack on 2015/10/30.
 */
public class SchedulePara {
    //定时任务开始时间
    public long startTime;
    //定时任务间隔时间
    public long periodTime;
    //定时任务是否运行在主线程
    public boolean executeInMain;
    //是否在onStart和onStop执行
    public boolean executeInStartAndStop;
    //是否在onCreateView和onDestroyView执行(service只能在这个地方设置)
    public boolean executeInCreateAndDestory;
}
