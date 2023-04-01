package com.example.module_common.utils.sp

import android.content.SharedPreferences
import com.google.gson.Gson

/**
 * 保存值
 */
fun SharedPreferences.put(key: String, value: Any) {
    with(edit()) {
        when (value) {
            is String -> putString(key, value)
            is Int -> putInt(key, value)
            is Long -> putLong(key, value)
            is Float -> putFloat(key, value)
            is Boolean -> putBoolean(key, value)
            else -> putString(key, Gson().toJson(value))
        }.apply()
    }
}

/**
 * 获取对应值
 */
inline fun <reified T> SharedPreferences.get(key: String, defaultValue: T? = null): T {
    return when (T::class.java) {
        String::class.java -> getString(key, defaultValue as? String) as T
        Int::class.java -> getInt(key, defaultValue as? Int ?: 0) as T
        Long::class.java -> getLong(key, defaultValue as? Long ?: 0L) as T
        Float::class.java -> getFloat(key, defaultValue as? Float ?: 0f) as T
        Boolean::class.java -> getBoolean(key, defaultValue as? Boolean ?: false) as T
        else -> Gson().fromJson(getString(key, null), T::class.java)
    }
}

/**
 * 移除key值
 */
fun SharedPreferences.remove(key: String) {
    edit().remove(key).apply()
}

/**
 * 清空key值
 */
fun SharedPreferences.clear() {
    edit().clear().apply()
}