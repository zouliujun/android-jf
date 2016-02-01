package cn.change365.framework.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by Jack on 2015/7/2.
 */
public class DeviceUtil {
    public static class DeviceInfo {
        public int widthPixels;    //分辨率宽
        public int heightPixels;   //分辨率高
        public float widthDpi;     //宽dpi
        public float heightDpi;    //高dpi
        public int densityDpi;     //实际dpi，如mdpi为160、hdpi为240
        public String densityStr;     //mdpi、hdpi等
        public float scaleFactor;  //dpi比例，如mdpi为1、hdpi为1.5
        public float widthDp;      //宽dp，定义布局用到，非dpi
        public float heightDp;     //高dp，定义布局用到，非dpi
        public float widthInches;  //宽英寸
        public float heightInches; //高英寸
        public double deviceInches;//实际设备英寸
        public String screenSize;  //small、normal、large、xlarge
        public int sdkInt;         //运行版本
        public String sdkName;     //运行版本名字

        @Override
        public String toString() {
            return StringUtils.connectStr("Resolution=", widthPixels, "x", heightPixels, "\n",
                            "Dpi=", densityDpi, ",", densityStr, ",", scaleFactor, "\n",
                            "Dp=", widthDp, "x", heightDp, "\n",
                            "Inches=", deviceInches, ",", screenSize, "\n",
                            "SDK=", sdkInt, ",", sdkName, "\n"
                            );
        }
    }

    public static DeviceInfo getDisplayInfo(Context context) {
        DeviceInfo info = new DeviceInfo();
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager windowManager=(WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(metrics);
        info.widthPixels = metrics.widthPixels;
        info.heightPixels = metrics.heightPixels;

        info.widthDpi = metrics.xdpi;
        info.heightDpi = metrics.ydpi;
        info.densityDpi = metrics.densityDpi;
        info.scaleFactor = metrics.density;

        if (info.densityDpi == DisplayMetrics.DENSITY_LOW) {
            info.densityStr = "ldpi";
        } else if (info.densityDpi == DisplayMetrics.DENSITY_MEDIUM) {
            info.densityStr = "mdpi";
        } else if (info.densityDpi == DisplayMetrics.DENSITY_HIGH) {
            info.densityStr = "hdpi";
        } else if (info.densityDpi == DisplayMetrics.DENSITY_XHIGH) {
            info.densityStr = "xhdpi";
        } else if (info.densityDpi == DisplayMetrics.DENSITY_XXHIGH) {
            info.densityStr = "xxhdpi";
        } else if (info.densityDpi == DisplayMetrics.DENSITY_XXXHIGH) {
            info.densityStr = "xxxhdpi";
        } else if (info.densityDpi == DisplayMetrics.DENSITY_TV) {
            info.densityStr = "tvdpi";
        } else if (info.densityDpi == DisplayMetrics.DENSITY_280) {
            info.densityStr = "DENSITY_280 between hdpi and xhdpi";
        } else if (info.densityDpi == DisplayMetrics.DENSITY_400) {
            info.densityStr = "DENSITY_400 between xhdpi and xxhdpi";
        } else if (info.densityDpi == DisplayMetrics.DENSITY_560) {
            info.densityStr = "DENSITY_560 between xxhdpi and xxxhdpi";
        } else {
            info.densityStr = "other";
        }

        info.widthDp = info.widthPixels / info.scaleFactor;
        info.heightDp = info.heightPixels / info.scaleFactor;

        info.widthInches = info.widthPixels / info.widthDpi;
        info.heightInches = info.heightPixels / info.heightDpi;
        info.deviceInches = Math.sqrt((info.widthInches * info.widthInches)
                + (info.heightInches * info.heightInches));

        int screenLayout = context.getResources().getConfiguration().screenLayout;
        if ((screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_XLARGE) {
            info.screenSize = "xlarge";
        } else if ((screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE) {
            info.screenSize = "large";
        } else if ((screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_NORMAL) {
            info.screenSize = "normal";
        } else if ((screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_SMALL) {
            info.screenSize = "small";
        } else {
            info.screenSize = "undefined";
        }

        info.sdkInt = Build.VERSION.SDK_INT;
        info.sdkName = Build.VERSION.RELEASE;

        return info;
    }

    /**
     * 检测网络是否可用
     * @return
     */
    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    /**
     * 获取当前网络类型
     * @return 0：没有网络   1：WIFI网络   2：移动网络
     */
    public static final int NO_CONNECT = 0;
    public static final int NETTYPE_WIFI = 1;
    public static final int NETTYPE_MOBILE = 2;
    public static final int NETTYPE_UNKNOW = 3;
    public static int getNetworkType(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo == null) {
            return NO_CONNECT;
        }
        int nType = networkInfo.getType();
        if (nType == ConnectivityManager.TYPE_MOBILE) {
            return NETTYPE_MOBILE;
        } else if (nType == ConnectivityManager.TYPE_WIFI) {
            return NETTYPE_WIFI;
        }
        return NETTYPE_UNKNOW;
    }

    public static void openAppInMarket(Context context) {
        if (context != null) {
            String pckName = context.getPackageName();
            try {
                gotoMarket(context, pckName);
            } catch (Exception ex) {
                try {
                    String otherMarketUri = "http://market.android.com/details?id="
                            + pckName;
                    Intent intent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse(otherMarketUri));
                    context.startActivity(intent);
                } catch (Exception e) {

                }
            }
        }
    }

    private static void gotoMarket(Context context, String pck) {
        if (!isHaveMarket(context)) {
            Toast.makeText(context, "你手机中没有安装应用市场！", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("market://details?id=" + pck));
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        }
    }

    public static boolean isHaveMarket(Context context) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.APP_MARKET");
        PackageManager pm = context.getPackageManager();
        List<ResolveInfo> infos = pm.queryIntentActivities(intent, 0);
        return infos.size() > 0;
    }

    public static String saveImageToGallery(Context context, Bitmap bmp, Bitmap.CompressFormat format, String dir, String fileName) {
        // 首先保存图片
        File appDir = new File(Environment.getExternalStorageDirectory(), dir);
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        if(fileName == null) {
            fileName = System.currentTimeMillis() + "";
        }
        if(format == Bitmap.CompressFormat.JPEG){
            fileName = fileName + ".jpg";
        }else{
            fileName = fileName + ".png";
        }
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(format, 100, fos);
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

//        // 其次把文件插入到系统图库
//        try {
//            MediaStore.Images.Media.insertImage(context.getContentResolver(),
//                    file.getAbsolutePath(), fileName, null);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
        // 最后通知图库更新
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri contentUri = Uri.fromFile(file);
        mediaScanIntent.setData(contentUri);
        context.sendBroadcast(mediaScanIntent);
        return file.getAbsolutePath();
    }

    //通过反射获取系统的硬件信息
    public static String getMobileInfo() {
        StringBuffer sb = new StringBuffer();
        try {
            Field[] fields = Build.class.getDeclaredFields();
            for(Field field: fields){
                field.setAccessible(true);
                String name = field.getName();
                String value = field.get(null).toString();
                sb.append(name+"="+value);
                sb.append("\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public static String getDeviceInfo(Context context) {
        try{
            StringBuilder sb = new StringBuilder();
            android.telephony.TelephonyManager tm = (android.telephony.TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE);

            //IMEI
            String device_id = tm.getDeviceId();

            android.net.wifi.WifiManager wifi = (android.net.wifi.WifiManager) context.getSystemService(Context.WIFI_SERVICE);

            //MAC地址
            String mac = wifi.getConnectionInfo().getMacAddress();
            sb.append("mac=").append(mac).append(" ");

            if( TextUtils.isEmpty(device_id) ){
                device_id = mac;
            }

            if( TextUtils.isEmpty(device_id) ){
                device_id = android.provider.Settings.Secure.getString(context.getContentResolver(),android.provider.Settings.Secure.ANDROID_ID);
            }

            sb.append("device_id=").append(device_id);
            return sb.toString();
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
