package com.example.module_common.log

import com.example.module_common.utils.log.MLog

/**
 * [MLog.v]方法的顶级方法，简化调用
 */
fun logV(tag: String? = null, msg: String, tr: Throwable? = null) {
    MLog.v(tag, msg, tr)
}

/**
 * [MLog.d]方法的顶级方法，简化调用
 */
fun logD(tag: String? = null, msg: String, tr: Throwable? = null) {
    MLog.d(tag, msg, tr)
}

/**
 * [MLog.i]方法的顶级方法，简化调用
 */
fun logI(tag: String? = null, msg: String, tr: Throwable? = null) {
    MLog.i(tag, msg, tr)
}

/**
 * [MLog.w]方法的顶级方法，简化调用
 */
fun logW(tag: String? = null, msg: String, tr: Throwable? = null) {
    MLog.w(tag, msg, tr)
}

/**
 * [MLog.e]方法的顶级方法，简化调用
 */
fun logE(tag: String? = null, msg: String, tr: Throwable? = null) {
    MLog.e(tag, msg, tr)
}