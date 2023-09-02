package com.example.wanandroid.ui.article

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.wanandroid.base.BaseActivity
import com.example.wanandroid.common.RoutePath
import com.example.wanandroid.databinding.ActivityArticleSubBinding
import com.example.wanandroid.model.Category
import com.google.android.material.tabs.TabLayoutMediator

/**
 * @author: Yang
 * @date: 2023/3/17
 * @description: 二级文章列表页面
 */
@Route(path = RoutePath.ARTICLE_SUB)
class ArticleSubActivity : BaseActivity<ActivityArticleSubBinding>() {

    companion object {
        const val CATEGORY = "category"
        const val SUB_CATEGORY_ID = "sub_id"
    }

    //一级分类id
    @Autowired(name = CATEGORY)
    @JvmField
    var category: Category? = null

    //选中的二级分类id
    @Autowired(name = SUB_CATEGORY_ID)
    @JvmField
    var subId: Int = 0

    //公众号tab
    private val tabs = mutableListOf<Category>()
    private val pagerAdapter: ListPageFragmentStateAdapter by lazy {
        ListPageFragmentStateAdapter()
    }

    private inner class ListPageFragmentStateAdapter : FragmentStateAdapter(this) {
        override fun getItemCount(): Int {
            return tabs.size
        }

        override fun createFragment(position: Int): Fragment {
            val id = tabs[position].id
            return ArticleSubListFragment.newInstance(id)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //初始化
        initView()
        //初始化tab
        initTab()
    }

    /**
     * 初始化View
     */
    private fun initView() {
        //viewPager
        mBinding.viewPager.adapter = pagerAdapter
        //toolbar
        mBinding.toolbar.apply {
            setOnLeftClick { finish() }
            setTitle(category?.name ?: "")
        }
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
        category?.apply { setupTabs(children) }
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
     * 设置默认选中tab
     */
    private fun setDefaultSelectTab() {
        //默认选中
        val tab = tabs.find { it.id == subId }
        val index = tabs.indexOf(tab)
        mBinding.viewPager.currentItem = index
    }

}