package com.example.wanandroid.common.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

/**
 * @author: Yang
 * @date: 2023/2/21
 * @description: 通用ViewPager适配器
 */
class CommonFragmentStateAdapter(private val fragments: List<Fragment>, activity: FragmentActivity) :
    FragmentStateAdapter(activity) {

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}