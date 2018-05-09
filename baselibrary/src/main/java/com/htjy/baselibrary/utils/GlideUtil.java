package com.htjy.baselibrary.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

/**
 * Created by Administrator on 2018/5/8.
 */

public class GlideUtil {

    public static void loadCenterCropWithCorner(Context context, Object model, ImageView imageView, int corner_px) {
        RequestOptions requestOptions = RequestOptions.bitmapTransform(new MultiTransformation<>(
                new CenterCrop(), new RoundedCorners(corner_px)));
        requestOptions.placeholder(android.R.color.transparent);
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
        requestOptions.dontAnimate();
        Glide.with(context)
                .load(model)
                .apply(requestOptions)
                .into(imageView);
    }

}
