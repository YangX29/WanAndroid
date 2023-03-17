package com.example.wanandroid.viewmodel.article

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * @author: Yang
 * @date: 2023/3/17
 * @description: 二级文章列表ViewModel
 */
class ArticleSubListViewModel(private val cid: Int) : ArticleListViewModel() {

    override suspend fun getArticleList() = apiService.getSubArticleList(page?.page ?: 0, cid)

    class Factory(val id: Int) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ArticleSubListViewModel(id) as T
        }
    }

}