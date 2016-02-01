package cn.change365.framework.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatRadioButton;
import android.util.AttributeSet;

import cn.change365.framework.R;


/**
 * Created by Jack on 2016/1/11.
 */
public class MyRadioButton extends AppCompatRadioButton {

    private String value;

    public MyRadioButton(Context context) {
        super(context);
    }

    public MyRadioButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        init(context, attrs);
    }

    public MyRadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.MyRadioButton, 0, 0);
        value = a.getString(R.styleable.MyRadioButton_android_value);
        a.recycle();
    }

    public String getValue() {
        return value;
    }
}
