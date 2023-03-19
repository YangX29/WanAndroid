package com.example.wanandroid.viewmodel.article

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * @author: Yang
 * @date: 2023/3/17
 * @description: 二级文章列表ViewModel
 */
class ArticleSubListViewModel(private val cid: Int, private val orderType: Int? = null) :
    ArticleListViewModel() {

    override suspend fun getArticleList() =
        apiService.getSubArticleList(page?.page ?: 0, cid, orderType)

    class Factory(val id: Int, val orderType: Int? = null) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ArticleSubListViewModel(id, orderType) as T
        }
    }

}