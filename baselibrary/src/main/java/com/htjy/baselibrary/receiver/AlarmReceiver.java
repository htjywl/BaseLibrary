package com.htjy.baselibrary.receiver;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.text.TextUtils;

import org.simple.eventbus.EventBus;

import java.io.Serializable;

/**
 * 闹钟广播
 *
 * @author linyibiao
 * @since 2018/6/6 15:57
 */
public class AlarmReceiver extends BroadcastReceiver {

    private static final String ACTION_ALARM = "android.intent.action.AlarmReceiver";

    public static void set(Context context, Serializable obj, long triggerAtMillis) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.setAction(ACTION_ALARM);
        intent.putExtra("obj", obj);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        if (Build.VERSION.SDK_INT < 19) {
            alarmManager.set(AlarmManager.RTC_WAKEUP, triggerAtMillis, pendingIntent);
        } else {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, triggerAtMillis, pendingIntent);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (TextUtils.equals(action, ACTION_ALARM)) {
            Serializable obj = intent.getSerializableExtra("obj");
            EventBus.getDefault().post(obj);
        }
    }

}
