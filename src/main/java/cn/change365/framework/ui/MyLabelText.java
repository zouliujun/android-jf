package cn.change365.framework.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.change365.framework.R;

/**
 * Created by Jack on 2015/8/18.
 */
public class MyLabelText extends LinearLayout {


    private TextView labelTV;
    private TextView valueTV;

    public MyLabelText(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.LabelText, 0, 0);
        String label = a.getString(R.styleable.LabelText_label);
        String value = a.getString(R.styleable.LabelText_value);
        int labelColor = a.getColor(R.styleable.LabelText_labelColor, Color.BLACK);
        int valueColor = a.getColor(R.styleable.LabelText_valueColor, Color.BLACK);
        a.recycle();

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.label_text_layout, this, true);

        labelTV = (TextView) this.findViewById(R.id.labelTV);
//        if(labelColor != -1){
            labelTV.setTextColor(labelColor);
//        }
        labelTV.setText(label);

        valueTV = (TextView) this.findViewById(R.id.valueTV);
//        if(valueColor != -1){
            valueTV.setTextColor(valueColor);
//        }
        valueTV.setText(value);

    }

    public void setValue(String value){
        valueTV.setText(value);
    }


}
