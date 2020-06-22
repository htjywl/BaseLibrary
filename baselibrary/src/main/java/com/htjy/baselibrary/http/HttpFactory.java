package com.htjy.baselibrary.http;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

import com.htjy.baselibrary.utils.temp.DialogAction;
import com.htjy.baselibrary.utils.temp.DialogUtils;


public class HttpFactory {

    private static boolean isShow = false;


    /**
     * 检查网络是否可用
     *
     * @param context
     * @return
     */
    public static boolean isOpenNetwork(final Context context) {
        if (context == null)
            return false;
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        boolean isAvailable = false;
        if (cm == null || cm.getActiveNetworkInfo() == null) {
            isAvailable = false;
        } else {
            isAvailable = cm.getActiveNetworkInfo().isAvailable();
        }

        if (!isAvailable) {
            DialogAction okAction = new DialogAction() {

                @Override
                public boolean action() {
                    isShow = false;
                    Intent intent = null;
                    String sdkVersion = android.os.Build.VERSION.SDK;
                    if (Integer.valueOf(sdkVersion) > 10) {
                        intent = new Intent(
                                android.provider.Settings.ACTION_SETTINGS);
                    } else {
                        intent = new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
                    }
                    context.startActivity(intent);
                    return true;
                }
            };
            DialogAction cancelAction = new DialogAction() {

                @Override
                public boolean action() {
                    isShow = false;
                    return true;
                }
            };
            if (!isShow && context != null ) {
                if (context instanceof Activity && !((Activity) context).isFinishing()){
                    isShow = true;
                    DialogUtils.showConfirmDialog(context, "提示", "网络未开启，是否马上设置？", okAction, cancelAction);
                }
            }
            return false;
        } else {
            return true;
        }
    }

}
