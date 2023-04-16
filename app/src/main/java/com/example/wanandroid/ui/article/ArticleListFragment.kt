package com.example.wanandroid.ui.article

import com.alibaba.android.arouter.launcher.ARouter
import com.example.wanandroid.common.RoutePath
import com.example.wanandroid.model.Article
import com.example.wanandroid.ui.list.ListPageFragment
import com.example.wanandroid.ui.web.WebActivity
import com.example.wanandroid.mvi.article.ArticleListViewModel
import com.example.wanandroid.mvi.list.SimpleListViewState

/**
 * @author: Yang
 * @date: 2023/3/5
 * @description: 通用文章列表Fragment
 */
abstract class ArticleListFragment<VM : ArticleListViewModel> :
    ListPageFragment<SimpleListViewState<Article>, VM>() {

    override val adapter = ArticleListAdapter()

    override fun onLoadMore(viewState: SimpleListViewState<Article>) {
        adapter.addData(viewState.data ?: mutableListOf())
    }

    override fun onRefresh(viewState: SimpleListViewState<Article>) {
        adapter.setList(viewState.data ?: mutableListOf())
    }

    override fun onItemClick(position: Int) {
        val article = adapter.data.getOrNull(position) ?: return
        ARouter.getInstance().build(RoutePath.WEB)
            .withString(WebActivity.WEB_URL, article.link)
            .navigation()
    }

}