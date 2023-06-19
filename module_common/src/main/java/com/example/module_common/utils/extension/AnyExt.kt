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