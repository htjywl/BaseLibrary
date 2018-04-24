package com.htjy.baselibrary.utils;

/**
 * 数据处理工具类
 * Created by linyibiao on 2017/8/25.
 */

public class DataUtils {

    public static int str2Int(String str) {
        try {
            return Integer.valueOf(str);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static long str2Long(String str) {
        try {
            return Long.valueOf(str);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static double str2Double(String str) {
        try {
            return Double.valueOf(str);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

}
