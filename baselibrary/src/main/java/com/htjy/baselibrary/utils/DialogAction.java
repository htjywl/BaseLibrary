package com.htjy.baselibrary.utils;

/**
 * 弹出对话框按钮操作事件
 */
public interface DialogAction {
    /**
     * Action操作，如果返回false则对话框不关闭
     *
     * @return
     */
    boolean action();
}
