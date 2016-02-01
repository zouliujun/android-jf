package cn.change365.framework.utils;

import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import cn.change365.framework.R;


/**
 * Created by Jack on 2015/8/26.
 */
public class AnimUtils {

    public static Animation getClickAnim(Context context){
        return AnimationUtils.loadAnimation(context, R.anim.click_item);
    }
}
