package com.htjy.baselibrary.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
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
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (!showHeader) {
            View headerView = null;
            for (int index = 0; index < getChildCount(); index++) {
                View view = getChildAt(index);
                LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
                if (layoutParams.header) {
                    headerView = view;
                    break;
                }
            }
            if (headerView != null && getPaddingTop() != -headerView.getMeasuredHeight()) {
                setPadding(0, -headerView.getMeasuredHeight(), 0, 0);
            }
        }
    }

    private boolean showHeader = false;

    /**
     * 别闹，第一次才有效
     */
    private void showHeader() {
        if (!showHeader) {
            showHeader = true;
            ValueAnimator valueAnimator = ValueAnimator.ofInt(getPaddingTop(), 0);
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
    private boolean hasIntoMove = false;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                hasIntoMove = false;
                break;
            case MotionEvent.ACTION_MOVE:
                if (hasIntoMove) {
                    if (ev.getY() > lastY) {
                        showHeader();
                    }
                }
                hasIntoMove = true;
                lastY = ev.getY();
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
