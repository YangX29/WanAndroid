package com.example.wanandroid.utils.ime

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.example.wanandroid.app.WanApplication

/**
 * @author: Yang
 * @date: 2023/3/29
 * @description: 键盘工具类
 */
object ImeUtils {

    /**
     * 隐藏键盘
     */
    fun hideSoftInput(activity: Activity) {
        val imm =
            WanApplication.context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                ?: return
        val view = activity.window.currentFocus ?: return
        view.clearFocus()
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    /**
     * 隐藏键盘
     */
    fun hideSoftInput(view: View) {
        val imm =
            view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                ?: return
        imm.hideSoftInputFromWindow(view.windowToken, 0)

    }

    /**
     * 显示键盘
     */
    fun showSoftInput(view: View) {
        val imm =
            view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                ?: return
        view.requestFocus()
        imm.showSoftInput(view, 0)
    }

    /**
     * 键盘是否显示
     */
    fun softInputIsShowing(context: Context): Boolean {
        val imm =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                ?: return false
        return imm.isActive
    }

}