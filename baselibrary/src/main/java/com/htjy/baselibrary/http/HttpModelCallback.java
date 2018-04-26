package com.htjy.baselibrary.http;


import com.htjy.baselibrary.http.base.BaseException;

/**
 * Created by Administrator on 2017/4/26.
 * http请求的回调   requestCode标志属于哪个请求,接口管理类
 */
public class HttpModelCallback {


    //jsonCallback数据的回调
    public interface HttpModelGsonCallback<T> {
        public void onSuccess(T result);

        public void onFail(Throwable exception);
    }
    //jsonCallback数据的回调,带有缓存
    public interface HttpModelGsonWithDbCallback<T> {
        public void onSuccess(T result);
        public void onGetDbSuccess(T result);
        public void onGetDbError(Throwable exception);
        public void onFail(Throwable exception);
    }


    //纯粹string类型数据的回调
    public interface HttpModelStringCallback<T> {
        public void onSuccess(T result);

        public void onFail(BaseException exception);
    }

    //重新登录
    public interface HttpReloginallback<T> {
        public void onSuccess(T result);

        public void onFail(BaseException exception);
    }
}
