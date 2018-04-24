package com.htjy.baselibrary.widget.imageloader.transformation;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.htjy.baselibrary.utils.SizeUtils;

import java.nio.ByteBuffer;
import java.security.MessageDigest;

/**
 * <pre>
 *     author : jiangwei
 *     e-mail : jiangwei_android@163.com
 *     time   : 2017/08/03
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class GlideRoundTransform extends BitmapTransformation {
    private static float radius = 0f;

    public GlideRoundTransform(Context context) {
        this(context, 4);
    }

    public GlideRoundTransform(Context context, int dp) {
        super(context);
        this.radius = SizeUtils.dp2px(dp);
    }

    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        return roundCrop(pool, toTransform);
    }

    private static Bitmap roundCrop(BitmapPool pool, Bitmap source) {
        if (source == null) return null;

        Bitmap result = pool.get(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
        if (result == null) {
            result = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(result);
        Paint paint = new Paint();
        paint.setShader(new BitmapShader(source, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
        paint.setAntiAlias(true);
        RectF rectF = new RectF(0f, 0f, source.getWidth(), source.getHeight());
        canvas.drawRoundRect(rectF, radius, radius, paint);
        return result;
    }

//    @Override public String getId() {
//        return getClass().getName() + Math.round(radius);
//    }

    @Override
    public void updateDiskCacheKey(MessageDigest messageDigest) {
        messageDigest.update(ByteBuffer.allocate(Float.SIZE)
                .putFloat(radius).array());
    }
}
