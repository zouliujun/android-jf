package cn.change365.framework.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.change365.framework.R;

/**
 * Created by Jack on 2015/11/16.
 */
public class SplitLine extends LinearLayout{

    private String text;
    private Position textPosition = Position.LEFT;
    private int splitColor = Color.WHITE;
    private float textSize;
    private int textColor;


    public enum Position{
        CENTER,
        LEFT,
        RIGHT
    }

    public SplitLine(Context context) {
        super(context);

        init(context);
    }

    public SplitLine(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.SplitLine, 0, 0);
        text = a.getString(R.styleable.SplitLine_android_text);
        textPosition = Position.values()[a.getInt(R.styleable.SplitLine_textPosition, 0)];
        splitColor = a.getColor(R.styleable.SplitLine_splitColor, Color.WHITE);
        textSize = a.getDimension(R.styleable.SplitLine_android_textSize, 12);
        textColor = a.getColor(R.styleable.SplitLine_android_textColor, Color.WHITE);
        a.recycle();

        init(context);
    }

    private void init(Context context){
        LayoutParams layoutParams =
                new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        setLayoutParams(layoutParams);
        setOrientation(HORIZONTAL);

        if(text == null){//no text
            this.addView(getSplitLine(context));
            return;
        }

        if(Position.CENTER == textPosition){
            this.addView(getSplitLine(context));
            this.addView(getText(context));
            this.addView(getSplitLine(context));
        }else if(Position.RIGHT == textPosition){
            this.addView(getSplitLine(context));
            this.addView(getText(context));
        }else{//left
            this.addView(getText(context));
            this.addView(getSplitLine(context));
        }
    }

    private View getSplitLine(Context context){
        View split = new View(context);
        LayoutParams layoutParams =
                new LayoutParams(0, 1, 1);
        layoutParams.setMargins(8, 0, 8, 0);
        layoutParams.gravity = Gravity.CENTER_VERTICAL;
        split.setLayoutParams(layoutParams);
        split.setBackgroundColor(splitColor);
        return split;
    }

    private TextView getText(Context context){
        TextView textView = new TextView(context);
        LayoutParams layoutParams =
                new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(8, 0, 8, 0);
        textView.setLayoutParams(layoutParams);
        textView.setText(text);
        if(textSize > 0){
            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        }
        if(textColor > 0){
            textView.setTextColor(textColor);
        }
        return textView;
    }

}
