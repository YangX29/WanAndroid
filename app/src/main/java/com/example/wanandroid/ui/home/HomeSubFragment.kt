package com.example.wanandroid.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.alibaba.android.arouter.launcher.ARouter
import com.example.wanandroid.R
import com.example.wanandroid.common.LiveEventKey
import com.example.wanandroid.common.RoutePath
import com.example.wanandroid.model.Article
import com.example.wanandroid.mvi.home.sub.HomeSubViewModel
import com.example.wanandroid.mvi.home.sub.HomeSubViewState
import com.example.wanandroid.ui.article.ArticleListAdapter
import com.example.wanandroid.ui.list.ListPageFragment
import com.example.wanandroid.ui.web.WebActivity
import com.example.wanandroid.view.widget.wan.BannerHeaderView
import com.jeremyliao.liveeventbus.LiveEventBus

/**
 * @author: Yang
 * @date: 2023/2/22
 * @description: 首页的首页子tab
 */
class HomeSubFragment : ListPageFragment<HomeSubViewState, HomeSubViewModel>() {

    //banner
    private val header: BannerHeaderView by lazy { BannerHeaderView(requireContext()) }

    override val viewModel: HomeSubViewModel by viewModels()

    override val adapter = ArticleListAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //初始化
        initHomeView()
        //监听LiveEvent
        observeLiveEvent()
    }

    private fun initHomeView() {
        //添加header
        adapter.setHeaderView(header)
    }

    override fun onLoadMore(viewState: HomeSubViewState) {
        adapter.addData(viewState.articles ?: mutableListOf())
    }

    override fun onRefresh(viewState: HomeSubViewState) {
        //banner
        viewState.banners?.let {
            header.setBanner(it)
        }
        //列表
        adapter.setList(viewState.articles ?: mutableListOf())
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
    private fun observeLiveEvent() {
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
    private fun clickCollect(article: Article) {
        article.run {
            viewModel.collectArticle(!collect, id)
        }
    }

    /**
     * 更新文章
     */
    private fun updateArticle(
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