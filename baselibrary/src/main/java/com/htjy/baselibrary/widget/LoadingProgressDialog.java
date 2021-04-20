package com.htjy.baselibrary.widget;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewParent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;

import androidx.core.content.ContextCompat;

import com.htjy.baselibrary.R;


/**
 * Created by Jiang on 2018/8/8. 12:53
 * mail:jiangwei_android@163.com
 */
public class LoadingProgressDialog extends AlertDialog {

    public LoadingProgressDialog(Context context) {
        super(context);
    }

    public LoadingProgressDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    public void show() {
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
        super.show();
        fullScreenImmersive(getWindow().getDecorView());
        this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
    }

    private void fullScreenImmersive(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_FULLSCREEN;
            view.setSystemUiVisibility(uiOptions);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ProgressBar bar = new ProgressBar(getContext());
        bar.setIndeterminateDrawable(getContext().getResources().getDrawable(R.drawable.loading_progressbar));
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
        getWindow().getDecorView().setSystemUiVisibility(View.GONE);
        //window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        lp.alpha = 1f;// 透明度
        lp.dimAmount = 0f;// 黑暗度
        window.setAttributes(lp);
        window.getDecorView().setBackgroundColor(ContextCompat.getColor(getContext(), R.color.transparent));
    }
}
