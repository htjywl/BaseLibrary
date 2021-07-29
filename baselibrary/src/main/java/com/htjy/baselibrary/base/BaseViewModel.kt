package com.htjy.baselibrary.base

import android.util.Log
import androidx.lifecycle.*
import com.blankj.utilcode.util.ToastUtils
import com.htjy.baselibrary.livedata.event.EventLiveData


open class BaseViewModel : ViewModel(), ViewModelLifecycle, ViewBehavior {
    private lateinit var lifcycleOwner: LifecycleOwner
    val loadingChange: UiLoadingChange by lazy { UiLoadingChange() }

    /**
     * 内置封装好的可通知Activity/fragment 显示隐藏加载框
     */
    inner class UiLoadingChange {
        //显示加载框
        val showDialog by lazy { EventLiveData<String>() }

        //隐藏
        val dismissDialog by lazy { EventLiveData<Boolean>() }
    }

    override fun onAny(owner: LifecycleOwner, event: Lifecycle.Event) {
        this.lifcycleOwner = owner
    }

    override fun onCreate() {
        Log.d(Companion.TAG, "onCreate: " + javaClass.name)
    }

    override fun onStart() {
        Log.d(Companion.TAG, "onStart: " + javaClass.name)
    }

    override fun onResume() {
        Log.d(Companion.TAG, "onResume: " + javaClass.name)
    }

    override fun onPause() {
        Log.d(Companion.TAG, "onPause: " + javaClass.name)
    }

    override fun onStop() {
        Log.d(Companion.TAG, "onStop: " + javaClass.name)
    }

    override fun onDestroy() {
        Log.d(Companion.TAG, "onDestroy: " + javaClass.name)
    }


    override fun showToast(text: String) {
        ToastUtils.showShort(text)
    }

    override fun showToast(resId: Int) {
        ToastUtils.showShort(resId)
    }

    companion object {
        private const val TAG = "BaseViewModel"
    }


}

interface ViewModelLifecycle : LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    fun onAny(owner: LifecycleOwner, event: Lifecycle.Event)

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate()

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart()

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume()

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause()

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop()

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy()
}

interface ViewBehavior {
    /**
     * 弹出Toast提示
     */
    fun showToast(text: String)
    fun showToast(resId: Int)
}