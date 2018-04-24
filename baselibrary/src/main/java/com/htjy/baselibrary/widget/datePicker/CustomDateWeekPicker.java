package com.htjy.baselibrary.widget.datePicker;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.htjy.baselibrary.R;
import com.htjy.baselibrary.utils.LogUtils;
import com.htjy.baselibrary.widget.wheel.AbstractWheel;
import com.htjy.baselibrary.widget.wheel.AbstractWheelTextAdapter;
import com.htjy.baselibrary.widget.wheel.ArrayWheelAdapter;
import com.htjy.baselibrary.widget.wheel.OnWheelScrollListener;
import com.htjy.baselibrary.widget.wheel.WheelVerticalView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CustomDateWeekPicker extends LinearLayout {
    private static final String TAG = "CustomDatePicker";

    private Context context;
    private WheelVerticalView yearWheelView;
    private WheelVerticalView weekWheelView;

    private ChangingListener listener;
    private SimpleDateFormat format;

    private int currentYear, currentWeek;

    public CustomDateWeekPicker(final Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        View v = LayoutInflater.from(context).inflate(R.layout.custom_datepicker, null);
        yearWheelView = v.findViewById(R.id.year);
        weekWheelView = v.findViewById(R.id.week);

        format = new SimpleDateFormat("yyyy年MM月dd日");
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        currentYear = cal.get(Calendar.YEAR);
        currentWeek = cal.get(Calendar.WEEK_OF_YEAR);

        ArrayList<String> yearList = new ArrayList<>();
        yearList.add((currentYear - 1) + "年");
        yearList.add(currentYear + "年");

        ArrayWheelAdapter adapter = new ArrayWheelAdapter(context, yearList.toArray());
        this.yearWheelView.setViewAdapter(adapter);
        setTextColor(adapter);

        setDayAdapter(currentYear, currentWeek);

        OnWheelScrollListener scrollListener1 = new OnWheelScrollListener() {

            @Override
            public void onScrollingStarted(AbstractWheel wheel) {
            }

            @Override
            public void onScrollingFinished(AbstractWheel wheel) {
                currentYear = yearWheelView.getCurrentItem() - 1 + Calendar.getInstance().get(Calendar.YEAR);
                setDayAdapter(currentYear, currentWeek);
                doListener();
            }
        };

        OnWheelScrollListener scrollListener2 = new OnWheelScrollListener() {

            @Override
            public void onScrollingStarted(AbstractWheel wheel) {
            }

            @Override
            public void onScrollingFinished(AbstractWheel wheel) {
                currentWeek = weekWheelView.getCurrentItem() + 1;
                doListener();
            }
        };

        this.yearWheelView.addScrollingListener(scrollListener1);
        this.weekWheelView.addScrollingListener(scrollListener2);

        addView(v);
    }


    private void doListener() {
        if (listener != null) {
            listener.onChange(currentYear, currentWeek);
        }
    }

    public interface ChangingListener {
        void onChange(int year, int week);
    }


    private void setDayAdapter(int year, int week) {
        ArrayList<String> weekList = new ArrayList<>();
        for (int i = 1; i < 53; i++) {
            Calendar calendar = Calendar.getInstance();
            calendar.setFirstDayOfWeek(Calendar.MONDAY);//设置每周的第一天为星期一
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
            calendar.setMinimalDaysInFirstWeek(7);  //设置每周最少为7天
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.WEEK_OF_YEAR, i);
            String start = format.format(calendar.getTime());
            if (start.substring(0, 4).equals(year + "")) {
                calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
                String end = format.format(calendar.getTime());
                if (i == week)
                    LogUtils.d(TAG, "start:" + start + " - end:" + end);
                weekList.add(start.substring(5) + "-" + end.substring(5));
            }
        }
        ArrayWheelAdapter dayAdapter = new ArrayWheelAdapter(context, weekList.toArray());
        dayAdapter.setShowLineItem(week-1);
        this.weekWheelView.setViewAdapter(dayAdapter);
        setTextColor(dayAdapter);
    }


    /**
     * 设置初始日期
     *
     * @param c
     */
    public void setCurrentItems(Calendar c) {
        LogUtils.d(TAG, "currentYear:" + currentYear + ", selectYear:" + c.get(Calendar.YEAR) + ",nowYear:" + Calendar.getInstance().get(Calendar.YEAR));
        yearWheelView.setCurrentItem(c.get(Calendar.YEAR) == Calendar.getInstance().get(Calendar.YEAR) ? 1 : 0);
        int d = c.get(Calendar.WEEK_OF_YEAR);
        weekWheelView.setCurrentItem(d - 1);
    }

    /**
     * 设置字体颜色
     *
     * @param adapter
     */
    public void setTextColor(AbstractWheelTextAdapter adapter) {
        adapter.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
    }


    /**
     * 设置日期改变的监听
     *
     * @param listener
     */
    public void addChangingListener(ChangingListener listener) {
        this.listener = listener;
    }

}
