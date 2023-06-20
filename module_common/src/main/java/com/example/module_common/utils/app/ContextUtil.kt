package com.example.module_common.utils.app

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context


/**
 * @author: Yang
 * @date: 2023/5/10
 * @description: Context工具类，用于获取全局Context
 */
@SuppressLint("StaticFieldLeak")
object ContextUtil {

    private var mApp: Application? = null

    /**
     * 初始化
     */
    fun init(app: Application) {
        mApp = app
    }

    /**
     * 初始化
     */
    fun init(app: Context) {
        mApp = app.applicationContext as Application?
    }

    /**
     * 获取context实例
     */
    fun getContext(): Context {
        return mApp ?: getApplicationByReflect()
    }

    /**
     * 获取application实例
     */
    fun getApp(): Application {
        return mApp ?: getApplicationByReflect()
    }

    /**
     * 反射获取application对象
     */
    @SuppressLint("PrivateApi")
    fun getApplicationByReflect(): Application {
        try {
            //反射获取application对象
            val activityThread = Class.forName("android.app.ActivityThread")
            val currentAt = activityThread.getMethod("currentActivityThread").invoke(null)
            mApp = activityThread.getMethod("getApplication").invoke(currentAt) as? Application
                ?: throw NullPointerException("you should init first")
            return mApp!!
        } catch (e: Exception) {
            e.printStackTrace()
        }
        throw NullPointerException("you should init first")
    }

}