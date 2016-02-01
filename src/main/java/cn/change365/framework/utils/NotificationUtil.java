package cn.change365.framework.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;


/**
 * Created by Jack on 2015/10/8.
 */
public class NotificationUtil {

    public static class Param{
        //状态栏滚动显示
        public String ticker;
        //标题
        public String title;
        //内容
        public String content;
        //次要内容
        public String subText;
        //点击事件
        public PendingIntent contentIntent;
        //notice唯一id，同一id
        public int id;
        //伴随声音
        public boolean sound;
        //如果为null则是默认声音
        public String soundUrl;
        //伴随震动
        public boolean vibrate;
        //伴随闪光
        public boolean light;
        //小图标
        public int smallIcon = -1;

    }

    public static void showNotification(Context context, Param param){
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(
                Context.NOTIFICATION_SERVICE);
        final NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setTicker(param.ticker)
                .setContentTitle(param.title)
                .setContentText(param.content)
                .setAutoCancel(true);

        if(param.contentIntent != null){
            builder.setContentIntent(param.contentIntent);
        }

        if(param.smallIcon != -1) {
            builder.setSmallIcon(param.smallIcon);
        }


        int nDefault = 0;
        if(param.sound){
            if(param.soundUrl != null){
                builder.setSound(Uri.parse(param.soundUrl));
            }else{
                nDefault = nDefault | Notification.DEFAULT_SOUND;
            }
        }
        if(param.vibrate){
            nDefault = nDefault | Notification.DEFAULT_VIBRATE;
        }
        if(param.light){
            nDefault = nDefault | Notification.DEFAULT_LIGHTS;
        }
        builder.setDefaults(nDefault);

        if(param.subText != null) {
            builder.setSubText(param.subText);
        }

        mNotificationManager.notify(param.id, builder.build());
    }

    public static void cancelNotification(Context context, int id){
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(
                Context.NOTIFICATION_SERVICE);
        mNotificationManager.cancel(id);
    }

}
