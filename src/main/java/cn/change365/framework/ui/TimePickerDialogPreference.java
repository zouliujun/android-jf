package cn.change365.framework.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.preference.DialogPreference;
import android.text.format.DateUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TimePicker;

/**
 * Created by Jack on 2015/10/27.
 */
public class TimePickerDialogPreference extends DialogPreference {

    private TimePicker timePicker;
    private static final String DEFAULT_VALUE = "00:00";
    private String currenTime;

    public TimePickerDialogPreference(Context context, AttributeSet attrs) {
        super(context, attrs);

        setPositiveButtonText(android.R.string.ok);
        setNegativeButtonText(android.R.string.cancel);

        setDialogIcon(null);
    }

    @Override
    protected View onCreateDialogView() {
        timePicker = new TimePicker(getContext());
        timePicker.setIs24HourView(true);
        return timePicker;
    }

    public String getTimeString(){
        int hour = timePicker.getCurrentHour();
        String sHour;
        if(hour < 10){
            sHour = "0"+hour;
        } else {
            sHour = String.valueOf(hour);
        }

        int minute = timePicker.getCurrentMinute();
        String sMinute;
        if(minute < 10){
            sMinute = "0"+minute;
        } else {
            sMinute = String.valueOf(minute);
        }
        return sHour + ":" + sMinute;
    }

    public void setTime(String str){
        if(str != null){
            String[] item = str.split(":");
            if(item.length == 2){
                timePicker.setCurrentHour(Integer.parseInt(item[0]));
                timePicker.setCurrentMinute(Integer.parseInt(item[1]));
            }
        }
    }

    @Override
    protected void onDialogClosed(boolean positiveResult) {
        // When the user selects "OK", persist the new value
        if (positiveResult) {
            persistString(getTimeString());
        }
    }

    @Override
    protected void onBindDialogView(View view) {
        super.onBindDialogView(view);
        setTime(currenTime);
    }

    @Override
    protected void onSetInitialValue(boolean restorePersistedValue, Object defaultValue) {

        if (restorePersistedValue) {
            // Restore existing state
            currenTime = this.getPersistedString(DEFAULT_VALUE);
        } else {
            // Set default state from the XML attribute
            currenTime = (String) defaultValue;
            persistString(currenTime);
        }
        setSummary(currenTime);
    }

    @Override
    protected Object onGetDefaultValue(TypedArray a, int index) {
        return a.getString(index);
    }



}
