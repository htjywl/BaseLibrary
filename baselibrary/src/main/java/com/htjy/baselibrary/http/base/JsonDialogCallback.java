package com.htjy.baselibrary.http.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;

import com.htjy.baselibrary.http.HttpFactory;
import com.htjy.baselibrary.utils.DialogUtils;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

import java.lang.ref.WeakReference;


/**
 * 对于网络请求是否需要弹出进度对话框
 */
public abstract class JsonDialogCallback<T> extends JsonCallback<T> {

    private static final String TAG = "JsonDialogCallback";
    private WeakReference<Context> mWeakRefrence;
    private boolean mNetwork = true;
    private Dialog dialog;
    private boolean isCartoonDialog = false; //是否显示卡通进度条
    private boolean canCancelDialog = true; // 是否能取消进度条
    private boolean showProgressDialog = true;// 是否显示进度条

    public JsonDialogCallback(Context activity) {
        super();
        mWeakRefrence = new WeakReference<>(activity);
        mNetwork = HttpFactory.isOpenNetwork(activity);
        getProgressDialog(mWeakRefrence.get());
    }

    /**
     * 动态设置进度条
     *
     * @param activity           activity
     * @param showProgressDialog 是否显示进度条
     * @param isCartoonDialog    是否显示卡通进度条
     * @param canCancelDialog    是否能取消进度条
     */
    public JsonDialogCallback(Context activity, boolean showProgressDialog, boolean isCartoonDialog, boolean canCancelDialog) {
        super();
        mNetwork = HttpFactory.isOpenNetwork(activity);
        mWeakRefrence = new WeakReference<>(activity);
        setCanCancelDialog(canCancelDialog);
        setCartoonDialog(isCartoonDialog);
        setShowProgressDialog(showProgressDialog);
        getProgressDialog(activity);
    }

    @Override
    public void onStart(Request<T, ? extends Request> request) {
        super.onStart(request);
        //网络请求前显示对话框
        Context context = mWeakRefrence.get();
        if (dialog != null && showProgressDialog && !dialog.isShowing() && context != null && !((Activity) context).isFinishing()) {
            dialog.show();
        }
    }


    @Override
    public void onFinish() {
        super.onFinish();
        //网络请求结束后关闭对话框
        if (dialog != null && dialog.isShowing() && !((Activity) mWeakRefrence.get()).isFinishing()) {
            dialog.dismiss();
        }
    }


    /**
     * 进度对话框
     *
     * @return
     */
    public Dialog getProgressDialog(Context context) {
        if (context != null && dialog == null) {
            dialog = new DialogUtils.DefaultProgressDialog(context);
            /*if (isCartoonDialog)
                dialog = new DialogUtils.CustomProgressDialog(activity);
            else
                dialog = new DialogUtils.DefaultProgressDialog(activity);*/
        }
        if (dialog != null) {
            dialog.setCancelable(canCancelDialog);
            dialog.setCanceledOnTouchOutside(canCancelDialog);
        }
        return dialog;
    }

    public void hideCaton() {
        setCartoonDialog(false);
    }

    public void setCartoonDialog(boolean cartoonDialog) {
        isCartoonDialog = cartoonDialog;
    }


    public Context getThisActivity() {
        return mWeakRefrence.get();
    }

    /**
     * 是否显示进度条
     *
     * @return
     */
    public boolean isShowProgressDialog() {
        return showProgressDialog;
    }

    /**
     * 设置是否显示进度条
     *
     * @param showProgressDialog
     */
    public void setShowProgressDialog(boolean showProgressDialog) {
        this.showProgressDialog = showProgressDialog;
    }

    /**
     * 设置是否能取消进度条
     *
     * @param canCancelDialog
     */
    public void setCanCancelDialog(boolean canCancelDialog) {
        this.canCancelDialog = canCancelDialog;
    }


    /**
     * 子线程 留给子类实现
     *
     * @param t
     */
    @Override
    public void afterConvertSuccess(T t) {

    }

    @Override
    public void onSuccess(Response<T> response) {
        super.onSuccess(response);
        if (response.getRawCall() == null || !response.getRawCall().isCanceled()) {
            onSimpleSuccess(response);
        }
    }

    /**
     * 这个成功回调不包括被主动取消的情况（被主动取消的情况包括activity直接回退的时候）
     *
     * @param response 此次请求的信息
     */
    public void onSimpleSuccess(Response<T> response) {

    }

    @Override
    public void onError(Response<T> response) {
        super.onError(response);
        if (response.getRawCall() == null || !response.getRawCall().isCanceled()) {
            onSimpleError(response);
        }
    }

    /**
     * 这个错误回调不包括被主动取消的情况（被主动取消的情况包括activity直接回退的时候）
     *
     * @param response 此次请求的信息
     */
    public void onSimpleError(Response<T> response) {

    }

}
