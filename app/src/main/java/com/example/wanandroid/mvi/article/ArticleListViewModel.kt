package com.example.wanandroid.mvi.article

import com.example.wanandroid.model.Article
import com.example.wanandroid.model.ListPage
import com.example.wanandroid.mvi.list.SimpleListViewModel
import com.example.wanandroid.net.ResponseResult
import com.example.wanandroid.utils.extension.launch

/**
 * @author: Yang
 * @date: 2023/3/5
 * @description: 通用文章列表ViewModel
 */
abstract class ArticleListViewModel : SimpleListViewModel<Article>() {

    private val repository by lazy { ArticleRepository() }

    override suspend fun getList(): ResponseResult<ListPage<Article>> {
        return getArticleList()
    }

    /**
     * 获取文章列表API
     */
    abstract suspend fun getArticleList(): ResponseResult<ListPage<Article>>

    /**
     * 收藏/取消收藏Article
     */
    fun collectArticle(collect: Boolean, id: Long) {
        launch {
            if (collect) {
                repository.collectArticle(id)
            } else {
                repository.uncollectArticle(id)
            }
        }
    }

    /**
     * 取消我的收藏
     */
    fun uncollectMine(id: Long, originId: Long) {
        launch { repository.uncollectMine(id, originId) }
    }

}