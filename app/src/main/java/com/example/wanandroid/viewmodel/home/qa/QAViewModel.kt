package com.example.wanandroid.viewmodel.home.qa

import com.example.wanandroid.model.Article
import com.example.wanandroid.model.ListPage
import com.example.wanandroid.net.ResponseResult
import com.example.wanandroid.viewmodel.article.ArticleListViewModel

/**
 * @author: Yang
 * @date: 2023/3/5
 * @description:
 */
class QAViewModel : ArticleListViewModel() {
    override suspend fun getArticleList(): ResponseResult<ListPage<Article>> {
        return apiService.getQAList(page?.page ?: 0)
    }

}