package com.example.wanandroid.viewmodel.project

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.wanandroid.model.Article
import com.example.wanandroid.model.Category
import com.example.wanandroid.model.ListPage
import com.example.wanandroid.net.ResponseResult
import com.example.wanandroid.viewmodel.article.ArticleListViewModel

/**
 * @author: Yang
 * @date: 2023/3/7
 * @description: 项目列表ViewModel
 */
class ProjectListViewModel(private val cid: Int) : ArticleListViewModel() {

    override suspend fun getArticleList(): ResponseResult<ListPage<Article>> {
        return if (cid == Category.TAB_ID_NEWEST_PROJECT) {//最新项目
            apiService.getNewestProjectList(page?.page ?: 0)
        } else {
            apiService.getProjectArticleList(page?.page ?: 0, cid)
        }
    }

    class Factory(val id: Int) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ProjectListViewModel(id) as T
        }
    }

}