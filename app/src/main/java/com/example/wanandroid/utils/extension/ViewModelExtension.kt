package com.example.wanandroid.utils.extension

import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.modele_net.common.error.NetError
import com.example.modele_net.scope_v1.NetManager
import com.example.modele_net.scope_v1.Status
import com.example.wanandroid.app.WanApplication
import com.example.wanandroid.net.ResponseResult
import com.example.wanandroid.net.executeWACall
import com.example.wanandroid.net.executeWASuspend
import com.example.wanandroid.utils.datastore.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

/**
 * 启动协程
 * TODO 指定Dispatchers.DEFAULT会导致闪屏页闪退
 */
fun ViewModel.launch(
    action: suspend CoroutineScope.() -> Unit
) {
    viewModelScope.launch {
        action.invoke(this)
    }
}

/**
 * 启动IO协程
 */
fun ViewModel.launchByIo(action: suspend CoroutineScope.() -> Unit) {
    viewModelScope.launch(Dispatchers.IO) {
        action.invoke(this)
    }
}

/**
 * 调用接口
 */
fun <T : Any> ViewModel.executeCall(
    requestCall: (suspend () -> ResponseResult<T>),
    onSuccess: (T?) -> Unit,
    onFailed: ((NetError) -> Unit)? = null,
    onStatusChange: ((Status) -> Unit)? = null,
    clientKey: String = NetManager.CLIENT_KEY_DEFAULT
) {
    executeWACall(viewModelScope, requestCall, onSuccess, onFailed, onStatusChange, clientKey)
}

/**
 * 挂起方式调用接口
 */
suspend fun <T : Any> ViewModel.executeCallSuspend(
    requestCall: (suspend () -> ResponseResult<T>),
    clientKey: String = NetManager.CLIENT_KEY_DEFAULT
): ResponseResult<T> {
    return executeWASuspend(requestCall, clientKey)
}

/**
 * 保存对象
 */
fun <T> ViewModel.putObject(
    key: Preferences.Key<String>, value: T, callback: ((Boolean) -> Unit)? = null
) {
    launch {
        kotlin.runCatching {
            WanApplication.context.dataStore.putObject(key, value)
            callback?.invoke(true)
        }.onFailure {
            callback?.invoke(false)
        }
    }
}

/**
 * 保存对象，挂起方法
 */
suspend fun <T> ViewModel.putObjectSuspend(
    key: Preferences.Key<String>, value: T
) {
    WanApplication.context.dataStore.putObject(key, value)
}

/**
 * 保存值
 */
fun <T> ViewModel.putData(
    key: Preferences.Key<T>, value: T, callback: ((Boolean) -> Unit)? = null
) {
    launch {
        kotlin.runCatching {
            WanApplication.context.dataStore.put(key, value)
            callback?.invoke(true)
        }.onFailure {
            callback?.invoke(false)
        }
    }
}

/**
 * 保存值，挂起方法
 */
suspend fun <T> ViewModel.putDataSuspend(key: Preferences.Key<T>, value: T) {
    WanApplication.context.dataStore.put(key, value)
}

/**
 * 获取对象
 */
inline fun <reified T> ViewModel.getObject(key: Preferences.Key<String>): Flow<T?> {
    return WanApplication.context.dataStore.getObject<T>(key)
}

/**
 * 获取对应值
 */
fun <T> ViewModel.getData(key: Preferences.Key<T>, defaultValue: T? = null): Flow<T?> {
    return WanApplication.context.dataStore.get(key, defaultValue)
}

/**
 * 是否包含key值
 */
fun <T> ViewModel.hasData(key: Preferences.Key<T>): Flow<Boolean> {
    return WanApplication.context.dataStore.has(key)
}

/**
 * 移除指定Key
 */
fun <T> ViewModel.remove(key: Preferences.Key<T>, callback: ((Boolean) -> Unit)? = null) {
    launch {
        kotlin.runCatching {
            WanApplication.context.dataStore.remove(key)
            callback?.invoke(true)
        }.onFailure {
            callback?.invoke(false)
        }
    }
}

/**
 * 移除指定Key，挂起方法
 */
suspend fun <T> ViewModel.removeSuspend(key: Preferences.Key<T>) {
    WanApplication.context.dataStore.remove(key)
}

/**
 * 清空
 */
fun ViewModel.clear(callback: ((Boolean) -> Unit)? = null) {
    launch {
        kotlin.runCatching {
            WanApplication.context.dataStore.clear()
            callback?.invoke(true)
        }.onFailure {
            callback?.invoke(false)
        }

    }
}

/**
 * 清空，挂起方法
 */
suspend fun ViewModel.clear() {
    WanApplication.context.dataStore.clear()
}