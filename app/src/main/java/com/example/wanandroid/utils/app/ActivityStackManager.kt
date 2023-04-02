package com.example.wanandroid.utils.app

import android.app.Activity
import android.app.Application
import android.os.Bundle
import java.util.Stack

/**
 * @author: Yang
 * @date: 2023/4/2
 * @description: Activity堆栈管理类
 */
object ActivityStackManager : Application.ActivityLifecycleCallbacks {

    private val stack = Stack<Activity>()

    /**
     * 注册app生命周期监听
     */
    fun register(app: Application) {
        app.registerActivityLifecycleCallbacks(this)
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

    override fun onActivityStarted(activity: Activity) {
    }

    override fun onActivityResumed(activity: Activity) {
    }

    override fun onActivityPaused(activity: Activity) {
    }

    override fun onActivityStopped(activity: Activity) {
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
    }

    override fun onActivityDestroyed(activity: Activity) {
        removeActivity(activity)
    }

}