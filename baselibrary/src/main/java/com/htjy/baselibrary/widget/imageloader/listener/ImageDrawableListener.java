package com.htjy.baselibrary.widget.imageloader.listener;

import android.graphics.drawable.Drawable;

/**
 * 图片下载监听器
 * Created by linyibiao on 2017/8/25.
 */

public interface ImageDrawableListener {

    void onSuccess(Drawable drawable);

    void onFail();

}
