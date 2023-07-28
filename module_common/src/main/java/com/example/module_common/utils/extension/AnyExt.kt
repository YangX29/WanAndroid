package com.example.module_common.utils.extension

/**
 * 检查是否为null
 */
inline fun <reified T> T?.checkNull(notNullAction: T.() -> Unit, nullAction: () -> Unit) {
    if (this == null) {
        nullAction.invoke()
    } else {
        notNullAction.invoke(this)
    }
}

/**
 * 判断是否为null
 */
fun Any?.isNull(): Boolean = this == null

/**
 * 如果为null则执行
 */
fun Any?.ifNull(action: () -> Unit) {
    if (this == null) {
        action.invoke()
    }
}

/**
 * 如果不为null则执行
 */
fun Any?.ifNotNull(action: () -> Unit) {
    if (this != null) {
        action.invoke()
    }
}

/**
 * 如果满足条件则使用当前值，否则使用默认值
 * 同 if(predicate) this else default
 */
inline fun <T> T.takeIfDefault(predicate: (T) -> Boolean, default: T): T {
    return takeIf(predicate) ?: default
}

/**
 * 如果不满足条件则使用当前值，否则使用默认值
 * 同 if(!predicate) this else default
 */
inline fun <T> T.takeUnlessDefault(predicate: (T) -> Boolean, default: T): T {
    return takeUnless(predicate) ?: default
}
