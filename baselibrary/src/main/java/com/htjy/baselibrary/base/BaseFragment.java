package com.htjy.baselibrary.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.htjy.baselibrary.utils.ToastUtils;
import com.lzy.okgo.OkGo;
import com.trello.rxlifecycle2.components.support.RxFragment;

import org.simple.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * <pre>
 *     author : jiangwei
 *     e-mail : jiangwei_android@163.com
 *     time   : 2017/07/12
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public abstract class BaseFragment extends RxFragment implements BaseView {

    private ProgressDialog progress;
    protected boolean hasBus = false;
    protected Activity mActivity;
    private View inflateView;
    private Unbinder unbinder;
    protected boolean mIsCreateView;
    private boolean isInitData;

    /**
     * 自动调用
     *
     * @param savedInstanceState
     */
    protected abstract void initViews(Bundle savedInstanceState);

    /**
     * 自动调用 ,懒加载
     */
    protected abstract void lazyLoad();

    /**
     * 自动调用
     */
    protected abstract void initListener();

    protected abstract void initStateLayout(View inflateView);

    /*//生命周期在onCreateView之后
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
*/
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (null == inflateView) { // 强制竖屏显示 activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); int layoutResId = getCreateViewLayoutId(); if (layoutResId > 0) inflateView = inflater.inflate(getCreateViewLayoutId(), container, false); // 解决点击穿透问题 inflateView.setOnTouchListener(new View.OnTouchListener() { @Override public boolean onTouch(View v, MotionEvent event) { return true; } }); } return inflateView;
            int getLayoutInflateId = getCreateViewLayoutId();
            if (getLayoutInflateId > 0) {
                inflateView = inflater.inflate(getLayoutInflateId, container, false);
                unbinder = ButterKnife.bind(this, inflateView);

            }
        } else {
            unbinder = ButterKnife.bind(this, inflateView);
        }
        hasBus = haveBus();
        if (hasBus) {
            EventBus.getDefault().register(this);
        }
        ViewGroup parent = (ViewGroup) inflateView.getParent();
        if (null != parent) {
            parent.removeView(inflateView);
        }
        mIsCreateView = true;
        initStateLayout(inflateView);
        initViews(savedInstanceState);
        return inflateView;

    }

    protected boolean haveBus() {
        return false;
    }

    ;

    protected void keyboardStatus(boolean toShow, int keyboardHeight) {

    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && mIsCreateView && !isInitData) {
            lazyLoad();
            isInitData = true;
        }
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden && mIsCreateView && !isInitData) {
            lazyLoad();
            isInitData = true;
        }
    }
    /**
     * 这里才真正的创建了activity
     *
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        afterInflateView(savedInstanceState);
    }

    //创建的时候加载
    protected void initFirstData() {

    }

    protected void afterInflateView(Bundle savedInstanceState) {
        initFirstData();
        if (!isHidden()) {
            lazyLoad();
            isInitData = true;
        } else {
            isInitData = false;
        }
        initListener();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null) {
            unbinder.unbind();
        }
        if (hasBus) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    public Activity getThisActivity() {
        return mActivity;
    }

    @Override
    public void onAttach(Activity context) {
        this.mActivity = context;
        super.onAttach(context);
    }

    @Override
    public void showProgress() {
        if (progress == null && isAdded()) {
            progress = new ProgressDialog(getActivity());
            progress.show();
        }
    }

    @Override
    public void hideProgress() {
        if (progress != null && progress.isShowing() && isAdded()) {
            progress.hide();
        }
    }

    @Override
    public void toast(CharSequence s) {
        ToastUtils.showShortToast(s);
    }

    @Override
    public void toast(int id) {
        ToastUtils.showShortToast(id);
    }

    @Override
    public void toastLong(CharSequence s) {
        ToastUtils.showLongToast(s);
    }

    @Override
    public void toastLong(int id) {
        ToastUtils.showLongToast(id);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (hasBus) {
            EventBus.getDefault().unregister(this);
        }
        OkGo.getInstance().cancelTag(this);
    }

    public abstract int getCreateViewLayoutId();
}
