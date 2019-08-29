package com.htjy.baselibrary.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
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
    /**
     * 当前是showhide，还是viewpager，还是replace
     */
    private final int mode_viewpager = 1;
    private final int mode_showhide = 2;
    private final int mode_replace = 3;
    private int currentMode = mode_replace;



    /**
     * 这两个只用于懒加载的判断
     */
    protected boolean mIsCreateView;
    private boolean isUIVisible = false;

    private boolean isFirstResume = true;//是否刚刚打开

    /**
     * 自动调用
     *
     * @param savedInstanceState
     */
    protected abstract void initViews(Bundle savedInstanceState);

    /**
     * 自动调用 ,懒加载,用于viewpager和showhide 注意  replace模式每次都会加载一次
     */
    protected abstract void lazyLoad();

    /**
     * 普通的初始化，非懒加载
     */
    protected abstract void initFragmentData();

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
                if (isBinding()) {
                    inflateView = getContentViewByBinding(inflater, getLayoutInflateId, container).getRoot();
                } else {
                    inflateView = inflater.inflate(getLayoutInflateId, container, false);
                }
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

    protected void setDataBinding(View root) {

    }

    protected <T extends ViewDataBinding> T getContentViewByBinding(LayoutInflater inflater, int layoutId, @Nullable ViewGroup container) {
        T t = DataBindingUtil.inflate(inflater, layoutId, container, false);
        setDataBinding(t.getRoot());
        return t;
    }

    protected <T extends ViewDataBinding> T getContentViewByBinding(View root) {
        return DataBindingUtil.getBinding(root);
    }

    /**
     * 是否使用databinding
     *
     * @return
     */
    protected boolean isBinding() {
        return false;
    }

    protected boolean haveBus() {
        return false;
    }

    ;

    protected void keyboardStatus(boolean toShow, int keyboardHeight) {

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


    protected void afterInflateView(Bundle savedInstanceState) {

        initFragmentData();
        loadLazyData();
        initListener();


    }

    /**
     * 在viewpager中会被调用
     *
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        currentMode = mode_viewpager;
        if (isVisibleToUser) {
            isUIVisible = true;
            loadLazyData();
            if (!isFirstResume){
                OnFragmentTrueResume();
            }
        } else {
            isUIVisible = false;
        }
    }

    private void loadLazyData() {
        if (mIsCreateView && isUIVisible) {
            lazyLoad(); //数据加载完毕,恢复标记,防止重复加载 isViewCreated = false; isUIVisible = false; printLog(mTextviewContent+"可见,加载数据"); }
            mIsCreateView = false;
            isUIVisible = false;
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        //replace模式和showhide模式第一次进入不会调用hidechange 在这里懒加载
        if ((currentMode == mode_showhide || currentMode == mode_replace) && isFirstResume){
            mIsCreateView = true;
            isUIVisible = true;
            loadLazyData();
        }


        if (currentMode == mode_showhide && isVisible()) {
            OnFragmentTrueResume();
        }




        if (currentMode == mode_viewpager && getUserVisibleHint()) {
            OnFragmentTrueResume();
        }

        if (currentMode == mode_replace) {
            OnFragmentTrueResume();
        }
        isFirstResume = false;
    }
    /**
     * 这个是每次fragment显示在人的面前都会调用一次（综合hidechange、onResume、setVisiHint） 无论是replace showhide 还是viewpager
     */
    public void OnFragmentTrueResume() {

    }

    /**
     * showhide的时候调用
     *
     * @param hidden
     */
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        currentMode = mode_showhide;
        if (!hidden) {
            OnFragmentTrueResume();
            isUIVisible = true;
            loadLazyData();
        } else {
            isUIVisible = false;
        }
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
