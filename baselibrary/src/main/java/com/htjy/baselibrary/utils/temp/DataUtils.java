package com.htjy.baselibrary.utils.temp;

import android.util.Base64;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

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


    public static String file2Base64(String path) {
        String result = null;
        if (path != null) {
            InputStream input = null;
            byte[] data = null;
            try {
                input = new FileInputStream(path);
                //创建一个字符流大小的数组。
                data = new byte[input.available()];
                //写入数组
                input.read(data);
                //用默认的编码格式进行编码
                result = Base64.encodeToString(data, Base64.DEFAULT);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (input != null) {
                        input.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

}
