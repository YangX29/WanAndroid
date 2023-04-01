package com.example.wanandroid.utils.string

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * @author: Yang
 * @date: 2023/4/1
 * @description: Gson工具类
 */
object GsonUtils {

    private val gson by lazy { Gson() }

    /**
     * 对象转换为json
     */
    fun toJson(src: Any?): String {
        return gson.toJson(src)
    }

    /**
     * json转换对象
     */
    fun <T> fromJson(json: String?, classOfT: Class<T>): T? {
        return gson.fromJson(json, classOfT)
    }

    /**
     * json转换为List
     */
    fun <T> fromJsonList(json: String?): MutableList<T> {
        return gson.fromJson(json, object : TypeToken<ArrayList<T>>() {}.type) ?: mutableListOf<T>()
    }

}