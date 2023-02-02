package com.example.wanandroid.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.modele_net.common.error.NetError
import com.example.modele_net.scope_v1.ApiProvider
import com.example.modele_net.scope_v1.NetManager
import com.example.modele_net.scope_v1.Status
import com.example.wanandroid.net.ResponseResult
import com.example.wanandroid.net.WanAndroidApi
import com.example.wanandroid.net.executeWACall

/**
 * @author: Yang
 * @date: 2023/1/14
 * @description: WanAndroid的ViewModel基类
 */
abstract class BaseViewModel : ViewModel() {

    //接口服务对象
    private val apiService by lazy { ApiProvider.api(WanAndroidApi::class.java) }

    /**
     * 网络接口调用的上层封装
     */
    fun <T : Any> executeCall(
        requestCall: (suspend () -> ResponseResult<T>),
        onSuccess: (T?) -> Unit,
        onFailed: ((NetError) -> Unit)? = null,
        onStatusChange:((Status)->Unit)? = null,
        clientKey: String = NetManager.CLIENT_KEY_DEFAULT
    ) {
        executeWACall(viewModelScope, requestCall, onSuccess, onFailed, onStatusChange, clientKey)
    }

}