package com.example.modele_net.scope_v1

import androidx.lifecycle.MutableLiveData
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
    loadState: MutableLiveData<Status>? = null,
    clientKey: String = NetManager.CLIENT_KEY_DEFAULT
) {
    NetExecutor.execute(scope, requestCall, onSuccess, onFailed, loadState, clientKey)
}