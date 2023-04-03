package com.example.wanandroid.ui.official

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.wanandroid.ui.tab.TabPageFragment
import com.example.wanandroid.mvi.official.OfficialViewModel

/**
 * @author: Yang
 * @date: 2023/2/21
 * @description: 公众号页
 */
class OfficialFragment : TabPageFragment<OfficialViewModel>() {
    override fun createPageFragment(position: Int): Fragment {
        return OfficialListFragment.newInstance(tabs[position].id)
    }

    override val viewModel: OfficialViewModel by viewModels()


}