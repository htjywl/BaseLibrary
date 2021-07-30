package com.htjy.baselibrary.utils;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.databinding.BindingAdapter;

import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.CenterInside;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.htjy.baselibrary.widget.imageloader.ImageLoaderUtil;

import java.text.DateFormat;

/**
 * databinding
 *
 * @author linyibiao
 * @since 2018/5/15 17:47
 */
public class DataBindingCommonUtil {

    @BindingAdapter(value = {"android:htjy_selected"}, requireAll = false)
    public static void setSelected(View view, boolean selected) {
        view.setSelected(selected);
    }

    @BindingAdapter(value = {"android:htjy_src_centerinside_url", "android:htjy_src_centerinside_place"}, requireAll = false)
    public static void setImageCenterInside(ImageView imageView, String url, int placeHolder) {
        RequestOptions requestOptions = RequestOptions.bitmapTransform(new CenterInside());
        Glide.with(imageView.getContext())
                .load(url)
                .apply(requestOptions.placeholder(placeHolder).diskCacheStrategy(DiskCacheStrategy.ALL).dontAnimate())
                .into(imageView);
    }

    @BindingAdapter(value = {"android:htjy_src_url", "android:htjy_src_place"}, requireAll = false)
    public static void setImage(ImageView imageView, String url, int placeHolder) {
        ImageLoaderUtil.getInstance().loadImage(url, placeHolder, imageView);
    }

    @BindingAdapter(value = {"android:htjy_src_corner_url", "android:htjy_src_corner_place", "android:htjy_src_corner_size"}, requireAll = false)
    public static void setCornerImage(ImageView imageView, String url, int placeHolder, int corner_px) {
        RequestOptions requestOptions = RequestOptions.bitmapTransform(new CenterCrop());
        if (corner_px > 0)
            requestOptions = RequestOptions.bitmapTransform(new MultiTransformation<>(
                    new CenterCrop(), new RoundedCorners(corner_px)));
        Glide.with(imageView.getContext())
                .load(url)
                .apply(requestOptions.placeholder(placeHolder).diskCacheStrategy(DiskCacheStrategy.ALL).dontAnimate())
                .into(imageView);
    }

    @BindingAdapter(value = {"android:htjy_src_circle_url", "android:htjy_src_circle_place"}, requireAll = false)
    public static void setCircleImage(ImageView imageView, String url, int placeHolder) {
        ImageLoaderUtil.getInstance().loadCircleImage(url, placeHolder, imageView);
    }

    @BindingAdapter(value = {"android:htjy_src_centercrop_url", "android:htjy_src_centercrop_corner"}, requireAll = false)
    public static void loadCenterCropWithCorner(ImageView imageView, String url, int corner_px) {
        if (corner_px > 0) {
            ImageLoaderUtil.getInstance().loadCenterCropWithCorner(imageView.getContext(), url, imageView, corner_px);
        } else {
            RequestOptions requestOptions = RequestOptions.bitmapTransform(new CenterCrop());
            Glide.with(imageView.getContext())
                    .load(url)
                    .apply(requestOptions.placeholder(android.R.color.transparent).diskCacheStrategy(DiskCacheStrategy.ALL).dontAnimate())
                    .into(imageView);
        }
    }

    @BindingAdapter(value = {"android:htjy_time", "android:htjy_format"}, requireAll = false)
    public static void setTimeStr(TextView textView, String time, DateFormat format) {
        if (ObjectUtils.isNotEmpty(time)) {
            if (format == null) {
                textView.setText(TimeUtils.millis2String(Long.valueOf(time) * 1000));
            } else {

                textView.setText(TimeUtils.millis2String(Long.valueOf(time) * 1000, format));
            }

        }
    }



    @BindingAdapter({"android:htjy_inputType"})
    public static void setInputType(EditText view, int type) {
        if (type != EditorInfo.TYPE_NULL) {
            view.setInputType(type);
        }
    }

    @BindingAdapter({"android:htjy_textHtml"})
    public static void setTextHtml_htjy(TextView view, String source) {
        if (!TextUtils.isEmpty(source)) {
            view.setText(Html.fromHtml(source));
        }
    }

    @BindingAdapter({"android:htjy_textColor"})
    public static void setTextColor(TextView view, int id) {
        if (id > 0) {
            view.setTextColor(ContextCompat.getColor(view.getContext(), id));
        }
    }

    @BindingAdapter({"android:htjy_isBold"})
    public static void setTextBold(TextView view, boolean isBold) {
        view.getPaint().setFakeBoldText(isBold);
    }

    @BindingAdapter({"android:src"})
    public static void setSrc(ImageView view, int resId) {
        view.setImageResource(resId);
    }

    @BindingAdapter({"android:background"})
    public static void setBackGround(View view, int resId) {
        view.setBackgroundResource(resId);
    }

    @BindingAdapter({"android:backgroundColor"})
    public static void setBackGroundColor(View view, int resId) {
        view.setBackgroundColor(resId);
    }

    @BindingAdapter({"android:drawableLeft"})
    public static void setDrawableLeft(TextView view, int resId) {
        try {
            Drawable drawable = view.getResources().getDrawable(resId);
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            Drawable[] drawables = view.getCompoundDrawables();
            view.setCompoundDrawables(drawable, drawables[1], drawables[2], drawables[3]);
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

    @BindingAdapter({"android:drawableTop_htjy"})
    public static void setDrawableTop_htjy(TextView view, int resId) {
        try {
            Drawable drawable = view.getResources().getDrawable(resId);
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            Drawable[] drawables = view.getCompoundDrawables();
            view.setCompoundDrawables(drawables[0], drawable, drawables[2], drawables[3]);
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

    @BindingAdapter({"android:htjy_src"})
    public static void setSrc_htjy(ImageView view, int resId) {
        if (resId > 0) {
            view.setImageResource(resId);
        }
    }

    @BindingAdapter({"android:htjy_background"})
    public static void setBackGround_htjy(View view, int resId) {
        if (resId > 0) {
            view.setBackgroundResource(resId);
        }
    }

    @BindingAdapter({"android:htjy_backgroundColor"})
    public static void setBackGroundColor_htjy(View view, int resId) {
        view.setBackgroundColor(resId);
    }


    @BindingAdapter({"android:htjy_drawableLeft"})
    public static void setDrawableLeft_htjy(TextView view, int resId) {
        try {
            Drawable drawable = view.getResources().getDrawable(resId);
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            Drawable[] drawables = view.getCompoundDrawables();
            view.setCompoundDrawables(drawable, drawables[1], drawables[2], drawables[3]);
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

    @BindingAdapter({"android:htjy_drawableRight"})
    public static void setDrawableRight_htjy(TextView view, int resId) {
        try {
            Drawable drawable = view.getResources().getDrawable(resId);
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            Drawable[] drawables = view.getCompoundDrawables();
            view.setCompoundDrawables(drawables[0], drawables[1], drawable, drawables[3]);
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }


    @BindingAdapter({"android:htjy_textsize"})
    public static void setTextSize_htjy(TextView view, int textSize) {
        view.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
    }

}
