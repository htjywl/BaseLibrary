package com.htjy.baselibrary.widget.datePicker;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.htjy.baselibrary.R;
import com.htjy.baselibrary.utils.ScreenUtils;
import com.htjy.baselibrary.utils.TimeUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class CustomMonthDayDatePickerPopWindow extends PopupWindow {
    private static final String TAG = "CustomDatePickerPopWind";

    private Context context;

    private TextView tv_sure, tv_cancel, tv_month;
    private CustomMonthDayDatePicker cdp;


    private Calendar calendar;
    private SimpleDateFormat format;
    private int selectYear, selectWeek;
    private Calendar current;
    private onDateListener listener;
    private Calendar c = Calendar.getInstance();
    private boolean isShowDay = true;
    private boolean isShowMonth = true;
    private boolean isShowYear = true;
    private boolean isLimitDay;

    private SimpleDateFormat sdfFrom;
    private Calendar nowData;

    public CustomMonthDayDatePickerPopWindow(final Activity context,boolean isShowYear,boolean isShowMonth,boolean isShowDay) {
        this.isShowDay = isShowDay;
        this.isShowMonth = isShowMonth;
        this.isShowYear = isShowYear;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.custom_month_day_datepicker_dialog, null);
        // 设置SelectPicPopupWindow的View
        this.setContentView(contentView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(ScreenUtils.getScreenWidth());
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        // 刷新状态
        this.update();
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0000000000);
        // 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
        this.setBackgroundDrawable(dw);
        //this.setAnimationStyle(android.R.style.Animation_Dialog);
        // 设置SelectPicPopupWindow弹出窗体动画效果
        this.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                Window window = context.getWindow();
                WindowManager.LayoutParams lp = window.getAttributes();
                lp.alpha = 1f;// 透明度
                window.setAttributes(lp);
            }
        });

        this.context = context;

        tv_month = contentView.findViewById(R.id.tv_month);
        tv_sure = contentView.findViewById(R.id.tv_sure);
        tv_cancel = contentView.findViewById(R.id.tv_cancel);


        cdp = contentView.findViewById(R.id.cdp);
        //cdp.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,));
        format = new SimpleDateFormat("yyyy年MM月");
        calendar = Calendar.getInstance();
        tv_month.setText(format.format(calendar.getTime()));
        cdp.setLimit(isLimitDay);
        cdp.setIsShow(isShowYear, isShowMonth, isShowDay);
        cdp.setDate(c);

        cdp.setNowData(nowData);


        //月日
        if (!isShowYear && isShowMonth && isShowDay) {
            sdfFrom = new SimpleDateFormat("MM月dd日", Locale.getDefault());
        } else if (isShowYear && isShowMonth && isShowDay) {
            sdfFrom = new SimpleDateFormat("yyyy年MM月dd日 E", Locale.getDefault());
        } else if (!isShowDay && isShowYear && isShowMonth) {
            sdfFrom = new SimpleDateFormat("yyyy年MM月", Locale.getDefault());
        }


        String string = sdfFrom.format(c.getTime());

        tv_month.setText(string);
        cdp.addChangingListener(new CustomMonthDayDatePicker.ChangingListener() {

            @Override
            public void onChange(Calendar c1) {
                c = c1;
                String string = sdfFrom.format(c.getTime());
                tv_month.setText(string);

            }
        });

        View.OnClickListener clickListener = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (v == tv_sure) {
                    if (listener != null) {
                        //去掉时分秒
                        String string = sdfFrom.format(c.getTime());
                        Date date = TimeUtils.string2Date(string, sdfFrom);
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(date);
                        listener.dateFinish(calendar);
                    }

                }
                dismiss();
            }
        };

        tv_sure.setOnClickListener(clickListener);
        tv_cancel.setOnClickListener(clickListener);
    }


    /*public void setCurrent(Calendar current) {
        this.calendar = current;
        cdp.setCurrentItems(calendar);
    }*/


    public interface onSureListener {
        void dateFinish(int year, int week);
    }

    public void show(View view) {
        Window window = ((Activity) context).getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.alpha = 0.5f;// 透明度
        window.setAttributes(lp);
        setAnimationStyle(R.style.default_popupWindow_anim_style);
        showAtLocation(view, Gravity.BOTTOM, 0, 0);
    }

    public void showWithNoAlpha(View view,float alpha) {
        Window window = ((Activity) context).getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.alpha = alpha;// 透明度
        window.setAttributes(lp);
        setAnimationStyle(R.style.default_popupWindow_anim_style);
        showAtLocation(view, Gravity.BOTTOM, 0, 0);
    }

    /**
     * 设置限制日期，设置后，不能选择设置的开始日期以前的日期
     *
     * @param c
     */
    public void setNowData(Calendar c) {
        if (cdp != null)
            cdp.setNowData(c);
        this.nowData = c;

    }
    public void setDate(Calendar calendar){
        this.c = calendar;
    }
   /* *//**
     * popwindow不可以用 dialog可以用 因为生命周期不同
     * @param isShowYear
     * @param isShowMonth
     * @param isShowday
     *//*
    public void setIsShow(boolean isShowYear, boolean isShowMonth, boolean isShowday) {
        this.isShowDay = isShowday;
        this.isShowMonth = isShowMonth;
        this.isShowYear = isShowYear;
    }*/

    /**
     * 设置点击确认的事件
     *
     * @param listener
     */
    public void addDateListener(onDateListener listener) {
        this.listener = listener;
    }


    public interface onDateListener {
        void dateFinish(Calendar c);
    }

    public void setLimitDay(boolean limitDay) {
        this.isLimitDay = limitDay;
    }
}
