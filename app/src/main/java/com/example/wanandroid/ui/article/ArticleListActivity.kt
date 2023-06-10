package com.example.wanandroid.ui.article

import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.launcher.ARouter
import com.example.wanandroid.R
import com.example.wanandroid.common.LiveEventKey
import com.example.wanandroid.common.RoutePath
import com.example.wanandroid.model.Article
import com.example.wanandroid.mvi.article.ArticleListViewModel
import com.example.wanandroid.ui.list.SimpleListActivity
import com.example.wanandroid.ui.web.WebActivity
import com.jeremyliao.liveeventbus.LiveEventBus

/**
 * @author: Yang
 * @date: 2023/4/15
 * @description: 通用文章列表Activity
 */
abstract class ArticleListActivity<VM : ArticleListViewModel> :
    SimpleListActivity<Article, VM>() {

    override val adapter = ArticleListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //监听LiveEvent
        observeLiveEvent()
    }

    override fun onItemClick(position: Int) {
        val article = adapter.data.getOrNull(position) ?: return
        ARouter.getInstance().build(RoutePath.WEB)
            .withString(WebActivity.WEB_URL, article.link)
            .navigation()
    }

    override fun onItemChildClick(view: View, position: Int) {
        val article = adapter.data.getOrNull(position) ?: return
        when (view.id) {
            R.id.ivCollect -> {
                clickCollect(article)
            }
        }
    }

    /**
     * 监听LiveEvent
     */
    open fun observeLiveEvent() {
        //收藏
        LiveEventBus.get<Long>(LiveEventKey.KEY_COLLECT_ARTICLE).observe(this) {
            updateArticle(it) { collect = true }
        }
        //取消收藏
        LiveEventBus.get<Long>(LiveEventKey.KEY_UNCOLLECT_ARTICLE).observe(this) {
            updateArticle(it) { collect = false }
        }
    }

    /**
     * 点击收藏
     */
    open fun clickCollect(article: Article) {
        article.run {
            viewModel.collectArticle(!collect, id)
        }
    }

    /**
     * 更新文章
     */
    protected fun updateArticle(
        id: Long,
        playLoad: String = ArticleListAdapter.PAYLOAD_COLLECT,
        block: Article.() -> Unit
    ) {
        adapter.data.find { article -> article.id == id }?.run {
            //获取index
            val index = adapter.data.indexOf(this)
            //更新article
            block.invoke(this)
            //更新列表
            adapter.notifyItemChanged(index + adapter.headerLayoutCount, playLoad)
        }
    }

}