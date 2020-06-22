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

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.htjy.baselibrary.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class CustomDatePickerWeekPopWindow extends PopupWindow {
    private static final String TAG = "CustomDatePickerPopWind";

    private Context context;

    private TextView tv_sure, tv_cancel, tv_month;
    private CustomDateWeekPicker cdp;

    private onSureListener listener;

    private Calendar calendar;
    private SimpleDateFormat format;
    private int selectYear, selectWeek;
    private Calendar current;

    public CustomDatePickerWeekPopWindow(final Activity context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.custom_datepicker_dialog, null);
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
        // mPopupWindow.setAnimationStyle(android.R.style.Animation_Dialog);
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

        format = new SimpleDateFormat("yyyy年MM月");
        calendar = Calendar.getInstance();
        tv_month.setText(format.format(calendar.getTime()));

        cdp.addChangingListener((year, week) -> {
            this.selectYear = year;
            this.selectWeek = week;
            LogUtils.d(TAG, "selectYear: " + selectYear + ",selectWeek: " + selectWeek);

            Calendar calendar = Calendar.getInstance();
            calendar.setFirstDayOfWeek(Calendar.MONDAY);//设置每周的第一天为星期一
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
            calendar.setMinimalDaysInFirstWeek(7);  //设置每周最少为7天
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.WEEK_OF_YEAR, week);

            tv_month.setText(format.format(calendar.getTime()));
        });
        View.OnClickListener clickListener = v -> {
            if (v == tv_sure) {
                if (listener != null) {
                    listener.dateFinish(selectYear, selectWeek);
                }
            }
            dismiss();
        };

        tv_sure.setOnClickListener(clickListener);
        tv_cancel.setOnClickListener(clickListener);
    }

    /**
     * 设置点击确认的事件
     *
     * @param listener
     */
    public void setOnSureListener(onSureListener listener) {
        this.listener = listener;
    }

    public void setCurrent(Calendar current) {
        this.calendar = current;
        cdp.setCurrentItems(calendar);
    }


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
}
