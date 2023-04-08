package com.example.wanandroid.utils.datastore

import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.flow.Flow

/**
 * 编辑数据
 */
fun editData(edit: (MutablePreferences) -> Unit) = DataStoreUtils.editData(edit)

/**
 * 保存对象
 */
fun <T> putObject(
    key: Preferences.Key<String>, value: T, callback: ((Boolean) -> Unit)? = null
) = DataStoreUtils.putObject(key, value, callback)

/**
 * 保存对象，挂起方法
 */
suspend fun <T> putObjectSuspend(
    key: Preferences.Key<String>, value: T
) = DataStoreUtils.putObjectSuspend(key, value)

/**
 * 保存值
 */
fun <T> putData(
    key: Preferences.Key<T>, value: T, callback: ((Boolean) -> Unit)? = null
) = DataStoreUtils.putData(key, value, callback)

/**
 * 保存值，挂起方法
 */
suspend fun <T> putDataSuspend(key: Preferences.Key<T>, value: T) =
    DataStoreUtils.putDataSuspend(key, value)

/**
 * 获取对象
 */
inline fun <reified T> getObject(key: Preferences.Key<String>): Flow<T?> =
    DataStoreUtils.getObject(key)

/**
 * 获取对应值
 */
fun <T> getData(key: Preferences.Key<T>, defaultValue: T? = null): Flow<T?> =
    DataStoreUtils.getData(key, defaultValue)

/**
 * 是否包含key值
 */
fun <T> hasData(key: Preferences.Key<T>): Flow<Boolean> = DataStoreUtils.hasData(key)

/**
 * 移除指定Key
 */
fun <T> removeData(key: Preferences.Key<T>, callback: ((Boolean) -> Unit)? = null) =
    DataStoreUtils.removeData(key, callback)

/**
 * 移除指定Key，挂起方法
 */
suspend fun <T> removeDataSuspend(key: Preferences.Key<T>) =
    DataStoreUtils.removeDataSuspend(key)

/**
 * 清空
 */
fun clearData(callback: ((Boolean) -> Unit)? = null) = DataStoreUtils.clearData(callback)

/**
 * 清空，挂起方法
 */
suspend fun clearData() = DataStoreUtils.clearData()