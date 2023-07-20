package com.example.wanandroid.ui.guide

import android.view.View
import androidx.fragment.app.viewModels
import com.alibaba.android.arouter.launcher.ARouter
import com.example.wanandroid.R
import com.example.wanandroid.common.RoutePath
import com.example.wanandroid.mvi.guide.CategoryViewState
import com.example.wanandroid.mvi.guide.tree.TreeViewModel
import com.example.wanandroid.ui.guide.adapter.TreeListAdapter
import com.example.wanandroid.ui.list.ListPageFragment
import com.example.wanandroid.utils.toast.ToastUtils

/**
 * @author: Yang
 * @date: 2023/7/16
 * @description: 学习路径Fragment
 */
class TreeFragment : ListPageFragment<CategoryViewState, TreeViewModel>() {
    override val viewModel: TreeViewModel by viewModels()

    override val adapter = TreeListAdapter()

    override fun canLoadMore() = false

    override fun onRefresh(viewState: CategoryViewState) {
        super.onRefresh(viewState)
        adapter.setNewInstance(viewState.categoryList ?: mutableListOf())
    }

    override fun onItemClick(position: Int) {
        val category = adapter.getItem(position)
        //跳转到学习路径详情页面
        ARouter.getInstance().build(RoutePath.TREE_ARTICLE)
            .withParcelable(TreeArticleActivity.KEY_TREE, category)
            .navigation()
    }

    override fun onItemChildClick(view: View, position: Int) {
        if (view.id == R.id.btFollow) {
            ToastUtils.show("follow ${adapter.getItem(position).category}")
        }
    }
}