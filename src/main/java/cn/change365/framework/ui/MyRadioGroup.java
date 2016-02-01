package cn.change365.framework.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

/**
 * Created by Jack on 2016/1/11.
 */
public class MyRadioGroup extends RadioGroup{

    public MyRadioGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public String getCheckValue(){
        RadioButton checkBtn = getCheckBtn();
        if(checkBtn != null && checkBtn instanceof MyRadioButton){
            return ((MyRadioButton) checkBtn).getValue();
        }
        return null;
    }

    public String getCheckText(){
        RadioButton checkBtn = getCheckBtn();
        if(checkBtn != null){
            return checkBtn.getText().toString();
        }
        return null;
    }

    public void checkByValue(String checkValue){
        if(checkValue == null || checkValue.isEmpty()){
            return;
        }
        int childCount = getChildCount();
        for(int i = 0; i < childCount; i++){
            View child = getChildAt(i);
            if(child instanceof MyRadioButton){
                MyRadioButton childBtn = (MyRadioButton) child;
                String value = childBtn.getValue();
                if(value.equals(checkValue)){
                    check(childBtn.getId());
                    return;
                }
            }
        }
    }

    private RadioButton getCheckBtn(){
        return (RadioButton)this.findViewById(getCheckedRadioButtonId());
    }
}
