package com.htjy.baselibrary.utils;

/**
 * 颜色工具类
 * Created by linyibiao on 2017/8/17.
 */

public class ColorUtils {

    private ColorUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 获取颜色值
     *
     * @param colorID 颜色ID
     * @return int值
     */
    public static int colorOfInt(int colorID) {
        return Utils.getContext().getResources().getColor(colorID);
    }

}
