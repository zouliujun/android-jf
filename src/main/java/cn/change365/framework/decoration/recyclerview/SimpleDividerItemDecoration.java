package cn.change365.framework.decoration.recyclerview;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Jack on 2015/7/3.
 */
public class SimpleDividerItemDecoration extends RecyclerView.ItemDecoration {

    private Paint p = new Paint();
    private int mColor;

    public SimpleDividerItemDecoration(int color) {
        mColor = color;
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int left = parent.getLeft();
        int right = parent.getRight();

        int childCount = parent.getChildCount();
        p.setColor(mColor);
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            int bottom = child.getBottom();
            c.drawLine(left, bottom, right, bottom, p);
        }
    }
}
