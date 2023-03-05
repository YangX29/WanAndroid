package com.example.wanandroid.viewmodel.official

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.wanandroid.model.Article
import com.example.wanandroid.model.ListPage
import com.example.wanandroid.net.ResponseResult
import com.example.wanandroid.viewmodel.article.ArticleListViewModel

/**
 * @author: Yang
 * @date: 2023/3/5
 * @description: 公众号列表ViewModel
 */
class OfficialListViewModel(val id: Int) : ArticleListViewModel() {

    override suspend fun getArticleList(): ResponseResult<ListPage<Article>> {
        return apiService.getOfficialArticleList(id, page?.page ?: 0)
    }

    class Factory(val id: Int): ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return OfficialListViewModel(id) as T
        }
    }

}