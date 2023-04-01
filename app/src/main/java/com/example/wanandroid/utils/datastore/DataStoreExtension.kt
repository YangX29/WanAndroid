package com.example.wanandroid.utils.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.example.wanandroid.utils.extension.fromJson
import com.example.wanandroid.utils.extension.toJson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * dataStore单例
 */
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

/**
 * 保存对象
 */
suspend fun <T> DataStore<Preferences>.putObject(key: Preferences.Key<String>, value: T) {
    edit {
        it[key] = value.toJson()
    }
}

/**
 * 保存值
 */
suspend fun <T> DataStore<Preferences>.put(key: Preferences.Key<T>, value: T) {
    edit {
        it[key] = value
    }
}

/**
 * 获取对应值
 */
fun <T> DataStore<Preferences>.get(key: Preferences.Key<T>, defaultValue: T? = null): Flow<T?> {
    return data.map { it[key] ?: defaultValue }
}

/**
 * 获取对象
 */
inline fun <reified T> DataStore<Preferences>.getObject(key: Preferences.Key<String>): Flow<T?> {
    return data.map { it[key].fromJson(T::class.java) }
}

/**
 * 是否包含key值
 */
fun <T> DataStore<Preferences>.has(key: Preferences.Key<T>): Flow<Boolean> {
    return data.map { it.contains(key) }
}

/**
 * 移除指定Key
 */
suspend fun <T> DataStore<Preferences>.remove(key: Preferences.Key<T>) {
    edit {
        it.remove(key)
    }
}

/**
 * 清空
 */
suspend fun DataStore<Preferences>.clear() {
    edit {
        it.clear()
    }
}