package com.example.module_common.utils.screen

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.Point
import android.os.Build
import android.view.WindowInsets
import android.view.WindowManager


/**
 * @author: Yang
 * @date: 2023/1/12
 * @description: 屏幕工具类
 */
object ScreenUtil {

    /**
     * 获取屏幕宽度
     * TODO 是否包含状态栏和导航栏高度
     */
    fun getScreenWidth(): Int {
        return Resources.getSystem().displayMetrics.widthPixels
    }

    /**
     * 获取屏幕高度
     * TODO 是否包含状态栏和导航栏高度
     */
    fun getScreenHeight(): Int {
        return Resources.getSystem().displayMetrics.heightPixels
    }

    /**
     * 获取屏幕宽高比
     */
    fun getScreenRatio(): Float {
        return (getScreenWidth() / getScreenHeight()).toFloat()
    }

    /**
     * 获取屏幕宽度
     */
    fun getScreenWidth(context: Context): Int {
        return getScreenSize(context)?.x ?: 0
    }

    /**
     * 获取屏幕高度，不包含状态栏
     */
    fun getScreenHeight(context: Context): Int {
        return getScreenSize(context)?.y ?: 0
    }

    /**
     * 获取屏幕宽高比
     */
    fun getScreenRatio(context: Context): Float {
        return (getScreenWidth(context) / getScreenHeight(context)).toFloat()
    }

    /**
     * 获取屏幕大小
     * TODO 是否包含状态栏和导航栏高度
     */
    fun getScreenSize(context: Context): Point? {
        val wm =
            context.applicationContext.getSystemService(Context.WINDOW_SERVICE) as? WindowManager
                ?: return null
        val point = Point()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            point.x = wm.currentWindowMetrics.bounds.width()
            point.y = wm.currentWindowMetrics.bounds.height()
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            wm.defaultDisplay.getRealSize(point)
        } else {
            wm.defaultDisplay.getSize(point)
        }
        return point
    }

    /**
     * 获取状态栏高度
     * TODO 是否包含状态栏和导航栏高度
     */
    @SuppressLint("InternalInsetResource")
    fun getStatusBarHeight(): Int {
        var result = 0
        try {
            val resourceId =
                Resources.getSystem().getIdentifier("status_bar_height", "dimen", "android")
            if (resourceId > 0) {
                result = Resources.getSystem().getDimensionPixelSize(resourceId)
            }
        } catch (e: Resources.NotFoundException) {
            e.printStackTrace()
        }
        return result
    }

    /**
     * 获取导航栏高度
     * TODO 是否包含状态栏和导航栏高度
     */
    @SuppressLint("InternalInsetResource")
    fun getNavBarHeight(): Int {
        var result = 0
        try {
            val resourceId =
                Resources.getSystem().getIdentifier("navigation_bar_height", "dimen", "android")
            if (resourceId > 0) {
                result = Resources.getSystem().getDimensionPixelSize(resourceId)
            }
        } catch (e: Resources.NotFoundException) {
            e.printStackTrace()
        }
        return result
    }

    /**
     * 是否为横屏
     */
    fun isLandscape(context: Context): Boolean {
        return context.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    }

    /**
     * 是否为竖屏
     */
    fun isPortrait(context: Context): Boolean {
        return context.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT
    }

    /**
     * 设置横屏
     */
    fun setLandscape(activity: Activity) {
        activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
    }

    /**
     * 设置竖屏
     */
    @SuppressLint("SourceLockedOrientationActivity")
    fun setPortrait(activity: Activity) {
        activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    /**
     * 隐藏状态栏
     */
    fun hideStatusBar(activity: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            activity.window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            activity.window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        }
    }

    /**
     * 显示状态栏
     */
    fun showStatusBar(activity: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            activity.window.insetsController?.show(WindowInsets.Type.statusBars())
        } else {
            activity.window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        }
    }

}