package com.example.wanandroid.mvi.article

import com.example.wanandroid.model.Article
import com.example.wanandroid.model.ListPage
import com.example.wanandroid.mvi.list.SimpleListViewModel
import com.example.wanandroid.net.ResponseResult

/**
 * @author: Yang
 * @date: 2023/3/5
 * @description: 通用文章列表ViewModel
 */
abstract class ArticleListViewModel : SimpleListViewModel<Article>() {

    override suspend fun getList(): ResponseResult<ListPage<Article>> {
        return getArticleList()
    }

    /**
     * 获取文章列表API
     */
    abstract suspend fun getArticleList(): ResponseResult<ListPage<Article>>
}