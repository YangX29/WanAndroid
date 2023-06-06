package com.example.module_common.utils.extension

import android.content.SharedPreferences
import com.google.gson.Gson

/**
 * 保存值，异步方法
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
inline fun <reified T> SharedPreferences.get(key: String, defaultValue: T? = null): T? {
    //基本类型需要使用java.lang包下的类，否则泛型的类型推断会出错
    val result: Any? = when (T::class.java) {
        java.lang.String::class.java -> getString(key, defaultValue as? String)
        java.lang.Integer::class.java -> getInt(key, defaultValue as? Int ?: 0)
        java.lang.Long::class.java -> getLong(key, defaultValue as? Long ?: 0L)
        java.lang.Float::class.java -> getFloat(key, defaultValue as? Float ?: 0f)
        java.lang.Boolean::class.java -> getBoolean(key, defaultValue as? Boolean ?: false)
        else -> {
            try {
                Gson().fromJson(getString(key, null), T::class.java)
            } catch (e: Exception) {
                null
            }
        }
    }
    return result as? T
}

/**
 * 移除key值，异步方法
 */
fun SharedPreferences.remove(key: String) {
    edit().remove(key).apply()
}

/**
 * 清空key值，异步方法
 */
fun SharedPreferences.clear() {
    edit().clear().apply()
}

/**
 * 保存值，同步方法，非必要请使用[put]
 * @return 修改结果
 */
fun SharedPreferences.putSync(key: String, value: Any): Boolean {
    return edit().apply {
        when (value) {
            is String -> putString(key, value)
            is Int -> putInt(key, value)
            is Long -> putLong(key, value)
            is Float -> putFloat(key, value)
            is Boolean -> putBoolean(key, value)
            else -> putString(key, Gson().toJson(value))
        }
    }.commit()
}

/**
 * 移除key值，同步方法，非必要请使用[remove]
 * @return 修改结果
 */
fun SharedPreferences.removeSync(key: String): Boolean {
    return edit().remove(key).commit()
}

/**
 * 清空key值，同步方法，非必要请使用[clear]
 * @return 修改结果
 */
fun SharedPreferences.clearSync(): Boolean {
    return edit().clear().commit()
}
