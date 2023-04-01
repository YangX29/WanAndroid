package com.example.wanandroid.viewmodel.search

import com.example.wanandroid.base.BaseViewModel
import com.example.wanandroid.utils.datastore.StoreKey
import com.example.wanandroid.utils.extension.editData
import com.example.wanandroid.utils.extension.toJson
import com.example.wanandroid.utils.string.GsonUtils

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
        //保存搜索记录，判断是否已存在
        editData {
            if (it.contains(StoreKey.KEY_SEARCH_HISTORY)) {
                val json = it[StoreKey.KEY_SEARCH_HISTORY]
                val list = GsonUtils.fromJsonList<String>(json)
                //移除原有key
                if (list.contains(key)) {
                    list.remove(key)
                }
                //添加历史记录到第一个位置
                list.add(0, key)
                it[StoreKey.KEY_SEARCH_HISTORY] = list.toJson()
            } else {
                it[StoreKey.KEY_SEARCH_HISTORY] = mutableListOf(key).toJson()
            }
            updateViewState(SearchViewState.UpdateHistory(key))
        }
        //搜索
        if (isUpdate) {
            updateViewState(SearchViewState.RefreshSearchList(key))
        } else {
            updateViewState(SearchViewState.SearchKey(key))
        }
    }

}