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
     * log打印优先级，只有高于或者等于该优先级才会打印日志，默认为VERBOSE，详见[Log]
     */
    private var logLevel = Log.VERBOSE

    /**
     * 是否启用log，默认根据[BuildConfig.DEBUG]判断
     */
    private var isOpen = BuildConfig.DEBUG

    /**
     * 设置启用log
     */
    fun openLog(open: Boolean) {
        isOpen = open
    }

    /**
     * 设置log优先级，只有等于或高于[level]才会打印日志
     */
    fun setLogLevel(level: Int) {
        logLevel = level
    }

    /**
     * 打印日志信息
     * @see Log.v
     */
    fun v(tag: String? = null, msg: String, tr: Throwable? = null) {
        checkOpen(Log.VERBOSE) {
            Log.v(tag, msg, tr)
        }
    }

    /**
     * 打印日志信息
     * @see Log.v
     */
    fun d(tag: String? = null, msg: String, tr: Throwable? = null) {
        checkOpen(Log.DEBUG) {
            Log.d(tag, msg, tr)
        }
    }

    /**
     * 打印日志信息
     * @see Log.i
     */
    fun i(tag: String? = null, msg: String, tr: Throwable? = null) {
        checkOpen(Log.INFO) {
            Log.i(tag, msg, tr)
        }
    }


    /**
     * 打印日志信息
     * @see Log.w
     */
    fun w(tag: String? = null, msg: String, tr: Throwable? = null) {
        checkOpen(Log.WARN) {
            Log.w(tag, msg, tr)
        }
    }

    /**
     * 打印日志信息
     * @see Log.e
     */
    fun e(tag: String? = null, msg: String, tr: Throwable? = null) {
        checkOpen(Log.ERROR) {
            Log.e(tag, msg, tr)
        }
    }


    /**
     * 检查日志是否开启，如果是[isOpen]为true执行[action]
     */
    private fun checkOpen(level: Int, action: () -> Unit) {
        if (isOpen) {
            //只有大于log等级才会输出日志
            if (level >= logLevel) {
                action.invoke()
            }
        }
    }

}