package com.example.module_common.utils.log

import android.util.Log
import com.example.module_common.BuildConfig

/**
 * @author: Yang
 * @date: 2023/1/7
 * @description: 日志输出类，用于打印日志，取缔[Log]，DEBUG模式下输出日志，RELEASE模式下不输出日志，减少性能消耗
 */
object MLog {

    /**
     * 是否为debug模式，默认根据[BuildConfig.DEBUG]判断
     */
    private var isDebug = BuildConfig.DEBUG

    /**
     * 设置是否为debug模式
     */
    fun setDebug(debug: Boolean) {
        isDebug = debug
    }

    /**
     * 打印日志信息
     * @see Log.v
     */
    fun v(tag: String? = null, msg: String, tr: Throwable? = null) {
        checkDebug {
            Log.v(tag, msg, tr)
        }
    }

    /**
     * 打印日志信息
     * @see Log.v
     */
    fun d(tag: String? = null, msg: String, tr: Throwable? = null) {
        checkDebug {
            Log.d(tag, msg, tr)
        }
    }

    /**
     * 打印日志信息
     * @see Log.w
     */
    fun w(tag: String? = null, msg: String, tr: Throwable? = null) {
        checkDebug {
            Log.w(tag, msg, tr)
        }
    }

    /**
     * 打印日志信息
     * @see Log.e
     */
    fun e(tag: String? = null, msg: String, tr: Throwable? = null) {
        checkDebug {
            Log.e(tag, msg, tr)
        }
    }


    /**
     * 检查是否为debug模式，如果是debug模式即[isDebug]为true执行[action]
     */
    private fun checkDebug(action: ()->Unit) {
        if (!isDebug) {
            action.invoke()
        }
    }

}