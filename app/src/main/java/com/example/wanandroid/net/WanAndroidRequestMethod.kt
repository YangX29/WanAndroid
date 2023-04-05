package com.example.wanandroid.net

import com.example.modele_net.common.error.NetError
import com.example.modele_net.scope_v1.*
import com.example.wanandroid.utils.view.LoadingManager
import kotlinx.coroutines.CoroutineScope

/**
 * WanAndroid网络接口调用的上层封装
 */
fun <T : Any> executeWACall(
    scope: CoroutineScope,
    requestCall: (suspend () -> ResponseResult<T>),
    onSuccess: (T?) -> Unit,
    onFailed: ((NetError) -> Unit)? = null,
    showLoading: Boolean = false,
    onStatusChange: ((Status) -> Unit)? = null,
    clientKey: String = NetManager.CLIENT_KEY_DEFAULT
) {
    callRequest(scope, requestCall, {
        onSuccess.invoke(it.data)
    }, onFailed, {
        onStatusChange?.invoke(it)
        //处理loading
        if (showLoading) {
            if (it == Status.LOADING) {
                LoadingManager.showCurrentLoading()
            } else {
                LoadingManager.dismissCurrentLoading()
            }
        }
    }, clientKey)
}

/**
 * WanAndroid网络接口挂起方式调用的上层封装
 */
suspend fun <T : Any> executeWASuspend(
    requestCall: (suspend () -> ResponseResult<T>),
    showLoading: Boolean = false,
    clientKey: String = NetManager.CLIENT_KEY_DEFAULT
): ResponseResult<T> {
    //显示loading
    if (showLoading) {
        LoadingManager.showCurrentLoading()
    }
    val wrapper = callRequestSuspend(requestCall, clientKey)
    //隐藏loading
    if (showLoading) {
        LoadingManager.dismissCurrentLoading()
    }
    return if (wrapper.result != null) {
        wrapper.result!!
    } else {
        ResponseResult(
            wrapper.netError?.code,
            wrapper.netError?.message,
            null
        )
    }

}