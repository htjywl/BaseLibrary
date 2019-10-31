package com.htjy.baselibrary.widget.imageloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.htjy.baselibrary.widget.imageloader.listener.ImageDownloadListener;
import com.htjy.baselibrary.widget.imageloader.listener.ImageDrawableListener;
import com.htjy.baselibrary.widget.imageloader.listener.ImageLoadListener;
import com.htjy.baselibrary.widget.imageloader.listener.ImageSaveListener;


/**
 * Created by soulrelay on 2016/10/11.
 * Class Note:
 * abstract class/interface defined to load image
 * (Strategy Pattern used here)
 */
public interface BaseImageLoaderStrategy {
    //无占位图
    void loadImage(String url, ImageView imageView);

    //这里的context指定为ApplicationContext
    void loadImageWithAppCxt(String url, ImageView imageView);

    void loadCenterCropWithCorner(Context context, Object model, ImageView imageView, int corner_px);

    void loadImage(String url, int placeholder, ImageView imageView);

    void loadImageWithListener(String url, int placeholder, ImageView imageView, ImageLoadListener listener);

    void loadImage(Context context, String url, int placeholder, ImageView imageView);

    void loadCircleImage(String url, int placeholder, ImageView imageView);
    void loadCentercropCircleImage(Object model, int placeholder, ImageView imageView);


    void loadCircleImage(Bitmap bitmap, int placeholder, ImageView imageView);
    void loadCircleImage(Bitmap bitmap, int placeholder, ImageView imageView,ImageLoadListener listener);
     void loadCircleImage(String url, int placeholder, ImageView imageView, ImageLoadListener listener);
    void loadCircleBorderImage(String url, int placeholder, ImageView imageView, float borderWidth, int borderColor);


    void loadCornerImage(Bitmap bitmap, int placeholder, ImageView imageView, int dp);

    void loadCornerImage(String url, int placeholder, ImageView imageView, int dp);

//    void loadGifImage(String url, int placeholder, ImageView imageView);

//    void loadImageWithProgress(String url, ImageView imageView, ProgressLoadListener listener);

//    void loadImageWithPrepareCall(String url, ImageView imageView, int placeholder, SourceReadyListener listener);

//    void loadGifWithPrepareCall(String url, ImageView imageView, SourceReadyListener listener);

    //清除硬盘缓存
    void clearImageDiskCache(final Context context);

    //清除内存缓存
    void clearImageMemoryCache(Context context);

    //根据不同的内存状态，来响应不同的内存释放策略
    void trimMemory(Context context, int level);

    //获取缓存大小
    String getCacheSize(Context context);

    void saveImage(Context context, String url, String savePath, String saveFileName, ImageSaveListener listener);

    void downloadOnly(Context context, String url, ImageDownloadListener listener);
    void loadCornerImage(String url, int placeholder, ImageView imageView, int dp, ImageLoadListener listener);

    void loadDrawable(Context context, String url, ImageDrawableListener listener);
}
