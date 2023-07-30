package com.example.module_common.utils.extension

/**
 * 查找首个满足条件的index，如果不存在返回-1
 */
inline fun <T> Iterable<T>.findFirstIndex(predicate: (T) -> Boolean): Int {
    forEachIndexed { index, item ->
        if (predicate.invoke(item)) return index
    }
    return -1
}

/**
 * 查找最后满足条件的index，如果不存在返回-1
 */
inline fun <T> Iterable<T>.findLastIndex(predicate: (T) -> Boolean): Int {
    var result = -1
    forEachIndexed { index, item ->
        if (predicate.invoke(item)) {
            result = index
        }
    }
    return result
}