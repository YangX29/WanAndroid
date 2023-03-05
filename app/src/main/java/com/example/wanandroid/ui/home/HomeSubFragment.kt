package com.example.wanandroid.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.wanandroid.ui.list.ListPageFragment
import com.example.wanandroid.view.common.BannerHeaderView
import com.example.wanandroid.viewmodel.home.sub.HomeSubViewModel
import com.example.wanandroid.viewmodel.home.sub.HomeSubViewState

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
        viewState.articles?.let {
            adapter.addData(it)
        }
    }

    override fun onRefresh(viewState: HomeSubViewState) {
        //banner
        viewState.banners?.let {
            header.setBanner(it)
        }
        //列表
        viewState.articles?.let {
            adapter.setList(it)
        }
    }

}