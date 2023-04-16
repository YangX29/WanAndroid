package com.example.wanandroid.ui.article

import com.alibaba.android.arouter.launcher.ARouter
import com.example.wanandroid.common.RoutePath
import com.example.wanandroid.model.Article
import com.example.wanandroid.mvi.article.ArticleListViewModel
import com.example.wanandroid.mvi.list.SimpleListViewState
import com.example.wanandroid.ui.list.ListPageActivity
import com.example.wanandroid.ui.web.WebActivity

/**
 * @author: Yang
 * @date: 2023/4/15
 * @description: 通用文章列表Activity
 */
abstract class ArticleListActivity<VM : ArticleListViewModel> :
    ListPageActivity<SimpleListViewState<Article>, VM>() {

    override val adapter = ArticleListAdapter()

    override fun onLoadMore(viewState: SimpleListViewState<Article>) {
        adapter.addData(viewState.data ?: mutableListOf())
    }

    override fun onRefresh(viewState: SimpleListViewState<Article>) {
        adapter.setNewInstance(viewState.data ?: mutableListOf())
    }

    override fun onItemClick(position: Int) {
        val article = adapter.data.getOrNull(position) ?: return
        ARouter.getInstance().build(RoutePath.WEB)
            .withString(WebActivity.WEB_URL, article.link)
            .navigation()
    }
}