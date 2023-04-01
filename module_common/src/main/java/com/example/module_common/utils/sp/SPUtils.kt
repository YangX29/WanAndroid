package com.example.module_common.utils.sp

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson

/**
 * @author: Yang
 * @date: 2023/4/1
 * @description: SP工具类
 */
object SPUtils {

    //sp名
    private const val SP_NAME = "sp_common"

    //单例
    lateinit var instance: SharedPreferences

    /**
     * 初始化
     */
    fun init(context: Context) {
        instance = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
    }

    /**
     * 保存数据
     */
    fun put(key: String, value: Any) {
        with(instance.edit()) {
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
     * 获取数据
     */
    inline fun <reified T> get(key: String, defaultValue: T? = null): T? {
        with(instance) {
            return when (T::class.java) {
                String::class.java -> getString(key, defaultValue as? String) as T
                Int::class.java -> getInt(key, defaultValue as? Int ?: 0) as T
                Long::class.java -> getLong(key, defaultValue as? Long ?: 0L) as T
                Float::class.java -> getFloat(key, defaultValue as? Float ?: 0f) as T
                Boolean::class.java -> getBoolean(key, defaultValue as? Boolean ?: false) as T
                else -> Gson().fromJson(getString(key, null), T::class.java)
            }
        }
    }

    /**
     * 是否包含key
     */
    fun has(key: String): Boolean {
        return instance.contains(key)
    }

    /**
     * 移除key值
     */
    fun remove(key: String) {
        instance.edit().remove(key).apply()
    }

    /**
     * 清除所有key值
     */
    fun clear() {
        instance.edit().clear().apply()
    }

    /**
     * 获取SharedPreferences实例
     */
    fun getSpInstance(context: Context, name: String): SharedPreferences {
        return context.getSharedPreferences(name, Context.MODE_PRIVATE)
    }

}