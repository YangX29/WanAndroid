package com.example.wanandroid.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.alibaba.android.arouter.launcher.ARouter
import com.example.wanandroid.common.RoutePath
import com.example.wanandroid.ui.article.ArticleListAdapter
import com.example.wanandroid.ui.list.ListPageFragment
import com.example.wanandroid.ui.web.WebActivity
import com.example.wanandroid.view.widget.wan.BannerHeaderView
import com.example.wanandroid.mvi.home.sub.HomeSubViewModel
import com.example.wanandroid.mvi.home.sub.HomeSubViewState

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

}