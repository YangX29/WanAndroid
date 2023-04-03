package com.example.wanandroid.mvi.search.home

import com.example.wanandroid.base.BaseViewModel
import com.example.wanandroid.utils.datastore.StoreKey
import com.example.wanandroid.utils.extension.*
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.firstOrNull

/**
 * @author: Yang
 * @date: 2023/3/22
 * @description: 搜索页ViewModel
 */
class SearchHomeViewModel : BaseViewModel<SearchHomeViewState, SearchHomeViewIntent>() {

    override fun handleIntent(viewIntent: SearchHomeViewIntent) {
        when (viewIntent) {
            is SearchHomeViewIntent.InitData -> {
                initData()
            }
            is SearchHomeViewIntent.ClearHistory -> {
                clearHistory()
            }
        }
    }

    /**
     * 初始化数据
     */
    private fun initData() {
        launch {
            val hotKeyTask = async { executeCallSuspend({ apiService.getSearchHotKey() }) }
            val historyTask = async { getData(StoreKey.KEY_SEARCH_HISTORY).firstOrNull() }
            val hotKey = hotKeyTask.await()
            val history = historyTask.await()
            if (hotKey.data == null) {
                updateViewState(SearchHomeViewState.InitFailed)
            } else {
                updateViewState(
                    SearchHomeViewState.InitSuccess(
                        hotKey.data ?: mutableListOf(),
                        history.fromJsonList<String>().toMutableList()
                    )
                )
            }
        }
    }


    /**
     * 清除历史记录
     */
    private fun clearHistory() {
        //移除搜索历史记录
        removeData(StoreKey.KEY_SEARCH_HISTORY) {
            if (it) updateViewState(SearchHomeViewState.ClearSuccess)
        }
    }

}