package com.htjy.baselibrary.base;

import android.app.Activity;
import android.app.ProgressDialog;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.ToastUtils;
import com.trello.rxlifecycle3.components.support.RxFragment;


import org.greenrobot.eventbus.EventBus;

import java.util.List;

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
    private static final String FRAGMENTATION_STATE_SAVE_IS_INVISIBLE_WHEN_LEAVE = "fragmentation_invisible_when_leave";
    private static final String FRAGMENTATION_STATE_SAVE_COMPAT_REPLACE = "fragmentation_compat_replace";


    // SupportVisible相关
    private boolean mIsSupportVisible;
    private boolean mNeedDispatch = true;
    private boolean mInvisibleWhenLeave;
    private boolean mIsFirstVisible = true;
    private boolean mFirstCreateViewCompatReplace = true;
    private Bundle mSaveInstanceState;
    private Handler mHandler;

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

    protected void lazyLoad(Bundle savedInstanceState) {

    }

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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mInvisibleWhenLeave = savedInstanceState.getBoolean(FRAGMENTATION_STATE_SAVE_IS_INVISIBLE_WHEN_LEAVE);
            mFirstCreateViewCompatReplace = savedInstanceState.getBoolean(FRAGMENTATION_STATE_SAVE_COMPAT_REPLACE);
            mSaveInstanceState = savedInstanceState;
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(FRAGMENTATION_STATE_SAVE_IS_INVISIBLE_WHEN_LEAVE, mInvisibleWhenLeave);
        outState.putBoolean(FRAGMENTATION_STATE_SAVE_COMPAT_REPLACE, mFirstCreateViewCompatReplace);
    }


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

        if (haveBus()) {
            EventBus.getDefault().register(this);
        }
        ViewGroup parent = (ViewGroup) inflateView.getParent();
        if (null != parent) {
            parent.removeView(inflateView);
        }


        initStateLayout(inflateView);
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
        if (!mFirstCreateViewCompatReplace && getTag() != null && getTag().startsWith("android:switcher:")) {
            return;
        }

        if (mFirstCreateViewCompatReplace) {
            mFirstCreateViewCompatReplace = false;
        }

        if (!mInvisibleWhenLeave && !isHidden() && getUserVisibleHint()) {
            if (getParentFragment() == null || isFragmentVisible(getParentFragment())) {
                mNeedDispatch = false;
                safeDispatchUserVisibleHint(true);
            }
        }

        afterInflateView(savedInstanceState);
    }


    protected void afterInflateView(Bundle savedInstanceState) {
        initBeforeInitView();
        initViews(savedInstanceState);
        initFragmentData();
        initListener();
    }

    protected void initBeforeInitView() {

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    /**
     * 这个是每次fragment显示在人的面前都会调用一次（综合hidechange、onResume、setVisiHint） 无论是replace showhide 还是viewpager
     */
    public void OnFragmentTrueResume() {

    }

    protected void OnFragmentTruePause() {
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
        ToastUtils.showShort(s);
    }

    @Override
    public void toast(int id) {
        ToastUtils.showShort(id);
    }

    @Override
    public void toastLong(CharSequence s) {
        ToastUtils.showLong(s);
    }

    @Override
    public void toastLong(int id) {
        ToastUtils.showLong(id);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (hasBus) {
            EventBus.getDefault().unregister(this);
        }

    }

    public abstract int getCreateViewLayoutId();

    @Override
    public void onResume() {
        super.onResume();
        if (!mIsFirstVisible) {
            if (!mIsSupportVisible && !mInvisibleWhenLeave && isFragmentVisible(this)) {
                mNeedDispatch = false;
                dispatchSupportVisible(true);
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mIsSupportVisible && isFragmentVisible(this)) {
            mNeedDispatch = false;
            mInvisibleWhenLeave = false;
            dispatchSupportVisible(false);
        } else {
            mInvisibleWhenLeave = true;
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden && !isResumed()) {
            //if fragment is shown but not resumed, ignore...
            mInvisibleWhenLeave = false;
            return;
        }
        if (hidden) {
            safeDispatchUserVisibleHint(false);
        } else {
            enqueueDispatchVisible();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isResumed() || (!isAdded() && isVisibleToUser)) {
            if (!mIsSupportVisible && isVisibleToUser) {
                safeDispatchUserVisibleHint(true);
            } else if (mIsSupportVisible && !isVisibleToUser) {
                dispatchSupportVisible(false);
            }
        }
    }

    private void safeDispatchUserVisibleHint(boolean visible) {
        if (mIsFirstVisible) {
            if (!visible) return;
            enqueueDispatchVisible();
        } else {
            dispatchSupportVisible(visible);
        }
    }

    private void enqueueDispatchVisible() {
        getHandler().post(new Runnable() {
            @Override
            public void run() {
                dispatchSupportVisible(true);
            }
        });
    }

    private void dispatchSupportVisible(boolean visible) {
        if (visible && isParentInvisible()) return;

        if (mIsSupportVisible == visible) {
            mNeedDispatch = true;
            return;
        }

        mIsSupportVisible = visible;

        if (visible) {
            if (checkAddState()) return;
            this.OnFragmentTrueResume();

            if (mIsFirstVisible) {
                mIsFirstVisible = false;
                lazyLoad();
                lazyLoad(mSaveInstanceState);
            }
            dispatchChild(true);
        } else {
            dispatchChild(false);
            this.OnFragmentTruePause();
        }
    }


    private void dispatchChild(boolean visible) {
        if (!mNeedDispatch) {
            mNeedDispatch = true;
        } else {
            if (checkAddState()) return;
            FragmentManager fragmentManager = getChildFragmentManager();
            List<Fragment> childFragments = getActiveFragments(fragmentManager);
            if (childFragments != null) {
                for (Fragment child : childFragments) {
                    if (child instanceof BaseFragment && !child.isHidden() && child.getUserVisibleHint()) {
                        ((BaseFragment) child).dispatchSupportVisible(visible);
                    }
                }
            }
        }
    }

    private boolean isParentInvisible() {
        Fragment parentFragment = getParentFragment();

        if (parentFragment instanceof BaseFragment) {
            return !((BaseFragment) parentFragment).isSupportVisible();
        }

        return parentFragment != null && !parentFragment.isVisible();
    }

    private boolean checkAddState() {
        if (!isAdded()) {
            mIsSupportVisible = !mIsSupportVisible;
            return true;
        }
        return false;
    }

    private boolean isFragmentVisible(Fragment fragment) {
        return !fragment.isHidden() && fragment.getUserVisibleHint();
    }

    public boolean isSupportVisible() {
        return mIsSupportVisible;
    }

    private Handler getHandler() {
        if (mHandler == null) {
            mHandler = new Handler(Looper.getMainLooper());
        }
        return mHandler;
    }


    public List<Fragment> getActiveFragments(FragmentManager fragmentManager) {
        return fragmentManager.getFragments();
    }

}
