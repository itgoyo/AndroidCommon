package com.ywg.androidcommon.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;

/**
 * 不滚动ListView
 */
public class NoScrollListView extends ListView {

    private boolean mEnabled = true;

    public NoScrollListView(Context context){
        super(context);
    }

    public NoScrollListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public void setEnabled(boolean enabled) {
        mEnabled = enabled;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return mEnabled && super.onTouchEvent(ev);
    }

    /**
     * 设置不滚动
     */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = View.MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

}