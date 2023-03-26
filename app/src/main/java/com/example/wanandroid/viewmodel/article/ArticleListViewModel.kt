package com.example.wanandroid.viewmodel.article

import com.example.wanandroid.model.Article
import com.example.wanandroid.model.ListPage
import com.example.wanandroid.net.ResponseResult
import com.example.wanandroid.viewmodel.list.ListPageViewIntent
import com.example.wanandroid.viewmodel.list.ListPageViewModel
import com.example.wanandroid.viewmodel.list.ListPageViewStatus

/**
 * @author: Yang
 * @date: 2023/3/5
 * @description: 通用文章列表ViewModel
 */
abstract class ArticleListViewModel : ListPageViewModel<ArticleListViewState, ListPageViewIntent>() {

    //文章列表
    private val articleList = mutableListOf<Article>()

    override fun refresh(isInit: Boolean) {
        loadData(true)
    }

    override fun loadMore() {
        loadData(false)
    }

    /**
     * 加载数据
     */
    private fun loadData(isRefresh: Boolean) {
        executeCall({ getArticleList() }, {
            it?.apply {
                //更新分页
                updatePage(it)
                //更新界面
                val status = if (isRefresh) {
                    ListPageViewStatus.RefreshFinish
                } else {
                    ListPageViewStatus.LoadMoreFinish(page?.isFinish ?: false)
                }
                updateViewState(ArticleListViewState(status, it.list))
                //更新数据
                if (isRefresh) {
                    articleList.clear()
                }
                articleList.addAll(it.list)
            }
        }, {
            val status = if (isRefresh) {
                ListPageViewStatus.RefreshFailed
            } else {
                ListPageViewStatus.LoadMoreFailed
            }
            updateViewState(ArticleListViewState(status))
        })
    }

    /**
     * 获取文章列表API
     */
    abstract suspend fun getArticleList(): ResponseResult<ListPage<Article>>
}