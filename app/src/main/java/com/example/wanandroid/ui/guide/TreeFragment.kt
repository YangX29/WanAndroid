package com.example.wanandroid.ui.guide

import android.view.View
import androidx.fragment.app.viewModels
import com.alibaba.android.arouter.launcher.ARouter
import com.example.wanandroid.R
import com.example.wanandroid.common.RoutePath
import com.example.wanandroid.mvi.guide.tree.TreeViewModel
import com.example.wanandroid.mvi.guide.tree.TreeViewState
import com.example.wanandroid.ui.article.ArticleSubActivity
import com.example.wanandroid.ui.guide.adapter.TreeListAdapter
import com.example.wanandroid.ui.list.ListPageFragment
import com.example.wanandroid.utils.toast.ToastUtils

/**
 * @author: Yang
 * @date: 2023/7/16
 * @description: 学习路径Fragment
 */
class TreeFragment : ListPageFragment<TreeViewState, TreeViewModel>() {
    override val viewModel: TreeViewModel by viewModels()

    override val adapter = TreeListAdapter()

    override fun canLoadMore() = false

    override fun onRefresh(viewState: TreeViewState) {
        super.onRefresh(viewState)
        adapter.setNewInstance(viewState.tree ?: mutableListOf())
    }

    override fun onItemClick(position: Int) {
        val category = adapter.getItem(position)
        //跳转到教程详情页面
        ARouter.getInstance().build(RoutePath.ARTICLE_SUB)
            .withParcelable(ArticleSubActivity.CATEGORY, category)
            .withInt(ArticleSubActivity.SUB_CATEGORY_ID, category.children[0].id)
            .navigation()
    }

    override fun onItemChildClick(view: View, position: Int) {
        if (view.id == R.id.btFollow) {
            ToastUtils.show("follow ${adapter.getItem(position).category}")
        }
    }
}