package com.example.wanandroid.viewmodel.search.home

import com.example.wanandroid.base.BaseViewModel
import com.example.wanandroid.utils.extension.executeCall

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
        executeCall({ apiService.getSearchHotKey() }, {
            updateViewState(SearchHomeViewState.InitSuccess(it ?: mutableListOf(), mutableListOf()))
        }, {
            updateViewState(SearchHomeViewState.InitFailed)
        })
    }


    /**
     * 清除历史记录
     */
    private fun clearHistory() {
        //TODO
    }

}