package com.example.wanandroid.ui.article

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.wanandroid.ui.tab.TabPageFragment
import com.example.wanandroid.mvi.article.ArticleSubViewModel

/**
 * @author: Yang
 * @date: 2023/3/17
 * @description: 体系二级页面
 */
class ArticleSubFragment : TabPageFragment<ArticleSubViewModel>() {

    companion object {

        private const val CATEGORY_ID = "id"
        private const val SUB_CATEGORY_ID = "sub_id"

        //实例化Fragment对象
        fun newInstance(id: Int, subId: Int): ArticleSubListFragment {
            val fragment = ArticleSubListFragment()
            val bundle = Bundle()
            bundle.putInt(CATEGORY_ID, id)
            bundle.putInt(SUB_CATEGORY_ID, subId)
            fragment.arguments = bundle
            return fragment
        }
    }

    //一级分类id
    private var id = 0

    //选中的二级分类id
    private var subId = 0

    override fun createPageFragment(position: Int): Fragment {
        return ArticleSubListFragment.newInstance(tabs[position].id)
    }

    override val viewModel: ArticleSubViewModel by viewModels {
        ArticleSubViewModel.Factory(id)
    }

    override fun setDefaultSelectTab() {
        //默认选中
        val tab = tabs.find { it.id == subId }
        val index = tabs.indexOf(tab)
        mBinding.viewPager.currentItem = index
    }
}