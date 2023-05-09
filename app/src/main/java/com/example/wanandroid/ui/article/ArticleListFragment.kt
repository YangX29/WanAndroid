package com.example.wanandroid.ui.article

import com.alibaba.android.arouter.launcher.ARouter
import com.example.wanandroid.common.RoutePath
import com.example.wanandroid.model.Article
import com.example.wanandroid.mvi.article.ArticleListViewModel
import com.example.wanandroid.ui.list.SimpleListFragment
import com.example.wanandroid.ui.web.WebActivity

/**
 * @author: Yang
 * @date: 2023/3/5
 * @description: 通用文章列表Fragment
 */
abstract class ArticleListFragment<VM : ArticleListViewModel> :
    SimpleListFragment<Article, VM>() {

    override val adapter = ArticleListAdapter()

    override fun onItemClick(position: Int) {
        val article = adapter.data.getOrNull(position) ?: return
        ARouter.getInstance().build(RoutePath.WEB)
            .withString(WebActivity.WEB_URL, article.link)
            .navigation()
    }

}