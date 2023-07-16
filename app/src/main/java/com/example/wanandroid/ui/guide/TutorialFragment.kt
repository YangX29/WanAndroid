package com.example.wanandroid.ui.guide

import androidx.fragment.app.viewModels
import com.alibaba.android.arouter.launcher.ARouter
import com.example.wanandroid.common.RoutePath
import com.example.wanandroid.mvi.guide.CategoryViewState
import com.example.wanandroid.mvi.guide.tutorial.TutorialViewModel
import com.example.wanandroid.ui.guide.adapter.TutorialListAdapter
import com.example.wanandroid.ui.list.ListPageFragment

/**
 * @author: Yang
 * @date: 2023/3/14
 * @description: 教程页面
 */
class TutorialFragment : ListPageFragment<CategoryViewState, TutorialViewModel>() {

    override val viewModel: TutorialViewModel by viewModels()

    override val adapter = TutorialListAdapter()

    override fun canLoadMore() = false
    override fun onRefresh(viewState: CategoryViewState) {
        super.onRefresh(viewState)
        adapter.setNewInstance(viewState.categoryList ?: mutableListOf())
    }

    override fun onItemClick(position: Int) {
        //跳转到教程详情页面
        ARouter.getInstance().build(RoutePath.TUTORIAL_ARTICLE)
            .withParcelable(TutorialArticleActivity.KEY_TUTORIAL, adapter.getItem(position))
            .navigation()
    }

}