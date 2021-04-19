package com.htjy.baselibrary.utils.temp;

import java.util.Collection;

/**
 * 文件描述：
 * 作者：jiangwei
 * 创建时间：2021/3/11
 * 更改时间：2021/3/11
 * 版本号：1
 */
public class HtStringUtils {
    public static <E> String listToString(Collection<E> collection, String regex) {
        StringBuilder builder = new StringBuilder();
        for (E e : collection) {
            builder.append(e.toString()).append(regex);
        }
        if (builder.length() > 0) {
            builder.deleteCharAt(builder.length() - 1);
        }
        return builder.toString();
    }
}
