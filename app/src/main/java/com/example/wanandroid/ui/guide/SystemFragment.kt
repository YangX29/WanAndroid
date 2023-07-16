package com.example.wanandroid.ui.guide

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.alibaba.android.arouter.launcher.ARouter
import com.example.wanandroid.common.RoutePath
import com.example.wanandroid.model.Category
import com.example.wanandroid.mvi.guide.CategoryViewState
import com.example.wanandroid.mvi.guide.system.SystemViewModel
import com.example.wanandroid.ui.article.ArticleSubActivity
import com.example.wanandroid.ui.guide.adapter.TagCategoryListAdapter
import com.example.wanandroid.ui.list.ListPageFragment

/**
 * @author: Yang
 * @date: 2023/3/14
 * @description: 知识体系页面
 */
class SystemFragment : ListPageFragment<CategoryViewState, SystemViewModel>() {

    override val viewModel: SystemViewModel by viewModels()

    override val adapter = TagCategoryListAdapter<Category>()

    override fun showDivider() = false

    override fun canLoadMore() = false
    override fun onRefresh(viewState: CategoryViewState) {
        adapter.setNewInstance(viewState.categoryList ?: mutableListOf())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //设置标签点击事件
        adapter.setOnTagClick { category, index ->
            ARouter.getInstance().build(RoutePath.ARTICLE_SUB)
                .withParcelable(ArticleSubActivity.CATEGORY, category)
                .withInt(ArticleSubActivity.SUB_CATEGORY_ID, category.children[index].id)
                .navigation()
        }
    }
}