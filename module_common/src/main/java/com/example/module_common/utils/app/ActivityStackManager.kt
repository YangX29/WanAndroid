package com.example.module_common.utils.app

import android.app.Activity
import android.os.Bundle
import com.example.module_common.utils.lifecycle.ActivityLifecycleListener
import com.example.module_common.utils.lifecycle.AppLifecycleManager
import java.util.Stack

/**
 * @author: Yang
 * @date: 2023/4/2
 * @description: Activity堆栈管理类，使用前请先初始化[AppLifecycleManager]
 */
object ActivityStackManager : ActivityLifecycleListener {

    private val stack by lazy { Stack<Activity>() }

    init {
        //注册activity生命周期监听
        AppLifecycleManager.registerActivityLifecycleListener(this)
    }

    /**
     * 页面数量
     */
    fun activityCount(): Int {
        return stack.size
    }

    /**
     * 判断是否拥有activity
     */
    fun hasActivity(activity: Activity): Boolean {
        return stack.contains(activity)
    }

    /**
     * 顶部activity
     */
    fun topActivity(): Activity? {
        return if (stack.isEmpty()) null else stack.peek()
    }

    /**
     * 弹出栈顶activity
     */
    fun popTopActivity() {
        //获取栈顶activity
        val activity = if (stack.isEmpty()) null else stack.pop()
        //结束activity
        activity?.finish()
    }

    /**
     * 结束所有Activity
     */
    fun popAllActivity() {
        val iterator = stack.iterator()
        while (iterator.hasNext()) {
            val activity = iterator.next()
            //结束activity
            activity.finish()
            //移除
            iterator.remove()
        }
    }

    /**
     * 添加activity
     */
    private fun addActivity(activity: Activity) {
        stack.push(activity)
    }

    /**
     * 移除activity
     */
    private fun removeActivity(activity: Activity) {
        stack.remove(activity)
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        addActivity(activity)
    }

    override fun onActivityDestroyed(activity: Activity) {
        removeActivity(activity)
    }

}