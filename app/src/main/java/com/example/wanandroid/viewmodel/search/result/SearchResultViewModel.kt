package com.example.wanandroid.viewmodel.search.result

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.wanandroid.viewmodel.article.ArticleListViewModel

/**
 * @author: Yang
 * @date: 2023/3/23
 * @description: 搜索结果ViewModel
 */
class SearchResultViewModel(private var key: String) : ArticleListViewModel() {

    override suspend fun getArticleList() = apiService.search(page?.page ?: 0, key)

    class Factory(val key: String) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return SearchResultViewModel(key) as T
        }
    }

    /**
     * 修改key,搜索
     */
    fun searchWithKey(key: String) {
        if (this.key == key) return
        this.key = key
        refresh(true)
    }

}