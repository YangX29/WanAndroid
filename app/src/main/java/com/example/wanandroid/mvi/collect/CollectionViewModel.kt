package com.example.wanandroid.mvi.collect

import com.example.wanandroid.model.Article
import com.example.wanandroid.model.ListPage
import com.example.wanandroid.mvi.article.ArticleListViewModel
import com.example.wanandroid.net.ResponseResult

/**
 * @author: Yang
 * @date: 2023/4/15
 * @description: 我的收藏页面ViewModel
 */
class CollectionViewModel : ArticleListViewModel() {

    override suspend fun getArticleList(): ResponseResult<ListPage<Article>> {
        val result = apiService.collectionList(page?.page ?: 0)
        //收藏列表数据处理，收藏设置为true
        result.data?.list?.onEach { it.collect = true }
        return result
    }
}