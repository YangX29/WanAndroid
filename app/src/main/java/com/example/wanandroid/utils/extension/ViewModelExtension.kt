package com.example.wanandroid.utils.extension

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.modele_net.common.error.NetError
import com.example.modele_net.scope_v1.NetManager
import com.example.modele_net.scope_v1.Status
import com.example.wanandroid.net.ResponseResult
import com.example.wanandroid.net.executeWACall
import com.example.wanandroid.net.executeWASuspend
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
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