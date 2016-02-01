package cn.change365.framework.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.change365.framework.R;


/**
 * Created by Jack on 2016/1/10.
 */
public class EmptyLayout extends RelativeLayout{

    protected TextView msgTv;
    protected Button msgBtn;


    public static final int TYPE_NEED_LOGIN = 1;

    public EmptyLayout(Context context) {
        super(context);
        init(context);
    }

    public EmptyLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.empty, this, true);
        msgTv = (TextView)this.findViewById(R.id.msgTv);
        msgBtn = (Button)this.findViewById(R.id.msgBtn);
        setVisibility(GONE);
    }

    public void showMsg(int strRes){
        msgTv.setText(strRes);
        msgTv.setVisibility(VISIBLE);
        msgBtn.setVisibility(GONE);
        setVisibility(VISIBLE);
    }

    public void showButton(int strId, OnClickListener listener){
        msgBtn.setText(strId);
        msgBtn.setVisibility(VISIBLE);
        msgTv.setVisibility(GONE);
        msgBtn.setOnClickListener(listener);
        setVisibility(VISIBLE);
    }

    public void hide(){
        setVisibility(GONE);
    }
}
