package com.example.modele_net.common.utils

/**
 * [NetLog.v]方法的顶级方法，简化调用
 */
fun logV(tag: String? = null, msg: String, tr: Throwable? = null) {
    NetLog.v(tag, msg, tr)
}

/**
 * [NetLog.d]方法的顶级方法，简化调用
 */
fun logD(tag: String? = null, msg: String, tr: Throwable? = null) {
    NetLog.d(tag, msg, tr)
}

/**
 * [NetLog.i]方法的顶级方法，简化调用
 */
fun logI(tag: String? = null, msg: String, tr: Throwable? = null) {
    NetLog.i(tag, msg, tr)
}

/**
 * [NetLog.w]方法的顶级方法，简化调用
 */
fun logW(tag: String? = null, msg: String, tr: Throwable? = null) {
    NetLog.w(tag, msg, tr)
}

/**
 * [NetLog.e]方法的顶级方法，简化调用
 */
fun logE(tag: String? = null, msg: String, tr: Throwable? = null) {
    NetLog.e(tag, msg, tr)
}