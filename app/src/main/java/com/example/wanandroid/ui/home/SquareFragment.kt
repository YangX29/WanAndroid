package com.example.wanandroid.ui.home

import androidx.fragment.app.viewModels
import com.example.wanandroid.ui.list.ListPageFragment
import com.example.wanandroid.viewmodel.home.square.SquareViewModel
import com.example.wanandroid.viewmodel.home.square.SquareViewState

/**
 * @author: Yang
 * @date: 2023/2/22
 * @description: 广场页面
 */
class SquareFragment : ListPageFragment<SquareViewState, SquareViewModel>() {

    override val adapter = ArticleListAdapter()
    override val viewModel: SquareViewModel by viewModels()

    override fun onLoadMore(viewState: SquareViewState) {
        adapter.addData(viewState.articles ?: mutableListOf())
    }

    override fun onRefresh(viewState: SquareViewState) {
        adapter.setList(viewState.articles ?: mutableListOf())
    }


}