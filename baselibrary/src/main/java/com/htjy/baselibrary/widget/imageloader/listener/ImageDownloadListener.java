package com.htjy.baselibrary.widget.imageloader.listener;

/**
 * 图片下载监听器
 * Created by linyibiao on 2017/8/25.
 */

public interface ImageDownloadListener {

    void onSuccess(String path);

    void onFail();

}
