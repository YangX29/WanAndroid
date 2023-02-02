package com.example.wanandroid.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.modele_net.common.error.NetError
import com.example.modele_net.scope_v1.ApiProvider
import com.example.modele_net.scope_v1.NetManager
import com.example.modele_net.scope_v1.Status
import com.example.wanandroid.base.mvi.ViewEffect
import com.example.wanandroid.base.mvi.ViewIntent
import com.example.wanandroid.base.mvi.ViewState
import com.example.wanandroid.net.ResponseResult
import com.example.wanandroid.net.WanAndroidApi
import com.example.wanandroid.net.executeWACall
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

/**
 * @author: Yang
 * @date: 2023/1/14
 * @description: WanAndroid的ViewModel基类
 */
abstract class BaseViewModel<VS: ViewState, VI: ViewIntent> : ViewModel() {

    //接口服务对象
    private val apiService by lazy { ApiProvider.api(WanAndroidApi::class.java) }

    //通用UI操作
    private val _viewEffect = MutableSharedFlow<ViewEffect>()
    val viewEffect = _viewEffect.asSharedFlow()
    //界面Intent
    private val _viewIntent = MutableSharedFlow<VI>()
    val viewIntent = _viewIntent
    //界面状态
    private val _viewState = MutableSharedFlow<VS>()
    val viewState = _viewState.asSharedFlow()

    init {
        //处理界面行为
        handleViewIntent()
    }

    /**
     * 处理界面行为
     */
    open fun handleIntent(viewIntent: VI) {

    }

    /**
     * 提交通用界面操作
     */
    fun emitViewEffect(viewEffect: ViewEffect) {
        viewModelScope.launch {
            _viewEffect.emit(viewEffect)
        }
    }

    /**
     * 更新ViewState
     */
    fun updateViewState(viewState: VS) {
        viewModelScope.launch {
            _viewState.emit(viewState)
        }
    }

    /**
     * 处理界面行为
     */
    private fun handleViewIntent() {
        viewModelScope.launch {
            viewIntent.distinctUntilChanged().collect {
                handleIntent(it)
            }
        }
    }

    /**
     * 网络接口调用的上层封装
     */
    private fun <T : Any> executeCall(
        requestCall: (suspend () -> ResponseResult<T>),
        onSuccess: (T?) -> Unit,
        onFailed: ((NetError) -> Unit)? = null,
        onStatusChange: ((Status) -> Unit)? = null,
        clientKey: String = NetManager.CLIENT_KEY_DEFAULT
    ) {
        executeWACall(viewModelScope, requestCall, onSuccess, onFailed, onStatusChange, clientKey)
    }

}