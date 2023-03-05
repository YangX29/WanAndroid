package com.example.wanandroid.ui.article

import com.example.wanandroid.ui.list.ListPageFragment
import com.example.wanandroid.viewmodel.article.ArticleListViewModel
import com.example.wanandroid.viewmodel.article.ArticleListViewState

/**
 * @author: Yang
 * @date: 2023/3/5
 * @description: 通用文章列表Fragment
 */
abstract class ArticleListFragment<VM : ArticleListViewModel> :
    ListPageFragment<ArticleListViewState, VM>() {

    override val adapter = ArticleListAdapter()

    override fun onLoadMore(viewState: ArticleListViewState) {
        adapter.addData(viewState.articles ?: mutableListOf())
    }

    override fun onRefresh(viewState: ArticleListViewState) {
        adapter.setList(viewState.articles ?: mutableListOf())
    }

}