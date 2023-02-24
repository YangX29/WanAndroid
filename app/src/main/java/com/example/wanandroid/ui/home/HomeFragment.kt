package com.example.wanandroid.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.module_common.utils.extension.dp2px
import com.example.wanandroid.R
import com.example.wanandroid.base.BaseMVIFragment
import com.example.wanandroid.view.adapter.CommonFragmentStateAdapter
import com.example.wanandroid.databinding.FragmentHomeBinding
import com.example.wanandroid.utils.extension.adaptImmersionByPadding
import com.example.wanandroid.viewmodel.home.HomeViewIntent
import com.example.wanandroid.viewmodel.home.HomeViewModel
import com.example.wanandroid.viewmodel.home.HomeViewState
import com.google.android.material.tabs.TabLayoutMediator

/**
 * @author: Yang
 * @date: 2023/2/21
 * @description: 首页
 */
class HomeFragment :
    BaseMVIFragment<FragmentHomeBinding, HomeViewState, HomeViewIntent, HomeViewModel>() {

    //子fragment
    private val fragments by lazy<List<Fragment>> {
        listOf(HomeSubFragment(), SquareFragment(), QAndAFragment())
    }

    //子tab
    private val tabs =
        listOf(R.string.home_tab_home, R.string.home_tab_square, R.string.home_tab_q_and_a)

    override val viewModel: HomeViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //初始化view
        initView()
    }

    override fun handleViewState(viewState: HomeViewState) {
        when (viewState) {
            is HomeViewState.RefreshFinish -> {
                mBinding.swipeRefresh.isRefreshing = false
            }
            is HomeViewState.StartRefresh -> {
                mBinding.swipeRefresh.isRefreshing = true
            }
        }
    }

    /**
     * 初始化view
     */
    private fun initView() {
        //沉浸式
        mBinding.tab.adaptImmersionByPadding()
        //viewPager
        mBinding.viewPager.adapter = CommonFragmentStateAdapter(fragments, requireActivity())
        //tab
        mBinding.tab.apply {
            //添加tab
            addTab(newTab().setText(R.string.home_tab_home))
            addTab(newTab().setText(R.string.home_tab_square))
            addTab(newTab().setText(R.string.home_tab_q_and_a))
        }
        TabLayoutMediator(mBinding.tab, mBinding.viewPager) { tab, position ->
            tab.setText(tabs[position])
        }.attach()
        //下拉刷新
        mBinding.swipeRefresh.setColorSchemeResources(R.color.common_refresh_scheme)
        mBinding.tab.post {
            mBinding.swipeRefresh.setProgressViewEndTarget(false, mBinding.tab.bottom + 50.dp2px())
        }
        mBinding.swipeRefresh.setOnRefreshListener {
            sendViewIntent(HomeViewIntent.Refresh(mBinding.viewPager.currentItem))
        }
    }

}