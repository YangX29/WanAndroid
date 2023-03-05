package com.example.wanandroid.ui.home

import androidx.fragment.app.viewModels
import com.example.wanandroid.ui.list.ListPageFragment
import com.example.wanandroid.viewmodel.home.qa.QAViewModel
import com.example.wanandroid.viewmodel.home.qa.QAViewState

/**
 * @author: Yang
 * @date: 2023/2/22
 * @description: 问答页面
 */
class QAFragment : ListPageFragment<QAViewState, QAViewModel>() {

    override val adapter = ArticleListAdapter()

    override val viewModel: QAViewModel by viewModels()

    override fun onLoadMore(viewState: QAViewState) {
        adapter.addData(viewState.articles ?: mutableListOf())
    }

    override fun onRefresh(viewState: QAViewState) {
        adapter.setList(viewState.articles ?: mutableListOf())
    }

}