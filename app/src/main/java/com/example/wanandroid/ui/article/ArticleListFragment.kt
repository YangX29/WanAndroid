package com.example.wanandroid.ui.article

import com.alibaba.android.arouter.launcher.ARouter
import com.example.wanandroid.common.RoutePath
import com.example.wanandroid.ui.list.ListPageFragment
import com.example.wanandroid.ui.web.WebActivity
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

    override fun onItemClick(position: Int) {
        val article = adapter.data.getOrNull(position) ?: return
        ARouter.getInstance().build(RoutePath.WEB)
            .withString(WebActivity.WEB_URL, article.link)
            .navigation()
    }

}