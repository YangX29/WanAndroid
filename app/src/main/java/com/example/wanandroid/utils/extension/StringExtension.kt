package com.example.wanandroid.utils.extension

import com.example.wanandroid.utils.string.GsonUtils

/**
 * json转换为对象
 */
fun <T> String?.fromJson(classOfT: Class<T>): T? {
    return GsonUtils.fromJson(this, classOfT)
}

/**
 * json转换为列表
 */
fun <T> String?.fromJsonList(): List<T> {
    return GsonUtils.fromJsonList(this)
}