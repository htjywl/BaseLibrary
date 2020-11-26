package com.htjy.baselibrary.base

import android.app.Activity
import android.content.Context
import android.hardware.display.DisplayManager
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.speech.tts.TextToSpeech
import android.view.Display
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ObjectUtils
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.Utils
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.htjy.baselibrary.widget.imageloader.ImageLoaderUtil
import okhttp3.OkHttpClient
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList

/**
 * For developer startup JPush SDK
 *
 *
 * 一般建议在自定义 Application 类里初始化。也可以在主 Activity 里。
 */
class BaseLibraryApplication : MultiDexApplication(), ViewModelStoreOwner {
    private lateinit var activityList: ArrayList<Activity>
//    var daoInstant: DaoSession? = null

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }



    override fun onCreate() {
        super.onCreate()
        mAppViewModelStore = ViewModelStore()
        //registerPackageChangeBroadcast();
        application = this
        activityList = ArrayList()
        Utils.init(this)
        initLife()

    }

    private lateinit var mAppViewModelStore: ViewModelStore

    private var mFactory: ViewModelProvider.Factory? = null

    override fun getViewModelStore(): ViewModelStore {
        return mAppViewModelStore
    }

    /**
     * 获取一个全局的ViewModel
     */
    fun getAppViewModelProvider(): ViewModelProvider {
        return ViewModelProvider(this, this.getAppFactory())
    }

    private fun getAppFactory(): ViewModelProvider.Factory {
        if (mFactory == null) {
            mFactory = ViewModelProvider.AndroidViewModelFactory.getInstance(this)
        }
        return mFactory as ViewModelProvider.Factory
    }

    val topActivity: Activity?
        get() = if (ObjectUtils.isNotEmpty(activityList)) {
            activityList[activityList.size - 1]
        } else {
            null
        }

    // 添加Activity 到容器中
    fun addActivity(activity: Activity) {
        activityList.add(activity)
    }

    fun removeActivity(activity: Activity) {
        activityList.remove(activity)
    }

    // 遍历所有Activity 并finish
    fun exit() {
        for (activity in activityList) {
            activity.finish()
        }
        System.exit(0)
    } // 遍历所有Activity 并不会exit

    fun finishAllActivity() {
        for (activity in activityList) {
            activity.finish()
        }
    }

    fun containsActivity(activityClass: Class<out Activity?>): Boolean {
        for (activity in activityList) {
            if (activity.javaClass.simpleName == activityClass.simpleName) return true
        }
        return false
    }

    fun exit(count: Int) {
        for (i in 1..count) {
            if (activityList.size - i >= 0) activityList[activityList.size - i].finish()
        }
        System.exit(0)
    }

    private fun initLife() {
        //在Application的oncreate方法里:传入context
        //在activity生命周期callback中拿到顶层activity引用:
        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                if (!activityList.contains(activity)) addActivity(activity)
            }

            override fun onActivityStarted(activity: Activity) {}
            override fun onActivityResumed(activity: Activity) {}
            override fun onActivityPaused(activity: Activity) {}
            override fun onActivityStopped(activity: Activity) {}
            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle?) {}
            override fun onActivityDestroyed(activity: Activity) {
                if (activityList!!.contains(activity)) removeActivity(activity)
            }
        })
    }


    companion object {
        private const val TAG = "MyApplication"
        lateinit var application: BaseLibraryApplication

        @Synchronized
        fun getInstance(): BaseLibraryApplication {
            return application
        }
    }
}