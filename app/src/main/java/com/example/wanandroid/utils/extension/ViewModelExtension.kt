package com.example.wanandroid.utils.extension

import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.modele_net.common.error.NetError
import com.example.modele_net.scope_v1.NetManager
import com.example.modele_net.scope_v1.Status
import com.example.wanandroid.net.ResponseResult
import com.example.wanandroid.net.executeWACall
import com.example.wanandroid.net.executeWASuspend
import com.example.wanandroid.utils.datastore.DataStoreUtils
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
    showLoading: Boolean = false,
    onStatusChange: ((Status) -> Unit)? = null,
    clientKey: String = NetManager.CLIENT_KEY_DEFAULT
) {
    executeWACall(
        viewModelScope,
        requestCall,
        onSuccess,
        onFailed,
        showLoading,
        onStatusChange,
        clientKey
    )
}

/**
 * 挂起方式调用接口
 */
suspend fun <T : Any> ViewModel.executeCallSuspend(
    requestCall: (suspend () -> ResponseResult<T>),
    showLoading: Boolean = false,
    clientKey: String = NetManager.CLIENT_KEY_DEFAULT
): ResponseResult<T> {
    return executeWASuspend(requestCall, showLoading, clientKey)
}

/**
 * 编辑数据
 */
fun ViewModel.editData(edit: (MutablePreferences) -> Unit) = DataStoreUtils.editData(edit)

/**
 * 保存对象
 */
fun <T> ViewModel.putObject(
    key: Preferences.Key<String>, value: T, callback: ((Boolean) -> Unit)? = null
) = DataStoreUtils.putObject(key, value, callback)

/**
 * 保存对象，挂起方法
 */
suspend fun <T> ViewModel.putObjectSuspend(
    key: Preferences.Key<String>, value: T
) = DataStoreUtils.putObjectSuspend(key, value)

/**
 * 保存值
 */
fun <T> ViewModel.putData(
    key: Preferences.Key<T>, value: T, callback: ((Boolean) -> Unit)? = null
) = DataStoreUtils.putData(key, value, callback)

/**
 * 保存值，挂起方法
 */
suspend fun <T> ViewModel.putDataSuspend(key: Preferences.Key<T>, value: T) =
    DataStoreUtils.putDataSuspend(key, value)

/**
 * 获取对象
 */
inline fun <reified T> ViewModel.getObject(key: Preferences.Key<String>): Flow<T?> =
    DataStoreUtils.getObject(key)

/**
 * 获取对应值
 */
fun <T> ViewModel.getData(key: Preferences.Key<T>, defaultValue: T? = null): Flow<T?> =
    DataStoreUtils.getData(key, defaultValue)

/**
 * 是否包含key值
 */
fun <T> ViewModel.hasData(key: Preferences.Key<T>): Flow<Boolean> = DataStoreUtils.hasData(key)

/**
 * 移除指定Key
 */
fun <T> ViewModel.removeData(key: Preferences.Key<T>, callback: ((Boolean) -> Unit)? = null) =
    DataStoreUtils.removeData(key, callback)

/**
 * 移除指定Key，挂起方法
 */
suspend fun <T> ViewModel.removeDataSuspend(key: Preferences.Key<T>) =
    DataStoreUtils.removeDataSuspend(key)

/**
 * 清空
 */
fun ViewModel.clearData(callback: ((Boolean) -> Unit)? = null) = DataStoreUtils.clearData(callback)

/**
 * 清空，挂起方法
 */
suspend fun ViewModel.clearData() = DataStoreUtils.clearData()