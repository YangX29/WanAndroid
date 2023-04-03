package com.example.wanandroid.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.alibaba.android.arouter.launcher.ARouter
import com.example.module_common.utils.extension.visible
import com.example.wanandroid.R
import com.example.wanandroid.base.BaseMVIFragment
import com.example.wanandroid.common.RoutePath
import com.example.wanandroid.databinding.FragmentHomeBinding
import com.example.wanandroid.databinding.LayoutHomeBarBinding
import com.example.wanandroid.view.adapter.CommonFragmentStateAdapter
import com.example.wanandroid.mvi.home.HomeViewIntent
import com.example.wanandroid.mvi.home.HomeViewModel
import com.example.wanandroid.mvi.home.HomeViewState
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
        listOf(HomeSubFragment(), SquareFragment(), QAFragment())
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

    /**
     * 初始化view
     */
    private fun initView() {
        //viewPager
        mBinding.viewPager.adapter = CommonFragmentStateAdapter(fragments, requireActivity())
        mBinding.viewPager.registerOnPageChangeCallback(HomeTabChangeCallback())
        //toolbar
        mBinding.toolbar.setBar<LayoutHomeBarBinding> {
            TabLayoutMediator(tab, mBinding.viewPager) { tab, position ->
                tab.setText(tabs[position])
            }.attach()
            //搜索
            ivSearch.setOnClickListener { jumpToSearch() }
            //添加
            ivAdd.setOnClickListener { jumpToAddArticle() }
        }
    }

    /**
     * 跳转到搜索页
     */
    private fun jumpToSearch() {
        ARouter.getInstance().build(RoutePath.SEARCH)
            .navigation()
    }

    /**
     * 跳转到添加文章页
     */
    private fun jumpToAddArticle() {
        ARouter.getInstance().build(RoutePath.SHARE)
            .navigation()
    }

    private inner class HomeTabChangeCallback() : OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            //广场tab显示添加按钮
            mBinding.toolbar.setBar<LayoutHomeBarBinding> {
                ivAdd.visible(position == 1)
            }
        }
    }

}