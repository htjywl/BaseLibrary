package com.htjy.baselibrary.widget.imageloader.listener;

import android.graphics.drawable.Drawable;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.target.Target;

/**
 * 图片保存监听器
 * added by sushuai 2017/1/10
 */
public interface ImageLoadListener {

    void onLoadReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource);

    void onLoadError(GlideException e, Object model, Target<Drawable> target, boolean isFirstResource);
}
