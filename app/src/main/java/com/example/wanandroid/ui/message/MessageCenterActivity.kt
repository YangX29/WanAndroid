package com.example.wanandroid.ui.message

import android.os.Bundle
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.wanandroid.R
import com.example.wanandroid.base.BaseActivity
import com.example.wanandroid.common.RoutePath
import com.example.wanandroid.databinding.ActivityMessageCenterBinding
import com.example.wanandroid.databinding.LayoutToolbarTabBinding
import com.example.wanandroid.utils.user.LoginInterceptor
import com.google.android.material.tabs.TabLayoutMediator

/**
 * @author: Yang
 * @date: 2023/5/2
 * @description: 消息中心页面
 */
@Route(path = RoutePath.MESSAGE_CENTER, extras = LoginInterceptor.INTERCEPTOR_PAGE)
class MessageCenterActivity : BaseActivity<ActivityMessageCenterBinding>() {

    private val tabs = mutableListOf(
        MsgTab(R.string.message_tab_new, MessageListFragment.TYPE_UNREAD_LIST),
        MsgTab(R.string.message_tab_history, MessageListFragment.TYPE_READ_LIST)
    )
    private val pagerAdapter: ListPageFragmentStateAdapter by lazy {
        ListPageFragmentStateAdapter()
    }

    /**
     * 消息中心tab
     */
    private data class MsgTab(
        @StringRes val title: Int, val type: String
    )

    private inner class ListPageFragmentStateAdapter : FragmentStateAdapter(this) {
        override fun getItemCount(): Int {
            return tabs.size
        }

        override fun createFragment(position: Int): Fragment {
            return createPageFragment(position)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //初始化
        initView()
    }

    /**
     * 初始化View
     */
    private fun initView() {
        //viewPager
        mBinding.viewPager.adapter = pagerAdapter
        //tab
        mBinding.toolbar.setBar<LayoutToolbarTabBinding> {
            //tab
            TabLayoutMediator(tab, mBinding.viewPager) { tab, position ->
                if (position >= tabs.size) return@TabLayoutMediator
                tab.setText(tabs[position].title)
            }.attach()
            //返回按钮
            ivBack.setOnClickListener { finish() }
        }
    }

    /**
     * 创建消息列表Fragment
     */
    private fun createPageFragment(position: Int): MessageListFragment {
        return MessageListFragment.newInstance(tabs[position].type)
    }


}