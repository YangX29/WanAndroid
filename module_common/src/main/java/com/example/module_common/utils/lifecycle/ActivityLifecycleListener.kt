package com.example.module_common.utils.lifecycle

import android.app.Activity
import android.os.Bundle

/**
 * @author: Yang
 * @date: 2023/6/19
 * @description: activity生命周期变化监听接口
 */
interface ActivityLifecycleListener {

    fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {}

    fun onActivityStarted(activity: Activity) {}

    fun onActivityResumed(activity: Activity) {}

    fun onActivityPaused(activity: Activity) {}

    fun onActivityStopped(activity: Activity) {}

    fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}

    fun onActivityDestroyed(activity: Activity) {}

}