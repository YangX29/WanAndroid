package com.example.wanandroid.viewmodel.home

import com.example.wanandroid.base.BaseViewModel
import com.example.wanandroid.utils.extension.launch
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

/**
 * @author: Yang
 * @date: 2023/2/23
 * @description: 首页ViewModel
 */
class HomeViewModel : BaseViewModel<HomeViewState, HomeViewIntent>() {

    //子Fragment的界面状态
    private val _tabState = MutableSharedFlow<TabViewState>(1)
    val tabState: SharedFlow<TabViewState>
        get() = _tabState.asSharedFlow()

    override fun handleIntent(viewIntent: HomeViewIntent) {
        when (viewIntent) {
            is HomeViewIntent.Refresh -> {
                //刷新子页面
                updateTabState(TabViewState.StartRefresh(viewIntent.tab))
            }
            is HomeViewIntent.RefreshFinish -> {
                //刷新结束,通知首页关闭loading
                updateViewState(HomeViewState.RefreshFinish)
            }
        }
    }

    private fun updateTabState(state: TabViewState) {
        launch { _tabState.emit(state) }
    }

}