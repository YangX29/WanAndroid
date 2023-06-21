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
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.math.abs


/**
 * @author: Yang
 * @date: 2023/1/12
 * @description: 屏幕工具类
 * TODO 使用[WindowInsets]的一系列方法有待验证，添加异形屏判断
 */
object ScreenUtils {

    /**
     * 获取屏幕宽度，不包含状态栏和导航栏高度
     */
    fun getScreenWidth(): Int {
        return Resources.getSystem().displayMetrics.widthPixels
    }

    /**
     * 获取屏幕高度，不包含状态栏和导航栏高度
     */
    fun getScreenHeight(): Int {
        return Resources.getSystem().displayMetrics.heightPixels
    }

    /**
     * 获取屏幕宽高比，不包含状态栏和导航栏高度
     */
    fun getScreenRatio(): Float {
        return (getScreenWidth() / getScreenHeight()).toFloat()
    }

    /**
     * 获取真实屏幕宽度
     */
    fun getScreenRealWidth(context: Context): Int {
        return getScreenRealSize(context)?.x ?: 0
    }

    /**
     * 获取真实屏幕高度
     */
    fun getScreenRealHeight(context: Context): Int {
        return getScreenRealSize(context)?.y ?: 0
    }

    /**
     * 获取屏幕宽高比
     */
    fun getScreenRealRatio(context: Context): Float {
        return (getScreenRealWidth(context) / getScreenRealHeight(context)).toFloat()
    }

    /**
     * 获取屏幕真实大小，包含状态栏和导航栏高度
     */
    fun getScreenRealSize(context: Context): Point? {
        val wm = ContextCompat.getSystemService(context, WindowManager::class.java) ?: return null
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
     * 通过insets获取状态栏高度，需要window添加后才能拿到，可以在[View.post]方法中获取
     */
    fun getStatusBarHeightByInsets(activity: Activity): Int {
        val top = ViewCompat.getRootWindowInsets(activity.window.decorView)
            ?.getInsetsIgnoringVisibility(WindowInsetsCompat.Type.statusBars())?.top ?: 0
        val bottom = ViewCompat.getRootWindowInsets(activity.window.decorView)
            ?.getInsetsIgnoringVisibility(WindowInsetsCompat.Type.statusBars())?.bottom ?: 0
        return abs(top - bottom)
    }

    /**
     * 通过insets获取导航栏高度，需要window添加后才能拿到，可以在[View.post]方法中获取
     */
    fun getNavBarHeightByInsets(activity: Activity): Int {
        val top = ViewCompat.getRootWindowInsets(activity.window.decorView)
            ?.getInsetsIgnoringVisibility(WindowInsetsCompat.Type.navigationBars())?.top ?: 0
        val bottom = ViewCompat.getRootWindowInsets(activity.window.decorView)
            ?.getInsetsIgnoringVisibility(WindowInsetsCompat.Type.navigationBars())?.bottom ?: 0
        return abs(top - bottom)
    }

    /**
     * 获取状态栏高度
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
     * 判断是否存在状态栏
     */
    fun isStatusBarVisible(activity: Activity): Boolean {
        return ViewCompat.getRootWindowInsets(activity.window.decorView)
            ?.isVisible(WindowInsetsCompat.Type.statusBars()) ?: true
    }

    /**
     * 隐藏状态栏
     */
    fun hideStatusBar(activity: Activity) {
        ViewCompat.getWindowInsetsController(activity.window.decorView)
            ?.hide(WindowInsetsCompat.Type.statusBars())
    }

    /**
     * 显示状态栏
     */
    fun showStatusBar(activity: Activity) {
        ViewCompat.getWindowInsetsController(activity.window.decorView)
            ?.show(WindowInsetsCompat.Type.statusBars())
    }

    /**
     * 隐藏状态栏，旧方式
     */
    fun hideStatusBarOld(activity: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            activity.window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            activity.window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        }
    }

    /**
     * 显示状态栏，旧方式
     */
    fun showStatusBarOld(activity: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            activity.window.insetsController?.show(WindowInsets.Type.statusBars())
        } else {
            activity.window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        }
    }

    /**
     * 导航栏是否可见
     */
    fun isNavBarVisible(activity: Activity): Boolean {
        return ViewCompat.getRootWindowInsets(activity.window.decorView)
            ?.isVisible(WindowInsetsCompat.Type.navigationBars()) ?: true
    }

    /**
     * 隐藏导航栏
     */
    fun hideNavBar(activity: Activity) {
        ViewCompat.getWindowInsetsController(activity.window.decorView)
            ?.hide(WindowInsetsCompat.Type.navigationBars())
    }

    /**
     * 显示导航栏
     */
    fun showNavBar(activity: Activity) {
        ViewCompat.getWindowInsetsController(activity.window.decorView)
            ?.show(WindowInsetsCompat.Type.navigationBars())
    }

}