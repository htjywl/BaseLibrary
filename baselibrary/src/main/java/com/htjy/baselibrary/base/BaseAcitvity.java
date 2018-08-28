package com.htjy.baselibrary.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.htjy.baselibrary.utils.FragmentUtils;
import com.htjy.baselibrary.utils.ToastUtils;
import com.htjy.baselibrary.widget.imageloader.listener.KeyboardChangeListener;
import com.lzy.okgo.OkGo;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import org.simple.eventbus.EventBus;

import java.util.List;

import butterknife.ButterKnife;


public abstract class BaseAcitvity extends RxAppCompatActivity implements BaseView {


    protected Activity activity;
    protected ProgressDialog progress;
    protected boolean hasBus = false;
    protected boolean hasListenerForKey = false;
    protected View noDataStubView;
    protected View sysErrStubView;
    private ViewGroup viewMain;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //BaseApplication.getInstance().addActivity(this);
        activity = this;
        /*if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // 设置contentFeature,可使用切换动画
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
            Transition explode = null;
            explode = TransitionInflater.from(this).inflateTransition(R.transition.explode);
            getWindow().setEnterTransition(explode);
        }*/
        if (getLayoutId() != 0) {
            setContentView(getLayoutId());
        }
        initBind();
        hasBus = haveBus();
        if (hasBus) {
            EventBus.getDefault().register(this);
        }
        hasListenerForKey = haveListenerForKey();
        if (hasListenerForKey) {
            setListenerToRootView();
        }
        //viewMain = (ViewGroup)findViewById(R.id.loadingLayout);
        //findViewById(R.id.loadingLayout);
        initViews(savedInstanceState);
        initData();
        initListener();
        initStateLayout();
    }

  /*  @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            if (getCurrentFocus() != null && getCurrentFocus().getWindowToken() != null && isShouldHideInput(getCurrentFocus(), ev)) {
                KeyboardUtils.hideSoftInput(this);
            }
        }
        return super.dispatchTouchEvent(ev);
    }*/

    private boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left
                    + v.getWidth();
            if (event.getY() >= top && event.getY() < bottom && event.getX() >= left && event.getX() < right) {
                // 点击EditText的事件，忽略它。
                return false;
            } else {
                return true;
            }
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditView上，和用户用轨迹球选择其他的焦点
        return false;
    }

    protected void keyboardStatus(boolean toShow, int keyboardHeight) {
        List<FragmentUtils.FragmentNode> fragmentNodes = FragmentUtils.getAllFragments(getSupportFragmentManager());
        dispatchKeyStatus(fragmentNodes, toShow, keyboardHeight);
    }

    private void dispatchKeyStatus(List<FragmentUtils.FragmentNode> fragmentNodes, boolean toShow, int keyboardHeight) {
        for (FragmentUtils.FragmentNode fragmentNode : fragmentNodes) {
            Fragment fragment = fragmentNode.getFragment();
            if (fragment instanceof BaseFragment) {
                ((BaseFragment) fragment).keyboardStatus(toShow, keyboardHeight);
            }
            dispatchKeyStatus(fragmentNode.getNext(), toShow, keyboardHeight);
        }
    }

    private KeyboardChangeListener keyboardChangeListener;

    private void setListenerToRootView() {
        if (keyboardChangeListener == null) {
            keyboardChangeListener = new KeyboardChangeListener(this);
        }
        keyboardChangeListener.setKeyBoardListener(new KeyboardChangeListener.KeyBoardListener() {
            @Override
            public void onKeyboardChange(boolean isShow, int keyboardHeight) {
                keyboardStatus(isShow, keyboardHeight);
            }
        });
    }

    private void removeListenerToRootView() {
        if (keyboardChangeListener != null) {
            keyboardChangeListener.destroy();
        }
    }

    protected void initBind() {
        ButterKnife.bind(activity);
    }

    protected int getLayoutId() {
        return 0;
    }

    protected abstract void initViews(Bundle savedInstanceState);

    protected abstract void initStateLayout();

    protected abstract void initData();

    protected abstract void initListener();


    protected boolean haveBus() {
        return false;
    }

    protected boolean haveListenerForKey() {
        return false;
    }

    public void finishPost() {
        getWindow().getDecorView().postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 0);
    }

    /**
     * 打开一个Activity 默认 不关闭当前activity
     */
    public void gotoActivity(Class<?> clz) {
        gotoActivity(clz, false, null);
    }

    public void gotoActivity(Class<?> clz, boolean isCloseCurrentActivity) {
        gotoActivity(clz, isCloseCurrentActivity, null);
    }

    public void gotoActivity(Class<?> clz, boolean isCloseCurrentActivity, Bundle ex) {
        Intent intent = new Intent(this, clz);
        if (ex != null) intent.putExtras(ex);
       /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
        }else{
            startActivity(intent);
        }*/
        startActivity(intent);
        if (isCloseCurrentActivity) {
            finishPost();
        }
    }

    /**
     * 打开一个Activity , 并要求返回结果
     */
    public void gotoActivityForResult(Class<?> clz, int code) {
        gotoActivityForResult(clz, code, null);
    }

    public void gotoActivityForResult(Class<?> clz, int code, Bundle ex) {
        Intent intent = new Intent(this, clz);
        if (ex != null) intent.putExtras(ex);
        startActivityForResult(intent, code);
    }


    @Override
    public void showProgress() {
        if (progress == null) {
            progress = new ProgressDialog(activity);
        }
        progress.show();
    }

    @Override
    public void hideProgress() {
        if (progress != null && progress.isShowing()) {
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
    public Activity getThisActivity() {
        return activity;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (hasBus) {
            EventBus.getDefault().unregister(this);
        }
        if (hasListenerForKey) {
            removeListenerToRootView();
        }
        OkGo.getInstance().cancelTag(this);
    }

    /**
     * 隐藏虚拟按键，并且全屏
     */
    protected void hideBottomUIMenu() {
        //隐藏虚拟按键，并且全屏
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if (Build.VERSION.SDK_INT >= 19) {
            //for new api versions.
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }
}
