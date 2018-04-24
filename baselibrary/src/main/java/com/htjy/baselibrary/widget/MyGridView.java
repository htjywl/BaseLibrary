package com.htjy.baselibrary.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.GridView;

public class MyGridView extends GridView {

    private OnTouchInvalidPositionListener mTouchInvalidPosListener;

    /**
     * 自定义gridView用于嵌套在scrollview里面<br/>
     * 在scrollview里面需要设置一下代码,让scrollview置顶;<br/>
     * sv = (ScrollView) findViewById(R.id.act_solution_4_sv);<br/>
     * sv.smoothScrollTo(0, 0);<br/>
     *
     * @param context
     */
    public MyGridView(Context context) {
        super(context);
    }

    public MyGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * 重写该方法，达到使ListView适应ScrollView的效果
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * 点击空白区域时的响应和处理接口
     */
    public void setOnTouchInvalidPositionListener(OnTouchInvalidPositionListener listener) {
        mTouchInvalidPosListener = listener;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (mTouchInvalidPosListener == null) {
            return super.onTouchEvent(event);
        }

        if (!isEnabled()) {
            // A disabled view that is clickable still consumes the touch
            // events, it just doesn't respond to them.
            return isClickable() || isLongClickable();
        }

        final int motionPosition = pointToPosition((int) event.getX(), (int) event.getY());


        if (motionPosition == INVALID_POSITION) {
            super.onTouchEvent(event);
            return mTouchInvalidPosListener.onTouchInvalidPosition(event.getActionMasked());
        }

        return super.onTouchEvent(event);
    }

    public interface OnTouchInvalidPositionListener {
        /**
         * motionEvent 可使用 MotionEvent.ACTION_DOWN 或者 MotionEvent.ACTION_UP等来按需要进行判断
         *
         * @return 是否要终止事件的路由
         */
        boolean onTouchInvalidPosition(int motionEvent);
    }
}