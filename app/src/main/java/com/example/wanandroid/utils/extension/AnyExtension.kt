package com.example.wanandroid.utils.extension

import com.example.wanandroid.utils.string.GsonUtils

/**
 * 转换为json字符串
 */
fun Any?.toJson(): String {
    return GsonUtils.toJson(this)
}