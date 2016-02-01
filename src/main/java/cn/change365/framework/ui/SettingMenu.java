package cn.change365.framework.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.change365.framework.R;

/**
 * Created by Jack on 2015/8/18.
 */
public class SettingMenu extends RelativeLayout {


    private TextView labelTV;
    private TextView iconTV;

    public SettingMenu(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.SettingMenu, 0, 0);
        String label = a.getString(R.styleable.SettingMenu_label);
        int icon = a.getResourceId(R.styleable.SettingMenu_icon, -1);
        float textSize = a.getDimension(R.styleable.SettingMenu_textSize, -1);
        a.recycle();

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.setting_menu, this, true);

        labelTV = (TextView) this.findViewById(R.id.settingLabel);
        labelTV.setText(label);

        iconTV = (TextView) this.findViewById(R.id.settingIcon);
        if(icon == -1){
            iconTV.setVisibility(View.GONE);
        }else{
            iconTV.setBackgroundResource(icon);
        }

        if(textSize != -1){
            textSize = px2Dp(textSize);
            labelTV.setTextSize(textSize);
            iconTV.setTextSize(textSize);
        }

    }

    private float px2Dp(float px) {
        return px / getContext().getResources().getDisplayMetrics().density;
    }



}
