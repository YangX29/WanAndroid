package com.example.wanandroid.ui.guide

import androidx.fragment.app.viewModels
import com.example.wanandroid.ui.guide.adapter.TutorialListAdapter
import com.example.wanandroid.ui.list.ListPageFragment
import com.example.wanandroid.viewmodel.guide.tutorial.TutorialViewModel
import com.example.wanandroid.viewmodel.guide.tutorial.TutorialViewState

/**
 * @author: Yang
 * @date: 2023/3/14
 * @description: 教程页面
 */
class TutorialFragment : ListPageFragment<TutorialViewState, TutorialViewModel>() {

    override val viewModel: TutorialViewModel by viewModels()

    override val adapter = TutorialListAdapter()

    override fun canLoadMore() = false
    override fun onRefresh(viewState: TutorialViewState) {
        super.onRefresh(viewState)
        adapter.setNewInstance(viewState.tutorials ?: mutableListOf())
    }



}