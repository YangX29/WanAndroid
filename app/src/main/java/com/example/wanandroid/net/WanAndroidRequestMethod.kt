package com.example.wanandroid.net

import androidx.lifecycle.MutableLiveData
import com.example.modele_net.common.error.NetError
import com.example.modele_net.scope_v1.NetManager
import com.example.modele_net.scope_v1.Status
import com.example.modele_net.scope_v1.callRequest
import kotlinx.coroutines.CoroutineScope

/**
 * WanAndroid网络接口调用的上层封装
 */
fun <T : Any> executeWACall(
    scope: CoroutineScope,
    requestCall: (suspend () -> ResponseResult<T>),
    onSuccess: (T?) -> Unit,
    onFailed: ((NetError) -> Unit)? = null,
    onStatusChange:((Status)->Unit)? = null,
    clientKey: String = NetManager.CLIENT_KEY_DEFAULT
) {
    callRequest(scope, requestCall, {
        onSuccess.invoke(it.data)
    }, onFailed, onStatusChange, clientKey)
}