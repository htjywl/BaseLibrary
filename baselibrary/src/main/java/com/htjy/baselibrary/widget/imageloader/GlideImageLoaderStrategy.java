package com.htjy.baselibrary.widget.imageloader;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Looper;
import androidx.annotation.Nullable;

import android.widget.ImageView;

import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.htjy.baselibrary.widget.imageloader.listener.ImageDownloadListener;
import com.htjy.baselibrary.widget.imageloader.listener.ImageDrawableListener;
import com.htjy.baselibrary.widget.imageloader.listener.ImageLoadListener;
import com.htjy.baselibrary.widget.imageloader.listener.ImageSaveListener;
import com.htjy.baselibrary.widget.imageloader.transformation.GlideCircleTransform;
import com.htjy.baselibrary.widget.imageloader.transformation.GlideRoundTransform;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


/**
 * Created by soulrelay on 2016/10/11 13:48.
 * Class Note:
 * using {@link Glide} to load image
 */
public class GlideImageLoaderStrategy implements BaseImageLoaderStrategy {

    @Override
    public void loadImage(Object url, int placeholder, ImageView imageView) {
        loadNormal(imageView.getContext(), url, placeholder, imageView);
    }

    @Override
    public void loadImageWithListener(Object url, int placeholder, ImageView imageView, ImageLoadListener listener) {
        loadNormal(imageView.getContext(), url, placeholder, imageView, listener);
    }

    @Override
    public void loadImage(Context context, Object url, int placeholder, ImageView imageView) {
        loadNormal(context, url, placeholder, imageView);
    }

    @Override
    public void loadCenterCropWithCorner(Context context, Object model, ImageView imageView, int corner_px) {
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


    @Override
    public void loadCentercropCircleImage(Object model, int placeholder, ImageView imageView) {
        RequestOptions options = new RequestOptions().placeholder(placeholder).diskCacheStrategy(DiskCacheStrategy.ALL).transforms(new CenterCrop(), new CircleCrop());
        Glide.with(imageView.getContext()).load(model)
                .apply(options)
                .into(imageView);
    }

    /**
     * 无holder的gif加载
     *
     * @param url
     * @param imageView
     */
    @Override
    public void loadImage(Object url, ImageView imageView) {
        RequestOptions options = new RequestOptions();
        options.placeholder(imageView.getDrawable());
        options.diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(imageView.getContext()).load(url)
//                .placeholder(imageView.getDrawable())
//                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .apply(options)
                .into(imageView);
    }

    @Override
    public void loadCircleImage(Object url, int placeholder, ImageView imageView) {
        RequestOptions options = new RequestOptions();
        options.placeholder(placeholder);
        options.diskCacheStrategy(DiskCacheStrategy.ALL);
        options.dontAnimate();
        options.circleCrop();
        Glide.with(imageView.getContext()).load(url)
//                .placeholder(placeholder)
//                .dontAnimate()
//                .transform(new GlideCircleTransform(imageView.getContext()))
//                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .apply(options)
                .into(imageView);
    }

    @Override
    public void loadCircleImage(Bitmap bitmap, int placeholder, ImageView imageView) {
        RequestOptions options = new RequestOptions();
        options.placeholder(placeholder);
        options.diskCacheStrategy(DiskCacheStrategy.ALL);
        options.dontAnimate();
        options.circleCrop();
        Glide.with(imageView.getContext()).load(bitmap)
//                .placeholder(placeholder)
//                .dontAnimate()
//                .transform(new GlideCircleTransform(imageView.getContext()))
//                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .apply(options)
                .into(imageView);
    }

    @Override
    public void loadCircleImage(Bitmap bitmap, int placeholder, ImageView imageView, ImageLoadListener listener) {
        RequestOptions options = new RequestOptions();
        options.placeholder(placeholder);
        options.diskCacheStrategy(DiskCacheStrategy.ALL);
        options.dontAnimate();
        options.circleCrop();
        Glide.with(imageView.getContext()).load(bitmap)
//                .placeholder(placeholder)
//                .dontAnimate()
//                .transform(new GlideCircleTransform(imageView.getContext()))
//                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .apply(options)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        listener.onLoadError(e, model, target, isFirstResource);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        listener.onLoadReady(resource, model, target, dataSource, isFirstResource);
                        return false;
                    }
                })
                .into(imageView);
    }

    @Override
    public void loadCircleImage(Object url, int placeholder, ImageView imageView, ImageLoadListener listener) {
        RequestOptions options = new RequestOptions();
        options.placeholder(placeholder);
        options.diskCacheStrategy(DiskCacheStrategy.ALL);
        options.dontAnimate();
        options.circleCrop();
        Glide.with(imageView.getContext()).load(url)
//                .placeholder(placeholder)
//                .dontAnimate()
//                .transform(new GlideCircleTransform(imageView.getContext()))
//                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .apply(options)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        listener.onLoadError(e, model, target, isFirstResource);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        listener.onLoadReady(resource, model, target, dataSource, isFirstResource);
                        return false;
                    }
                })
                .into(imageView);
    }

    @Override
    public void loadCircleBorderImage(Object url, int placeholder, ImageView imageView, float borderWidth, int borderColor) {
        RequestOptions options = new RequestOptions();
        options.placeholder(placeholder);
        options.diskCacheStrategy(DiskCacheStrategy.ALL);
        options.dontAnimate();
        options.transform(new GlideCircleTransform( borderWidth, borderColor));
        Glide.with(imageView.getContext()).load(url)
//                .placeholder(placeholder)
//                .dontAnimate()
//                .transform(new GlideCircleTransform(imageView.getContext(),borderWidth,borderColor))
//                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .apply(options)
                .into(imageView);
    }

    @Override
    public void loadCornerImage(Bitmap bitmap, int placeholder, ImageView imageView, int dp) {
        byte[] bytes = new byte[0];
        if (bitmap != null) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            bytes = baos.toByteArray();
        }


        RequestOptions options = new RequestOptions();
        options.placeholder(placeholder);
        options.diskCacheStrategy(DiskCacheStrategy.ALL);
        options.dontAnimate();
        options.transform(new GlideRoundTransform(imageView.getContext()));
        //options.transform(new RoundedCorners(SizeUtils.dp2px(dp)));
        Glide.with(imageView.getContext()).load(bytes)
//                .placeholder(placeholder)
//                .dontAnimate()
//                .transform(new GlideRoundTransform(imageView.getContext(),dp))
//                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .apply(options)
                .into(imageView);
    }

    @Override
    public void loadCornerImage(Object url, int placeholder, ImageView imageView, int dp) {
        RequestOptions options = new RequestOptions();
        options.placeholder(placeholder);
        options.diskCacheStrategy(DiskCacheStrategy.ALL);
        options.dontAnimate();
        /*options.transform(new RoundedCorners(SizeUtils.dp2px(dp)));*/
        options.transform(new GlideRoundTransform(imageView.getContext()));
        Glide.with(imageView.getContext()).load(url)
//                .placeholder(placeholder)
//                .dontAnimate()
//                .transform(new GlideRoundTransform(imageView.getContext(),dp))
//                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .apply(options)
                .into(imageView);
    }

    @Override
    public void loadCornerImage(Object url, int placeholder, ImageView imageView, int dp, ImageLoadListener listener) {
        RequestOptions options = new RequestOptions();
        options.placeholder(placeholder);
        options.diskCacheStrategy(DiskCacheStrategy.ALL);
        options.dontAnimate();
        options.transform(new RoundedCorners(SizeUtils.dp2px(dp)));
        Glide.with(imageView.getContext()).load(url)
//                .placeholder(placeholder)
//                .dontAnimate()
//                .transform(new GlideRoundTransform(imageView.getContext(),dp))
//                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .apply(options)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        listener.onLoadError(e, model, target, isFirstResource);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        listener.onLoadReady(resource, model, target, dataSource, isFirstResource);
                        return false;
                    }
                })
                .into(imageView);
    }


    @Override
    public void loadImageWithAppCxt(Object url, ImageView imageView) {
        RequestOptions options = new RequestOptions();
        options.placeholder(imageView.getDrawable());
        options.diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(imageView.getContext().getApplicationContext()).load(url)
//                .placeholder(imageView.getDrawable())
//                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .apply(options)
                .into(imageView);
    }

//    @Override
//    public void loadGifImage(Object url, int placeholder, ImageView imageView) {
//        loadGif(imageView.getContext(), url, placeholder, imageView);
//    }

//    @Override
//    public void loadImageWithProgress(Object url, final ImageView imageView, final ProgressLoadListener listener) {
//        Glide.with(imageView.getContext()).using(new ProgressModelLoader(new ProgressUIListener() {
//            @Override
//            public void update(final int bytesRead, final int contentLength) {
//                imageView.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        listener.update(bytesRead, contentLength);
//                    }
//                });
//            }
//        })).load(url).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.SOURCE).
//                listener(new RequestListener<String, GlideDrawable>() {
//                    @Override
//                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
//                        listener.onException();
//                        return false;
//                    }
//
//                    @Override
//                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
//                        listener.onResourceReady();
//                        return false;
//                    }
//                }).into(imageView);
//    }
//
//
//    @Override
//    public void loadGifWithPrepareCall(Object url, ImageView imageView, final SourceReadyListener listener) {
//        Glide.with(imageView.getContext()).load(url).asGif()
//                .skipMemoryCache(true)
//                .diskCacheStrategy(DiskCacheStrategy.SOURCE).
//                listener(new RequestListener<String, GifDrawable>() {
//                    @Override
//                    public boolean onException(Exception e, String model, Target<GifDrawable> target, boolean isFirstResource) {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean onResourceReady(GifDrawable resource, String model, Target<GifDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
//                        listener.onResourceReady(resource.getIntrinsicWidth(), resource.getIntrinsicHeight());
//                        return false;
//                    }
//                }).into(imageView);
//    }
//
//    @Override
//    public void loadImageWithPrepareCall(Object url, ImageView imageView, int placeholder, final SourceReadyListener listener) {
//        Glide.with(imageView.getContext()).load(url)
//                .skipMemoryCache(true)
//                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
//                .placeholder(placeholder)
//                .listener(new RequestListener<String, GlideDrawable>() {
//                    @Override
//                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
//                        listener.onResourceReady(resource.getIntrinsicWidth(), resource.getIntrinsicHeight());
//                        return false;
//                    }
//                }).into(imageView);
//    }

    @Override
    public void clearImageDiskCache(final Context context) {
        try {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.get(context.getApplicationContext()).clearDiskCache();
                    }
                }).start();
            } else {
                Glide.get(context.getApplicationContext()).clearDiskCache();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void clearImageMemoryCache(Context context) {
        try {
            if (Looper.myLooper() == Looper.getMainLooper()) { //只能在主线程执行
                Glide.get(context.getApplicationContext()).clearMemory();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void trimMemory(Context context, int level) {
        Glide.get(context).trimMemory(level);
    }

    @Override
    public String getCacheSize(Context context) {
        try {
            return CommonUtils.getFormatSize(CommonUtils.getFolderSize(Glide.getPhotoCacheDir(context.getApplicationContext())));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public void saveImage(Context context, Object url, String savePath, String saveFileName, ImageSaveListener listener) {
        if (!CommonUtils.isSDCardExsit() || ObjectUtils.isEmpty(url)) {
            listener.onSaveFail();
            return;
        }
        InputStream fromStream = null;
        OutputStream toStream = null;
        try {
            File cacheFile = Glide.with(context).load(url).downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).get();
            if (cacheFile == null || !cacheFile.exists()) {
                listener.onSaveFail();
                return;
            }
            File dir = new File(savePath);
            if (!dir.exists()) {
                dir.mkdir();
            }
            File file = new File(dir, saveFileName + CommonUtils.getPicType(cacheFile.getAbsolutePath()));

            fromStream = new FileInputStream(cacheFile);
            toStream = new FileOutputStream(file);
            byte length[] = new byte[1024];
            int count;
            while ((count = fromStream.read(length)) > 0) {
                toStream.write(length, 0, count);
            }
            //用广播通知相册进行更新相册
            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            Uri uri = Uri.fromFile(file);
            intent.setData(uri);
            context.sendBroadcast(intent);
            listener.onSaveSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            listener.onSaveFail();
        } finally {
            if (fromStream != null) {
                try {
                    fromStream.close();
                    toStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    fromStream = null;
                    toStream = null;
                }
            }
        }

    }

    @Override
    public void loadDrawable(Context context, Object url, ImageDrawableListener listener) {
        SimpleTarget<Drawable> simpleTarget = new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                listener.onSuccess(resource);
            }
        };

        Glide.with(context).load(url).into(simpleTarget);
    }

    @Override
    public void downloadOnly(Context context, String url, ImageDownloadListener listener) {
        new AsyncTask<String, Void, File>() {

            @Override
            protected File doInBackground(String... params) {
                try {
                    String imgUrl = params[0];
                    return Glide.with(context)
                            .download(imgUrl).submit().get();
                } catch (Exception ex) {
                    return null;
                }
            }

            @Override
            protected void onPostExecute(File file) {
                super.onPostExecute(file);
                if (file != null) {
                    listener.onSuccess(file.getPath());
                } else {
                    listener.onFail();
                }
            }
        }.execute(url);
    }


    /**
     * load image with Glide
     */
    private void loadNormal(final Context ctx, final Object url, int placeholder, ImageView imageView) {
        /**
         *  为其添加缓存策略,其中缓存策略可以为:Source及None,None及为不缓存,Source缓存原型.如果为ALL和Result就不行.然后几个issue的连接:
         https://github.com/bumptech/glide/issues/513
         https://github.com/bumptech/glide/issues/281
         https://github.com/bumptech/glide/issues/600
         modified by xuqiang
         */

        //去掉动画 解决与CircleImageView冲突的问题 这个只是其中的一个解决方案
        //使用SOURCE 图片load结束再显示而不是先显示缩略图再显示最终的图片（导致图片大小不一致变化）
        final long startTime = System.currentTimeMillis();
        RequestOptions options = new RequestOptions();
        options.placeholder(placeholder);
        options.diskCacheStrategy(DiskCacheStrategy.ALL);
        RequestListener<Drawable> listener = new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                return false;
            }
        };
        Glide.with(ctx).load(url)
//                .placeholder(placeholder)
//                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
//                .listener(new RequestListener<String, GlideDrawable>() {
//            @Override
//            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
//                return false;
//            }
//
//            @Override
//            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
//                return false;
//            }
//        })
                .apply(options)
                .listener(listener)
                .into(imageView);
    }

    /**
     * load image with Glide
     */
    private void loadNormal(final Context ctx, final Object url, int placeholder, ImageView imageView, ImageLoadListener listeners) {
        /**
         *  为其添加缓存策略,其中缓存策略可以为:Source及None,None及为不缓存,Source缓存原型.如果为ALL和Result就不行.然后几个issue的连接:
         https://github.com/bumptech/glide/issues/513
         https://github.com/bumptech/glide/issues/281
         https://github.com/bumptech/glide/issues/600
         modified by xuqiang
         */

        //去掉动画 解决与CircleImageView冲突的问题 这个只是其中的一个解决方案
        //使用SOURCE 图片load结束再显示而不是先显示缩略图再显示最终的图片（导致图片大小不一致变化）
        final long startTime = System.currentTimeMillis();
        RequestOptions options = new RequestOptions();
        options.placeholder(placeholder);
        options.diskCacheStrategy(DiskCacheStrategy.ALL);
        RequestListener<Drawable> listener = new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                listeners.onLoadError(e, model, target, isFirstResource);
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                listeners.onLoadReady(resource, model, target, dataSource, isFirstResource);
                return false;
            }
        };
        if (ctx != null)
            Glide.with(ctx).load(url)
//                    .placeholder(placeholder)
//                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
//                    .listener(new RequestListener<String, GlideDrawable>() {
//                        @Override
//                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
//                            return false;
//                        }
//
//                        @Override
//                        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
//                            return false;
//                        }
//                    })
                    .apply(options)
                    .listener(listener)
                    .into(imageView);
    }

//    /**
//     * load image with Glide
//     */
//    private void loadGif(final Context ctx, Object url, int placeholder, ImageView imageView) {
//        final long startTime = System.currentTimeMillis();
//        Glide.with(ctx).load(url).asGif()
//                .placeholder(placeholder).skipMemoryCache(true)
//                .diskCacheStrategy(DiskCacheStrategy.SOURCE).listener(new RequestListener<String, GifDrawable>() {
//            @Override
//            public boolean onException(Exception e, String model, Target<GifDrawable> target, boolean isFirstResource) {
//                return false;
//            }
//
//            @Override
//            public boolean onResourceReady(GifDrawable resource, String model, Target<GifDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
//                return false;
//            }
//        })
//                .into(imageView);
//    }

}
