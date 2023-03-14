package com.example.wanandroid.ui.guide

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.wanandroid.R
import com.example.wanandroid.base.BaseFragment
import com.example.wanandroid.databinding.FragmentGuideBinding
import com.example.wanandroid.databinding.LayoutCommonTabBarBinding
import com.example.wanandroid.view.adapter.CommonFragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator

/**
 * @author: Yang
 * @date: 2023/2/21
 * @description: 导航页
 */
class GuideFragment : BaseFragment<FragmentGuideBinding>() {

    //子fragment
    private val fragments by lazy<List<Fragment>> {
        listOf(SystemFragment(), WebGuideFragment(), TutorialFragment())
    }

    //子tab
    private val tabs =
        listOf(R.string.guide_tab_system, R.string.guide_tab_web, R.string.guide_tab_tutorial)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //初始化
        initView()
    }

    /**
     * 初始化View
     */
    private fun initView() {
        //viewPager
        mBinding.viewPager.adapter = CommonFragmentStateAdapter(fragments, requireActivity())
        //toolbar
        mBinding.toolbar.setBar<LayoutCommonTabBarBinding> {
            TabLayoutMediator(tab, mBinding.viewPager) { tab, position ->
                tab.setText(tabs[position])
            }.attach()
        }
    }

}