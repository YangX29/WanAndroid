package com.example.wanandroid.utils.view

import android.app.Activity
import android.os.Bundle
import com.example.module_common.utils.app.ActivityStackManager
import com.example.module_common.utils.lifecycle.ActivityLifecycleListener
import com.example.module_common.utils.lifecycle.AppLifecycleManager
import com.example.wanandroid.view.widget.loading.ILoading
import com.example.wanandroid.view.widget.loading.LoadingDialog
import java.util.WeakHashMap

/**
 * @author: Yang
 * @date: 2023/4/5
 * @description: Loading管理类，非线程安全
 */
object LoadingManager : ActivityLifecycleListener {
    //loading集合
    private val loadings by lazy { WeakHashMap<Activity, ILoading>() }

    init {
        //注册activity生命周期监听
        AppLifecycleManager.registerActivityLifecycleListener(this)
    }

    /**
     * 在当前页面显示loading
     */
    fun showCurrentLoading() {
        ActivityStackManager.topActivity()?.apply {
            runOnUiThread {
                showPageLoading(this)
            }
        }
    }

    /**
     * 隐藏当前页面loading
     */
    fun dismissCurrentLoading() {
        ActivityStackManager.topActivity()?.apply {
            runOnUiThread {
                dismissPageLoading(this)
            }
        }
    }


    /**
     * 创建当前页面loading，应该在主线程中调用
     */
    fun createPageLoading(activity: Activity): ILoading {
        return if (loadings.containsKey(activity)) {
            loadings[activity]!!
        } else {
            val loading = LoadingDialog(activity)
            loadings[activity] = loading
            return loading
        }
    }

    /**
     * 显示当前页面loading
     */
    fun showPageLoading(activity: Activity) {
        activity.runOnUiThread {
            val loading = createPageLoading(activity)
            loading.show()
        }
    }

    /**
     * 隐藏当前页面loading
     */
    fun dismissPageLoading(activity: Activity) {
        activity.runOnUiThread {
            loadings[activity]?.dismiss()
        }
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
        //移除当前页面loading
        dismissPageLoading(activity)
        loadings.remove(activity)
    }

    override fun onActivityDestroyed(activity: Activity) {
        //移除当前页面loading
        dismissPageLoading(activity)
        loadings.remove(activity)
    }

}