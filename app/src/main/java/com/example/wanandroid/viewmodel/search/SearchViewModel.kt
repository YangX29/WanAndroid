package com.example.wanandroid.viewmodel.search

import com.example.wanandroid.base.BaseViewModel

/**
 * @author: Yang
 * @date: 2023/3/23
 * @description: 搜索页ViewModel
 */
class SearchViewModel : BaseViewModel<SearchViewState, SearchViewIntent>() {

    override fun handleIntent(viewIntent: SearchViewIntent) {
        when (viewIntent) {
            is SearchViewIntent.Search -> {
                search(viewIntent.key, false)
            }
            is SearchViewIntent.UpdateSearchKey -> {
                search(viewIntent.key, true)
            }
        }
    }

    /**
     * 搜索
     */
    private fun search(key: String, isUpdate: Boolean) {
        //TODO 保存搜索记录，判断是否已存在
        updateViewState(SearchViewState.UpdateHistory(key))
        //搜索
        if (isUpdate) {
            updateViewState(SearchViewState.RefreshSearchList(key))
        } else {
            updateViewState(SearchViewState.SearchKey(key))
        }
    }

}