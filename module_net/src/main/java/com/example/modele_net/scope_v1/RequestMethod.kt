package com.example.modele_net.scope_v1

import com.example.modele_net.common.error.NetError
import kotlinx.coroutines.CoroutineScope

/**
 * 发起网络请求，对[NetExecutor.execute]方法的调用，方便使用
 * @see NetExecutor.execute
 */
fun <T : IResult> callRequest(
    scope: CoroutineScope,
    requestCall: (suspend () -> T),
    onSuccess: (T) -> Unit,
    onFailed: ((NetError) -> Unit)? = null,
    onStatusChange: ((Status) -> Unit)? = null,
    clientKey: String = NetManager.CLIENT_KEY_DEFAULT
) {
    NetExecutor.execute(scope, requestCall, onSuccess, onFailed, onStatusChange, clientKey)
}

/**
 * 发起网络请求，挂起函数,对[NetExecutor.executeSuspend]方法的调用，方便使用
 * @see NetExecutor.executeSuspend
 */
suspend fun <T : IResult> executeSuspend(
    requestCall: (suspend () -> T),
    clientKey: String = NetManager.CLIENT_KEY_DEFAULT
): ResultWrapper<T> {
    return NetExecutor.executeSuspend(requestCall, clientKey)
}