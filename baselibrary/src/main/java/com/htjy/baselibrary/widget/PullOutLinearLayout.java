package com.htjy.baselibrary.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;

import com.htjy.baselibrary.R;


/**
 * 拉取弹出操作栏
 *
 * @author linyibiao
 * @since 2017/11/17 15:24
 */
public class PullOutLinearLayout extends LinearLayout {

    public PullOutLinearLayout(Context context) {
        this(context, null);
    }

    public PullOutLinearLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PullOutLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(LinearLayout.VERTICAL);
        getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                getViewTreeObserver().removeOnPreDrawListener(this);
                showHeader(false);
                return true;
            }
        });
    }

    private ValueAnimator valueAnimator;

    public void showHeader(boolean showHeader) {
        if (valueAnimator != null && valueAnimator.isRunning()) {
            return;
        }
        View headerView = null;
        for (int index = 0; index < getChildCount(); index++) {
            View view = getChildAt(index);
            LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
            if (layoutParams.header) {
                headerView = view;
                break;
            }
        }
        if (headerView != null && ((showHeader && getPaddingTop() != 0) || (!showHeader && getPaddingTop() != -headerView.getMeasuredHeight()))) {
            valueAnimator = ValueAnimator.ofInt(getPaddingTop(), showHeader ? 0 : -headerView.getMeasuredHeight());
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    setPadding(0, (Integer) animation.getAnimatedValue(), 0, 0);
                }
            });
            valueAnimator.start();
        }
    }

    private float lastY;
    private static final float distance = 10;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                lastY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                if (ev.getY() > lastY + distance) {
                    showHeader(true);
                    lastY = ev.getY();
                } else if (ev.getY() < lastY - distance) {
                    showHeader(false);
                    lastY = ev.getY();
                }
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        if (getOrientation() == HORIZONTAL) {
            return new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        } else if (getOrientation() == VERTICAL) {
            return new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        return null;
    }

    @Override
    protected LayoutParams generateLayoutParams(ViewGroup.LayoutParams lp) {
        return new LayoutParams(lp);
    }


    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return p instanceof LayoutParams;
    }

    public static class LayoutParams extends LinearLayout.LayoutParams {

        public boolean header;

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
            TypedArray typedArray = c.obtainStyledAttributes(attrs, R.styleable.PullOutLinearLayout_Layout);
            header = typedArray.getBoolean(R.styleable.PullOutLinearLayout_Layout_pullout_header, false);
            typedArray.recycle();
        }

        public LayoutParams(int width, int height) {
            super(width, height);
        }

        public LayoutParams(int width, int height, float weight) {
            super(width, height, weight);
        }

        public LayoutParams(ViewGroup.LayoutParams p) {
            super(p);
        }

        public LayoutParams(MarginLayoutParams source) {
            super(source);
        }

    }

}