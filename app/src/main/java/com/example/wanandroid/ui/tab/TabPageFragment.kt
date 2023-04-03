package com.example.wanandroid.ui.tab

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.wanandroid.base.BaseMVIFragment
import com.example.wanandroid.databinding.FragmentTabPageBinding
import com.example.wanandroid.databinding.LayoutCommonTabBarBinding
import com.example.wanandroid.model.Category
import com.example.wanandroid.mvi.tab.TabPageViewModel
import com.example.wanandroid.mvi.tab.TabPageViewIntent
import com.example.wanandroid.mvi.tab.TabPageViewState
import com.example.wanandroid.mvi.tab.TabPageViewStatus
import com.google.android.material.tabs.TabLayoutMediator

/**
 * @author: Yang
 * @date: 2023/3/17
 * @description: 通用tabLayout+viewPager2页面
 */
abstract class TabPageFragment<VM : TabPageViewModel> :
    BaseMVIFragment<FragmentTabPageBinding, TabPageViewState, TabPageViewIntent, VM>() {

    //公众号tab
    protected val tabs = mutableListOf<Category>()
    private val pagerAdapter: ListPageFragmentStateAdapter by lazy {
        ListPageFragmentStateAdapter()
    }

    private inner class ListPageFragmentStateAdapter : FragmentStateAdapter(this) {
        override fun getItemCount(): Int {
            return tabs.size
        }

        override fun createFragment(position: Int): Fragment {
            return createPageFragment(position)
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //初始化
        initView()
        initTab()
    }

    override fun handleViewState(viewState: TabPageViewState) {
        when (viewState.status) {
            is TabPageViewStatus.InitFinish -> {
                setupTabs(viewState.tabs ?: mutableListOf())
            }
            is TabPageViewStatus.InitFailed -> {
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
        sendViewIntent(TabPageViewIntent.InitTab)
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
        //默认选中
        setDefaultSelectTab()
    }

    /**
     * 创建子fragment页面
     */
    abstract fun createPageFragment(position: Int): Fragment

    open fun setDefaultSelectTab() {}

}