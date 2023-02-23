package com.example.wanandroid.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.wanandroid.R
import com.example.wanandroid.base.BaseFragment
import com.example.wanandroid.view.adapter.CommonFragmentStateAdapter
import com.example.wanandroid.databinding.FragmentHomeBinding
import com.example.wanandroid.utils.extension.adaptImmersionByPadding
import com.google.android.material.tabs.TabLayoutMediator

/**
 * @author: Yang
 * @date: 2023/2/21
 * @description: 首页
 */
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    //子fragment
    private val fragments by lazy<List<Fragment>> {
        listOf(HomeSubFragment(), SquareFragment(), QAndAFragment())
    }

    //子tab
    private val tabs =
        listOf(R.string.home_tab_home, R.string.home_tab_square, R.string.home_tab_q_and_a)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //初始化view
        initView()
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
    }

}