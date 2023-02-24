package com.example.wanandroid.viewmodel.home.sub

import com.example.module_common.utils.log.logE
import com.example.wanandroid.base.BaseViewModel
import com.example.wanandroid.utils.extension.launch

/**
 * @author: Yang
 * @date: 2023/2/23
 * @description:
 */
class HomeSubViewModel : BaseViewModel<HomeSubViewState, HomeSubViewIntent>() {

    override fun handleIntent(viewIntent: HomeSubViewIntent) {
        when (viewIntent) {
            is HomeSubViewIntent.Refresh -> {
                //调用接口获取数据
                refresh()
            }
        }
    }

    /**
     * 刷新
     */
    private fun refresh() {
        launch {
            val result = executeCallSuspend(apiService::getBanner)
            logE("test_bug", "list1:${result.data?.size}")
            updateViewState(HomeSubViewState.RefreshFinish)
        }

    }

    /**
     * 加载更多
     */
    private fun loadMore() {

    }

}