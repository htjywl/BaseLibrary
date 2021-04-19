package com.htjy.baselibrary.base;

import android.app.Activity;

/**
 * Created by hankkin on 2017/3/29.
 */

public interface BaseView {

    /**
     * 显示loading框
     */
    void showProgress(String hint);

    /**
     * 隐藏loading框
     */
    void hideProgress();

    void toast(CharSequence s);

    void toast(int id);

    void toastLong(CharSequence s);

    void toastLong(int id);


    /**
     * 显示空数据布局
     */
    void showNullLayout();



    /**
     * 显示异常布局
     */
    void showErrorLayout();

    void showSuccessLayout();

    /**
     * 显示网络错误布局
     */
    void showNetWorkErrorLayout();

    Activity getThisActivity();



}
