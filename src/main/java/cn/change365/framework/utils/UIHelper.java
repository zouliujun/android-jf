package cn.change365.framework.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import cn.change365.framework.R;
import cn.change365.framework.ui.BadgeView;
import cn.change365.framework.ui.SimpleBackActivity;

/**
 * Created by Jack on 2015/8/26.
 */
public class UIHelper {

    public static ProgressDialog getWaitDialog(Context context, int message) {
        ProgressDialog waitDialog = new ProgressDialog(context);
        String msg = context.getString(message);
        if (!StringUtils.isEmptyStr(msg)) {
            waitDialog.setMessage(msg);
        }
        return waitDialog;
    }

    public static Dialog getConfirmDialog(Context context, int msgId,
                                          DialogInterface.OnClickListener positiveAction,
                                          DialogInterface.OnClickListener negativeAction){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false)
                .setMessage(msgId)
                .setTitle(R.string.base_prompt)
                .setPositiveButton(R.string.base_confirm, positiveAction);
        if(negativeAction != null) {
            builder.setNegativeButton(R.string.base_cancel, negativeAction);
        }else{
            builder.setNegativeButton(R.string.base_cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
        }
        return builder.create();
    }

    public static void showConfirmDialog(Context context, int msgId,
                                         DialogInterface.OnClickListener positiveAction,
                                         DialogInterface.OnClickListener negativeAction){
        getConfirmDialog(context, msgId, positiveAction, negativeAction).show();
    }

    public static Dialog getMessageDialog(Context context, int msgId){
        return getMessageDialog(context, msgId, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
    }

    public static Dialog getMessageDialog(Context context, int msgId, DialogInterface.OnClickListener okAction){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(msgId)
                .setTitle(R.string.base_prompt)
                .setPositiveButton(R.string.base_confirm, okAction);
        return builder.create();
    }

    public static void showMessageDialog(Context context, int msgId, DialogInterface.OnClickListener okAction){
        getMessageDialog(context, msgId, okAction).show();
    }

    public static void showMessageDialog(Context context, int msgId){
        getMessageDialog(context, msgId).show();
    }

    public static void showSimpleBackActivity(Context context, Class clazz, Bundle bundle, String tag, String title){
        Intent intent = new Intent(context, SimpleBackActivity.class);
        intent.putExtra(SimpleBackActivity.ARGS_FRAGMENT_CLASS, clazz);
        intent.putExtra(SimpleBackActivity.ARGS_FRAGMENT_BUNDLE, bundle);
        intent.putExtra(SimpleBackActivity.ARGS_TAG, tag);
        intent.putExtra(SimpleBackActivity.ARGS_TITLE, title);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }



    public static BadgeView getBadgeView(Context context, View target, int gravity){
        BadgeView badgeView = new BadgeView(context);
        badgeView.setTargetView(target);
        badgeView.setBadgeGravity(gravity);
        badgeView.setCircleBackground(8, Color.RED);
        return badgeView;
    }

    public static void showInfo(Context context, int msg){
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

}
