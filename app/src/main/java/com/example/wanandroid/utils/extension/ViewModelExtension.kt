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

//TODO 指定Dispatchers.DEFAULT会导致闪屏页闪退
fun ViewModel.launch(
    action: suspend CoroutineScope.() -> Unit
) {
    viewModelScope.launch {
        action.invoke(this)
    }
}

fun ViewModel.launchByIo(action: suspend CoroutineScope.() -> Unit) {
    viewModelScope.launch(Dispatchers.IO) {
        action.invoke(this)
    }
}


fun <T : Any> ViewModel.executeCall(
    requestCall: (suspend () -> ResponseResult<T>),
    onSuccess: (T?) -> Unit,
    onFailed: ((NetError) -> Unit)? = null,
    onStatusChange: ((Status) -> Unit)? = null,
    clientKey: String = NetManager.CLIENT_KEY_DEFAULT
) {
    executeWACall(viewModelScope, requestCall, onSuccess, onFailed, onStatusChange, clientKey)
}

suspend fun <T : Any> ViewModel.executeCallSuspend(
    requestCall: (suspend () -> ResponseResult<T>),
    clientKey: String = NetManager.CLIENT_KEY_DEFAULT
): ResponseResult<T> {
    return executeWASuspend(requestCall, clientKey)
}