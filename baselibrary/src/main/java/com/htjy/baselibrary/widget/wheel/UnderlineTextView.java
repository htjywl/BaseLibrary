package com.htjy.baselibrary.widget.wheel;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.util.TypedValue;

import com.htjy.baselibrary.R;

/**
 * Created by Jiang on 2017/11/17. 16:27
 * mail:jiangwei_android@163.com
 */

public class UnderlineTextView extends AppCompatTextView {
    //Paint即画笔，在绘图过程中起到了极其重要的作用，画笔主要保存了颜色，
    //样式等绘制信息，指定了如何绘制文本和图形，画笔对象有很多设置方法，
    //大体上可以分为两类，一类与图形绘制相关，一类与文本绘制相关
    private final Paint paint = new Paint();
    //下划线高度
    private int underlineHeight = 4;
    //下划线颜色
    private int underLineColor = ContextCompat.getColor(getContext(), R.color.tc_47aefe);
    private boolean mShowLine;

    //通过new创建实例是调用这个构造函数
    //这种情况下需要添加额外的一些函数供外部来控制属性，如set*(...);
    public UnderlineTextView(Context context) {
        this(context, null);
    }
    //通过XML配置但不定义style时会调用这个函数
    public UnderlineTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        //获取自定义属性

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.UnderlineTextView);
        //获取具体属性值
        underLineColor = typedArray.getColor(R.styleable.UnderlineTextView_underline_color,ContextCompat.getColor(getContext(), R.color.tc_47aefe));
        underlineHeight = (int)typedArray.getDimension(R.styleable.UnderlineTextView_underline_height,
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics()));
    }
    //通过XML配置且定义样式时会调用这个函数
    public UnderlineTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
    //防止下划线高度大到一定值时会覆盖掉文字，需从写此方法
    @Override
    public void setPadding(int left, int top, int right, int bottom) {
        if (mShowLine)
        super.setPadding(left, top+ underlineHeight, right, bottom + underlineHeight);
    }
    //绘制下划线
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //设置下划线颜色
        paint.setColor(underLineColor);
        if (mShowLine){
            RectF oval1 = new RectF(24, getHeight() - underlineHeight, getWidth()-24, getHeight());
            RectF oval2 = new RectF(24, 0, getWidth()-24, underlineHeight);
            canvas.drawRoundRect(oval1, 2, 2, paint);
            canvas.drawRoundRect(oval2, 2, 2, paint);
        }
        //float left, float top, float right, float bottom

        //canvas.drawRect(24, getHeight() - underlineHeight, getWidth()-24, getHeight(), paint);
    }

    public void setShowLine(boolean showLine) {
        mShowLine = showLine;
        invalidate();
    }
}
