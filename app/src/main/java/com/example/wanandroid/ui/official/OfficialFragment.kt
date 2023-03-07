package com.example.wanandroid.ui.official

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.wanandroid.base.BaseMVIFragment
import com.example.wanandroid.databinding.FragmentOfficialBinding
import com.example.wanandroid.model.Category
import com.example.wanandroid.utils.extension.adaptImmersionByPadding
import com.example.wanandroid.view.adapter.CommonFragmentStateAdapter
import com.example.wanandroid.viewmodel.official.OfficialViewIntent
import com.example.wanandroid.viewmodel.official.OfficialViewModel
import com.example.wanandroid.viewmodel.official.OfficialViewState
import com.example.wanandroid.viewmodel.official.OfficialViewStatus
import com.google.android.material.tabs.TabLayoutMediator

/**
 * @author: Yang
 * @date: 2023/2/21
 * @description: 公众号页
 */
class OfficialFragment :
    BaseMVIFragment<FragmentOfficialBinding, OfficialViewState, OfficialViewIntent, OfficialViewModel>() {

    override val viewModel: OfficialViewModel by viewModels()

    //公众号tab
    private val fragments = mutableListOf<OfficialListFragment>()
    private val tabs = mutableListOf<Category>()
    private val pagerAdapter by lazy {
        CommonFragmentStateAdapter(fragments, requireActivity())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //初始化
        initView()
        initTab()
    }

    override fun handleViewState(viewState: OfficialViewState) {
        when (viewState.status) {
            is OfficialViewStatus.InitFinish -> {
                setupTabs(viewState.tabs ?: mutableListOf())
            }
            is OfficialViewStatus.InitFailed -> {
                //TODO
            }
        }
    }

    /**
     * 初始化View
     */
    private fun initView() {
        //沉浸式
        mBinding.tab.adaptImmersionByPadding()
        //viewPager
        mBinding.viewPager.adapter = pagerAdapter
        //tab
        TabLayoutMediator(mBinding.tab, mBinding.viewPager) { tab, position ->
            if (position >= tabs.size) return@TabLayoutMediator
            //公众号名
            tab.text = tabs[position].name
        }.attach()
    }

    /**
     * 初始化Tab
     */
    private fun initTab() {
        sendViewIntent(OfficialViewIntent.InitTab)
    }

    /**
     * 设置tab
     */
    @SuppressLint("NotifyDataSetChanged")
    private fun setupTabs(tabs: MutableList<Category>) {
        //tab列表
        this.tabs.addAll(tabs)
        //fragment列表
        val list = tabs.map { OfficialListFragment.newInstance(it.id) }
        fragments.addAll(list)
        pagerAdapter.notifyDataSetChanged()
    }

}