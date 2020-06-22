package com.htjy.baselibrary.widget.datePicker;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.htjy.baselibrary.R;
import com.htjy.baselibrary.utils.temp.TimeUtils;
import com.htjy.baselibrary.widget.wheel.AbstractWheel;
import com.htjy.baselibrary.widget.wheel.AbstractWheelTextAdapter;
import com.htjy.baselibrary.widget.wheel.ArrayWheelAdapter;
import com.htjy.baselibrary.widget.wheel.OnWheelScrollListener;
import com.htjy.baselibrary.widget.wheel.WheelVerticalView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * <pre>
 *     author : jiangwei
 *     e-mail : jiangwei_android@163.com
 *     time   : 2017/08/14
 *     desc   :
 *     version: 1.0
 *     日期选择器
 * </pre>
 */
public class CustomMonthDayDatePicker extends LinearLayout {
    private WheelVerticalView year;
    private WheelVerticalView month;
    private WheelVerticalView day;
    private ChangingListener listener;

    private ArrayWheelAdapter yearAdapter, monthAdapter, dayAdapter;
    private Context context;
    private Calendar select_date;
    private Calendar now_date;
    private boolean limit;
    private final int MAX_YEAR = 2020;
    private final int MIN_YEAR = 1970;

    public CustomMonthDayDatePicker(final Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        View v = LayoutInflater.from(context).inflate(
                R.layout.custom_month_day_datepicker, null);
        v.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        year = (WheelVerticalView) v.findViewById(R.id.year);
        month = (WheelVerticalView) v.findViewById(R.id.month);
        day = (WheelVerticalView) v.findViewById(R.id.day);

        ArrayList<String> yearList = new ArrayList<>();

        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int yearNow = calendar.get(Calendar.YEAR);
        int monthNow = calendar.get(Calendar.MONTH) + 1;

        if (limit) {
            for (int i = MIN_YEAR; i <= yearNow; i++) {
                yearList.add(i + "年");
            }
        } else {
            for (int i = MIN_YEAR; i <= MAX_YEAR; i++) {
                yearList.add(i + "年");
            }
        }

        yearAdapter = new ArrayWheelAdapter(context, yearList.toArray());
        this.year.setViewAdapter(yearAdapter);
        setTextColor(yearAdapter);
        setTextSize(yearAdapter);
        setMonthAdapter(yearNow);
        setDayAdapter(yearNow, monthNow);

        this.year.setCyclic(true);
        this.month.setCyclic(true);
        this.day.setCyclic(true);

        setDate(Calendar.getInstance());

        OnWheelScrollListener scrollListener = new OnWheelScrollListener() {

            @Override
            public void onScrollingStarted(AbstractWheel wheel) {
                select_date = getDate();
            }

            @Override
            public void onScrollingFinished(AbstractWheel wheel) {

                if (now_date != null) {
                    Calendar date = getDate();

                    date.set(Calendar.SECOND,0);
                    date.set(Calendar.MILLISECOND,0);
                    now_date.set(Calendar.SECOND,0);
                    now_date.set(Calendar.MILLISECOND,0);
                    int compareTo = date.compareTo(now_date);

                    if (compareTo > 0) {
                        setDate(select_date);

                        Toast.makeText(getContext(), "不能选择此日期",
                                Toast.LENGTH_SHORT).show();

                    }
                }
                if (limit)
                    setMonthAdapter(year.getCurrentItem() + MIN_YEAR);
                setDayAdapter(year.getCurrentItem() + MIN_YEAR, month.getCurrentItem() + 1);

            }
        };

        //年
        OnWheelScrollListener scrollListener1 = new OnWheelScrollListener() {

            @Override
            public void onScrollingStarted(AbstractWheel wheel) {
                select_date = getDate();
            }

            @Override
            public void onScrollingFinished(AbstractWheel wheel) {
                if (now_date != null) {

                    Calendar date = getDate();


                    date.set(Calendar.SECOND,0);
                    date.set(Calendar.MILLISECOND,0);
                    now_date.set(Calendar.SECOND,0);
                    now_date.set(Calendar.MILLISECOND,0);
                    int compareTo = date.compareTo(now_date);

                    if (compareTo > 0) {
                        setDate(select_date);

                        Toast.makeText(getContext(), "不能选择此日期",
                                Toast.LENGTH_SHORT).show();

                    }

                }
                setDayAdapter(year.getCurrentItem() + MIN_YEAR, month.getCurrentItem() + 1);
            }
        };

        OnWheelScrollListener scrollListener2 = new OnWheelScrollListener() {

            @Override
            public void onScrollingStarted(AbstractWheel wheel) {
                select_date = getDate();

            }

            @Override
            public void onScrollingFinished(AbstractWheel wheel) {

                if (now_date != null) {
                    Calendar date = getDate();
                    date.set(Calendar.SECOND,0);
                    date.set(Calendar.MILLISECOND,0);
                    now_date.set(Calendar.SECOND,0);
                    now_date.set(Calendar.MILLISECOND,0);
                    String s = TimeUtils.date2String(date.getTime());
                    String s1 = TimeUtils.date2String(now_date.getTime());

                    int compareTo = date.compareTo(now_date);

                    if (compareTo > 0) {
                        setDate(select_date);
                        Toast.makeText(getContext(), "不能选择此日期",
                                Toast.LENGTH_SHORT).show();
                    }

                }
                doListener();

            }
        };
        this.month.addScrollingListener(scrollListener);
        this.year.addScrollingListener(scrollListener1);
        this.day.addScrollingListener(scrollListener2);

        addView(v);
    }


    private List getMonthList(int num) {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 1; i <= num; i++) {
            String d = i + "月";
            if (i < 10) {
                d = "\t" + d;
            }
            list.add(d);
        }
        return list;
    }

    private List getDayList(int num) {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 1; i <= num; i++) {
            String d = i + "日";
            if (i < 10) {
                d = "\t" + d;
            }
            list.add(d);
        }
        return list;
    }

    private void doListener() {
        if (listener != null) {
            listener.onChange(getDate());
        }

    }


    public void setLimit(boolean limit) {
        this.limit = limit;
    }

    public interface ChangingListener {
        void onChange(Calendar c);
    }

    public void setIsShow(boolean isShowYear, boolean isShowMonth, boolean isShowDay) {
        if (isShowDay) {
            day.setVisibility(VISIBLE);
        } else {
            day.setVisibility(GONE);
        }

        if (isShowMonth) {
            month.setVisibility(VISIBLE);
        } else {
            month.setVisibility(GONE);
        }

        if (isShowYear) {
            year.setVisibility(VISIBLE);
        } else {
            year.setVisibility(GONE);
        }
    }

    private void setMonthAdapter(int yearI) {
        if (limit && yearI == Calendar.getInstance().get(Calendar.YEAR)) {
            //当天
            int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
            monthAdapter = new ArrayWheelAdapter(context, getMonthList(month).toArray());
        } else {
            monthAdapter = new ArrayWheelAdapter(context, getMonthList(12).toArray());
        }

        this.month.setViewAdapter(monthAdapter);
        setTextColor(monthAdapter);
        setTextSize(monthAdapter);
        doListener();
    }


    private void setDayAdapter(int yearI, int monthI) {
        if (limit && yearI == Calendar.getInstance().get(Calendar.YEAR) && monthI == Calendar.getInstance().get(Calendar.MONTH) + 1) {
            int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
            dayAdapter = new ArrayWheelAdapter(context, getDayList(day).toArray());
        } else {
            switch (monthI) {
                case 1:
                case 3:
                case 5:
                case 7:
                case 8:
                case 10:
                case 12:
                    dayAdapter = new ArrayWheelAdapter(context, getDayList(31).toArray());
                    break;
                case 2:
                    if (yearI % 4 == 0) {
                        dayAdapter = new ArrayWheelAdapter(context, getDayList(29).toArray());
                    } else {
                        dayAdapter = new ArrayWheelAdapter(context, getDayList(28).toArray());
                    }
                    break;
                case 4:
                case 6:
                case 9:
                case 11:
                    dayAdapter = new ArrayWheelAdapter(context, getDayList(30).toArray());
                    break;
                default:
                    break;
            }
        }

        this.day.setViewAdapter(dayAdapter);
        setTextColor(dayAdapter);
        doListener();
    }

    /**
     * 设置初始日期
     *
     * @param c
     */
    public void setDate(Calendar c) {
        int y = c.get(Calendar.YEAR);
        int index1 = y - MIN_YEAR;
        year.setCurrentItem(index1);

        int m = c.get(Calendar.MONTH);
        month.setCurrentItem(m);

        int d = c.get(Calendar.DAY_OF_MONTH);
        setDayAdapter(y, m + 1);
        day.setCurrentItem(d - 1);
    }

    /**
     * 设置字体颜色
     *
     * @param adapter
     */
    public void setTextColor(AbstractWheelTextAdapter adapter) {
        adapter.setTextColor(context.getResources().getColor(R.color.tc_47aefe));
    }

    public void setTextSize(AbstractWheelTextAdapter adapter) {
        //adapter.setTextSize(SizeUtils.sp2px(14));
    }

    /**
     * 设置限制日期，设置后，不能选择设置的开始日期以前的日期
     *
     * @param c
     */
    public void setNowData(Calendar c) {
        now_date = c;
    }

    /**
     * 得到日期
     *
     * @return
     */
    public Calendar getDate() {
        int y = year.getCurrentItem() + MIN_YEAR;
        yearAdapter.setShowLineItem(y);
        int m = month.getCurrentItem();
        monthAdapter.setShowLineItem(m);
        int d = day.getCurrentItem();
        dayAdapter.setShowLineItem(d);
        Calendar c = Calendar.getInstance();
        c.set(y, m, d + 1);
        return c;
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
