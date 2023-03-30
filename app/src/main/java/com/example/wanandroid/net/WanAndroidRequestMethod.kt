package com.example.wanandroid.net

import com.example.modele_net.common.error.NetError
import com.example.modele_net.scope_v1.*
import kotlinx.coroutines.CoroutineScope

/**
 * WanAndroid网络接口调用的上层封装
 */
fun <T : Any> executeWACall(
    scope: CoroutineScope,
    requestCall: (suspend () -> ResponseResult<T>),
    onSuccess: (T?) -> Unit,
    onFailed: ((NetError) -> Unit)? = null,
    onStatusChange: ((Status) -> Unit)? = null,
    clientKey: String = NetManager.CLIENT_KEY_DEFAULT
) {
    callRequest(scope, requestCall, {
        onSuccess.invoke(it.data)
    }, onFailed, onStatusChange, clientKey)
}

/**
 * WanAndroid网络接口挂起方式调用的上层封装
 */
suspend fun <T : Any> executeWASuspend(
    requestCall: (suspend () -> ResponseResult<T>),
    clientKey: String = NetManager.CLIENT_KEY_DEFAULT
): ResponseResult<T> {
    val wrapper = callRequestSuspend(requestCall, clientKey)
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