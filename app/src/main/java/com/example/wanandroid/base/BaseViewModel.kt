package com.example.wanandroid.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.modele_net.common.error.NetError
import com.example.modele_net.scope_v1.ApiProvider
import com.example.modele_net.scope_v1.IResult
import com.example.modele_net.scope_v1.NetManager
import com.example.modele_net.scope_v1.Status
import com.example.wanandroid.base.mvi.ViewEvent
import com.example.wanandroid.base.mvi.ViewIntent
import com.example.wanandroid.base.mvi.ViewState
import com.example.wanandroid.net.ResponseResult
import com.example.wanandroid.net.WanAndroidApi
import com.example.wanandroid.net.executeWACall
import com.example.wanandroid.net.executeWASuspend
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/**
 * @author: Yang
 * @date: 2023/1/14
 * @description: WanAndroid的ViewModel基类
 */
abstract class BaseViewModel<VS : ViewState, VI : ViewIntent> : ViewModel() {

    //接口服务对象
    protected val apiService by lazy { ApiProvider.api(WanAndroidApi::class.java) }

    //通用UI操作
    private val _viewEvent = MutableSharedFlow<ViewEvent>()
    val viewEvent: SharedFlow<ViewEvent>
        get() = _viewEvent.asSharedFlow()

    //界面Intent
    val viewIntent = MutableSharedFlow<VI>()

    //界面状态
    protected val mViewState = MutableSharedFlow<VS>(1)
    val viewState: SharedFlow<VS>
        get() = mViewState.asSharedFlow()

    //界面状态数据
    val state: VS?
        get() = mViewState.replayCache.firstOrNull()

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
    fun emitViewEvent(viewEvent: ViewEvent) {
        viewModelScope.launch {
            _viewEvent.emit(viewEvent)
        }
    }

    /**
     * 更新ViewState
     */
    fun updateViewState(viewState: VS) {
        viewModelScope.launch {
            mViewState.emit(viewState)
        }
    }

    /**
     * 释放资源
     */
    open fun release() {

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
    protected fun <T : Any> executeCall(
        requestCall: (suspend () -> ResponseResult<T>),
        onSuccess: (T?) -> Unit,
        onFailed: ((NetError) -> Unit)? = null,
        onStatusChange: ((Status) -> Unit)? = null,
        clientKey: String = NetManager.CLIENT_KEY_DEFAULT
    ) {
        executeWACall(viewModelScope, requestCall, onSuccess, onFailed, onStatusChange, clientKey)
    }

    /**
     * 网络接口调用的上层封装
     */
    suspend fun <T : Any> executeCallSuspend(
        requestCall: (suspend () -> ResponseResult<T>),
        clientKey: String = NetManager.CLIENT_KEY_DEFAULT
    ): ResponseResult<T> {
        return executeWASuspend(requestCall, clientKey)
    }

}