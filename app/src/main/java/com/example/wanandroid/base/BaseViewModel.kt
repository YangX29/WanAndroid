package com.example.wanandroid.base

import androidx.lifecycle.ViewModel
import com.example.modele_net.scope_v1.ApiProvider
import com.example.wanandroid.base.mvi.ViewEvent
import com.example.wanandroid.base.mvi.ViewIntent
import com.example.wanandroid.base.mvi.ViewState
import com.example.wanandroid.net.WanAndroidApi
import com.example.wanandroid.utils.extension.launch
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

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
    protected val mViewState = MutableSharedFlow<VS>()
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
        launch {
            _viewEvent.emit(viewEvent)
        }
    }

    /**
     * 更新ViewState
     */
    fun updateViewState(viewState: VS) {
        launch {
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
        launch {
            viewIntent.collect {
                handleIntent(it)
            }
        }
    }

}