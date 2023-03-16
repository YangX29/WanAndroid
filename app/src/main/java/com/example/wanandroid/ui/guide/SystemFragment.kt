package com.example.wanandroid.ui.guide

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.wanandroid.model.Category
import com.example.wanandroid.ui.guide.adapter.TagCategoryListAdapter
import com.example.wanandroid.ui.list.ListPageFragment
import com.example.wanandroid.viewmodel.guide.system.SystemViewModel
import com.example.wanandroid.viewmodel.guide.system.SystemViewState

/**
 * @author: Yang
 * @date: 2023/3/14
 * @description: 知识体系页面
 */
class SystemFragment : ListPageFragment<SystemViewState, SystemViewModel>() {

    override val viewModel: SystemViewModel by viewModels()

    override val adapter = TagCategoryListAdapter<Category>()

    override fun showDivider() = false

    override fun canLoadMore() = false
    override fun onRefresh(viewState: SystemViewState) {
        adapter.setNewInstance(viewState.systems ?: mutableListOf())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //设置标签点击事件
        adapter.setOnTagClick { category, i ->
            //TODO 跳转到对应列表
        }
    }
}