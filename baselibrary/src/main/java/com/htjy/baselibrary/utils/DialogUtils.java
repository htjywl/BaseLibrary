package com.htjy.baselibrary.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewParent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;

import com.htjy.baselibrary.R;
import com.lyb.besttimer.pluginwidget.view.recyclerview.adapter.AdapterPosClick;

import java.util.List;

/**
 * Created by jiangwei on 2017/7/11.
 */

public class DialogUtils {

    public static Dialog showChooseDialog(Activity activity, List<String> chooseList, AdapterPosClick<String> adapterPosClick) {

        AlertDialog alertDialog=new AlertDialog.Builder(activity)
                .setItems(chooseList.toArray(new String[]{}), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (adapterPosClick!=null){
                            adapterPosClick.onClick(chooseList.get(which),which);
                        }
                    }
                })
                .create();

        alertDialog.show();

        return alertDialog;
    }

    /**
     * 确认对话框, 包含确认跟取消处理接口
     *
     * @param context      调用Activity
     * @param title        标题
     * @param msg          消息
     * @param okAction     点确认按钮后的处理接口
     * @param cancelAction 点取消按钮后的处理接口
     * @return
     */
    public static Dialog showConfirmDialog(Context context, String title,
                                           String msg, final DialogAction okAction,
                                           final DialogAction cancelAction) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        if (title != null) {
            builder.setTitle(title);
        }
        builder.setMessage(msg);
        builder.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (okAction != null) {
                            if (okAction.action()) {
                                dialog.dismiss();
                            }
                        } else {
                            dialog.dismiss();
                        }
                    }
                });
        builder.setNegativeButton("取消",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (cancelAction != null) {
                            if (cancelAction.action()) {
                                dialog.dismiss();
                            }
                        } else {
                            dialog.dismiss();
                        }
                    }
                });
        Dialog dlg = builder.create();
        // add by chenjb
        dlg.setCanceledOnTouchOutside(false);
        dlg.show();
        return dlg;
    }
    /**
     * 确认对话框
     *
     * @param context      调用Activity
     * @param msg          消息
     * @param okAction     点确认按钮后的处理接口
     * @return
     */
    public static Dialog showSimpleDialog(Context context, String msg, final DialogAction okAction) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(msg);
        builder.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (okAction != null) {
                            if (okAction.action()) {
                                dialog.dismiss();
                            }
                        } else {
                            dialog.dismiss();
                        }
                    }
                });
        Dialog dlg = builder.create();
        dlg.setCanceledOnTouchOutside(false);
        dlg.show();
        return dlg;
    }

    public static class DefaultProgressDialog extends AlertDialog {

        public DefaultProgressDialog(Context context) {
            super(context);
        }

        public DefaultProgressDialog(Context context,int themeResId) {
            super(context,themeResId);
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            ProgressBar bar = new ProgressBar(getContext());
            setContentView(bar);
            ViewParent vp = bar.getParent();
            while (vp != null) {
                ViewParent vp0 = vp.getParent();
                if (vp0 == null) {
                    if (vp instanceof View) {
                        ((View) vp).setBackgroundColor(Color.LTGRAY);
                    }
                    break;
                } else {
                    vp = vp0;
                }
            }
            // ((View)bar.getParent().getParent().getParent()).setBackgroundColor(Color.LTGRAY);
            /** 设置透明度 */
            Window window = getWindow();
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.alpha = 1f;// 透明度
            lp.dimAmount = 0f;// 黑暗度
            window.setAttributes(lp);
            window.getDecorView().setBackgroundColor(ContextCompat.getColor(getContext(), R.color.transparent));
        }
    }
}
