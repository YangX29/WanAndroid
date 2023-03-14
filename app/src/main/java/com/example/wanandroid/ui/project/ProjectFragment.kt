package com.example.wanandroid.ui.project

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.wanandroid.base.BaseMVIFragment
import com.example.wanandroid.databinding.FragmentProjectBinding
import com.example.wanandroid.databinding.LayoutCommonTabBarBinding
import com.example.wanandroid.model.Category
import com.example.wanandroid.utils.extension.adaptImmersionByPadding
import com.example.wanandroid.viewmodel.project.ProjectViewIntent
import com.example.wanandroid.viewmodel.project.ProjectViewModel
import com.example.wanandroid.viewmodel.project.ProjectViewState
import com.example.wanandroid.viewmodel.project.ProjectViewStatus
import com.google.android.material.tabs.TabLayoutMediator

/**
 * @author: Yang
 * @date: 2023/2/21
 * @description: 项目页
 */
class ProjectFragment :
    BaseMVIFragment<FragmentProjectBinding, ProjectViewState, ProjectViewIntent, ProjectViewModel>() {

    override val viewModel: ProjectViewModel by viewModels()

    private val tabs = mutableListOf<Category>()
    private val pagerAdapter: ListPageFragmentStateAdapter by lazy {
        ListPageFragmentStateAdapter()
    }

    private inner class ListPageFragmentStateAdapter : FragmentStateAdapter(this) {
        override fun getItemCount(): Int {
            return tabs.size
        }

        override fun createFragment(position: Int): Fragment {
            return ProjectListFragment.newInstance(tabs[position].id)
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //初始化
        initView()
        initTab()
    }

    override fun handleViewState(viewState: ProjectViewState) {
        when (viewState.status) {
            is ProjectViewStatus.InitFinish -> {
                setupTabs(viewState.tabs ?: mutableListOf())
            }
            is ProjectViewStatus.InitFailed -> {
                //TODO
            }
        }
    }

    /**
     * 初始化View
     */
    private fun initView() {
        //viewPager
        mBinding.viewPager.adapter = pagerAdapter
        //tab
        mBinding.toolbar.setBar<LayoutCommonTabBarBinding> {
            TabLayoutMediator(tab, mBinding.viewPager) { tab, position ->
                if (position >= tabs.size) return@TabLayoutMediator
                //公众号名
                tab.text = tabs[position].name
            }.attach()
        }
    }

    /**
     * 初始化Tab
     */
    private fun initTab() {
        sendViewIntent(ProjectViewIntent.InitTab)
    }

    /**
     * 设置tab
     */
    @SuppressLint("NotifyDataSetChanged")
    private fun setupTabs(tabs: MutableList<Category>) {
        //tab列表
        this.tabs.addAll(tabs)
        //更新ViewPager2
        pagerAdapter.notifyDataSetChanged()
    }

}