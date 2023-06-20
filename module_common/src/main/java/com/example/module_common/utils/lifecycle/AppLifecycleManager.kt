package com.example.module_common.utils.lifecycle

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner

/**
 * @author: Yang
 * @date: 2023/6/19
 * @description: app生命周期管理类，使用前请先初始化[AppLifecycleManager]
 */
object AppLifecycleManager : Application.ActivityLifecycleCallbacks, LifecycleEventObserver {

    //activity生命周期监听集合
    private val activityLifecycleListeners by lazy { mutableSetOf<ActivityLifecycleListener>() }

    //app状态监听集合
    private val appStateListeners by lazy { mutableSetOf<AppStateListener>() }

    //应用进程状态监听集合
    private val processStateListeners by lazy { mutableSetOf<ProcessStateListener>() }

    //当前活跃activity数量
    private var activityPageCount = 0

    /**
     * 注册app生命周期监听
     */
    fun register(app: Application) {
        //注册activity生命周期
        app.registerActivityLifecycleCallbacks(this)
        //注册应用进程监听
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
    }

    /**
     * 注销app生命周期
     */
    fun unregister(app: Application) {
        //注销activity生命周期
        app.unregisterActivityLifecycleCallbacks(this)
        //注销应用进程监听
        ProcessLifecycleOwner.get().lifecycle.removeObserver(this)
    }

    /**
     * 注册Activity生命周期监听
     */
    fun registerActivityLifecycleListener(listener: ActivityLifecycleListener) {
        activityLifecycleListeners.add(listener)
    }

    /**
     * 注销Activity生命周期监听
     */
    fun unregisterActivityLifecycleListener(listener: ActivityLifecycleListener) {
        activityLifecycleListeners.remove(listener)
    }

    /**
     * 注册app状态变化监听，通过[Application.ActivityLifecycleCallbacks]实现
     */
    fun registerAppStateListener(listener: AppStateListener) {
        appStateListeners.add(listener)
    }


    /**
     * 注销app状态变化监听，通过[Application.ActivityLifecycleCallbacks]实现
     */
    fun unregisterAppStateListener(listener: AppStateListener) {
        appStateListeners.remove(listener)
    }

    /**
     * 注册进程状态变化监听，通过[ProcessLifecycleOwner]实现
     */
    fun registerProcessStateListener(listener: ProcessStateListener) {
        processStateListeners.add(listener)
    }


    /**
     * 注销进程状态变化监听，通过[ProcessLifecycleOwner]实现
     */
    fun unregisterProcessStateListener(listener: ProcessStateListener) {
        processStateListeners.remove(listener)
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        //回调activity生命周期
        activityLifecycleListeners.forEach {
            it.onActivityCreated(activity, savedInstanceState)
        }
    }

    override fun onActivityStarted(activity: Activity) {
        //回调activity生命周期
        activityLifecycleListeners.forEach {
            it.onActivityStarted(activity)
        }
        //更新活跃activity数量
        activityPageCount++
        //进入前台
        //TODO 在启动app闪屏结束activity时存在问题，此时可能会调用onBackground然后再次调用onForeground
        if (activityPageCount == 1) {
            appStateListeners.forEach {
                it.onForeground()
            }
        }
    }

    override fun onActivityResumed(activity: Activity) {
        //回调activity生命周期
        activityLifecycleListeners.forEach {
            it.onActivityResumed(activity)
        }
    }

    override fun onActivityPaused(activity: Activity) {
        //回调activity生命周期
        activityLifecycleListeners.forEach {
            it.onActivityPaused(activity)
        }
    }

    override fun onActivityStopped(activity: Activity) {
        //回调activity生命周期
        activityLifecycleListeners.forEach {
            it.onActivityStopped(activity)
        }
        //更新活跃activity数量
        activityPageCount--
        //退入后台
        if (activityPageCount == 0) {
            appStateListeners.forEach {
                it.onBackground()
            }
        }
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
        //回调activity生命周期
        activityLifecycleListeners.forEach {
            it.onActivitySaveInstanceState(activity, outState)
        }
    }

    override fun onActivityDestroyed(activity: Activity) {
        //回调activity生命周期
        activityLifecycleListeners.forEach {
            it.onActivityDestroyed(activity)
        }
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when (event) {
            Lifecycle.Event.ON_START -> {//进入前台
                processStateListeners.forEach {
                    it.onForeground()
                }
            }

            Lifecycle.Event.ON_STOP -> {//进入后台
                processStateListeners.forEach {
                    it.onBackground()
                }
            }

            else -> {}
        }
        //回调process生命周期
        processStateListeners.forEach {
            it.onStateChange(event)
        }
    }

}