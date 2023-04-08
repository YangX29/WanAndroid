package com.example.wanandroid.utils.datastore

import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.example.wanandroid.app.WanApplication
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull

/**
 * @author: Yang
 * @date: 2023/4/2
 * @description: DataStore工具类
 */
object DataStoreUtils {

    //dataStore实例
    val dataStore by lazy { WanApplication.context.dataStore }

    //协程实例
    private val scope: CoroutineScope by lazy { CoroutineScope(SupervisorJob() + Dispatchers.IO) }

    /**
     * 编辑数据
     */
    fun editData(edit: (MutablePreferences) -> Unit) {
        scope.launch {
            dataStore.edit {
                edit.invoke(it)
            }
        }
    }

    /**
     * 保存对象
     */
    fun <T> putObject(
        key: Preferences.Key<String>, value: T, callback: ((Boolean) -> Unit)? = null
    ) {
        scope.launch {
            kotlin.runCatching {
                dataStore.putObject(key, value)
                callback?.invoke(true)
            }.onFailure {
                callback?.invoke(false)
            }
        }
    }

    /**
     * 保存对象，挂起方法
     */
    suspend fun <T> putObjectSuspend(
        key: Preferences.Key<String>, value: T
    ) {
        dataStore.putObject(key, value)
    }

    /**
     * 保存值
     */
    fun <T> putData(
        key: Preferences.Key<T>, value: T, callback: ((Boolean) -> Unit)? = null
    ) {
        scope.launch {
            kotlin.runCatching {
                dataStore.put(key, value)
                callback?.invoke(true)
            }.onFailure {
                callback?.invoke(false)
            }
        }
    }

    /**
     * 保存值，挂起方法
     */
    suspend fun <T> putDataSuspend(key: Preferences.Key<T>, value: T) {
        dataStore.put(key, value)
    }

    /**
     * 同步获取对象，耗时方法
     */
    inline fun <reified T> getObjectSync(key: Preferences.Key<String>): T? {
        return runBlocking { dataStore.getObject<T>(key).firstOrNull() }
    }

    /**
     * 同步获取对应值，耗时方法
     */
    fun <T> getDataSync(key: Preferences.Key<T>, defaultValue: T? = null): T? {
        return runBlocking { dataStore.get(key, defaultValue).firstOrNull() }
    }

    /**
     * 同步获取是否包含key值，耗时方法
     */
    fun <T> hasDataSync(key: Preferences.Key<T>): Boolean {
        return runBlocking { dataStore.has(key).first() }
    }

    /**
     * 获取对象
     */
    inline fun <reified T> getObject(key: Preferences.Key<String>): Flow<T?> {
        return dataStore.getObject<T>(key)
    }

    /**
     * 获取对应值
     */
    fun <T> getData(key: Preferences.Key<T>, defaultValue: T? = null): Flow<T?> {
        return dataStore.get(key, defaultValue)
    }

    /**
     * 是否包含key值
     */
    fun <T> hasData(key: Preferences.Key<T>): Flow<Boolean> {
        return dataStore.has(key)
    }

    /**
     * 移除指定Key
     */
    fun <T> removeData(key: Preferences.Key<T>, callback: ((Boolean) -> Unit)? = null) {
        scope.launch {
            kotlin.runCatching {
                dataStore.remove(key)
                callback?.invoke(true)
            }.onFailure {
                callback?.invoke(false)
            }
        }
    }

    /**
     * 移除指定Key，挂起方法
     */
    suspend fun <T> removeDataSuspend(key: Preferences.Key<T>) {
        dataStore.remove(key)
    }

    /**
     * 清空
     */
    fun clearData(callback: ((Boolean) -> Unit)? = null) {
        scope.launch {
            kotlin.runCatching {
                dataStore.clear()
                callback?.invoke(true)
            }.onFailure {
                callback?.invoke(false)
            }

        }
    }

    /**
     * 清空，挂起方法
     */
    suspend fun clearData() {
        dataStore.clear()
    }

}