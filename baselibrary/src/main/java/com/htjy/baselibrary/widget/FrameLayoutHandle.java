package com.htjy.baselibrary.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;

/**
 * Created by Administrator on 2018/4/26.
 */

public class FrameLayoutHandle extends FrameLayout {
    public FrameLayoutHandle(@NonNull Context context) {
        super(context);
    }

    public FrameLayoutHandle(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public FrameLayoutHandle(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public interface DispatchEvent {
        void dispatchTouchEvent(MotionEvent ev);
    }

    private DispatchEvent dispatchEvent;

    public void setDispatchEvent(DispatchEvent dispatchEvent) {
        this.dispatchEvent = dispatchEvent;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (dispatchEvent != null) {
            dispatchEvent.dispatchTouchEvent(ev);
        }
        return super.dispatchTouchEvent(ev);
    }
}
