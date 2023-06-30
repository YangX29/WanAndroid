package com.example.wanandroid.ui.main

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.example.wanandroid.R
import com.example.wanandroid.base.BaseMVIActivity
import com.example.wanandroid.common.RoutePath
import com.example.wanandroid.databinding.ActivityMainBinding
import com.example.wanandroid.mvi.main.MainViewIntent
import com.example.wanandroid.mvi.main.MainViewModel
import com.example.wanandroid.mvi.main.MainViewState
import com.example.wanandroid.ui.guide.GuideFragment
import com.example.wanandroid.ui.home.HomeFragment
import com.example.wanandroid.ui.mine.MineFragment
import com.example.wanandroid.ui.official.OfficialFragment
import com.example.wanandroid.ui.project.ProjectFragment
import com.example.wanandroid.ui.web.WebActivity
import com.example.wanandroid.utils.net.UrlUtils
import com.example.wanandroid.utils.view.ClipboardTip
import com.example.wanandroid.view.adapter.CommonFragmentStateAdapter

/**
 * 首页
 */
@Route(path = RoutePath.HOME)
class MainActivity :
    BaseMVIActivity<ActivityMainBinding, MainViewState, MainViewIntent, MainViewModel>() {

    override val viewModel: MainViewModel by viewModels()

    //主页tab对应fragment
    private val fragments by lazy<List<Fragment>> {
        listOf(
            HomeFragment(),
            GuideFragment(),
            OfficialFragment(),
            ProjectFragment(),
            MineFragment()
        )
    }

    //tab对应id
    private val tabIds = listOf(
        R.id.tab_home, R.id.tab_guide, R.id.tab_official, R.id.tab_project, R.id.tab_mine
    )

    //上次检查的剪切板内容
    private var lastClipboardText = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //初始化view
        initView()
        //检查登录状态
        sendIntent(MainViewIntent.CheckLoginState)
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        //主页检查剪切板，打开链接
        if (hasFocus && mBinding.viewPager.currentItem == 0) {
            ClipboardTip.checkClipboard(this, getString(R.string.share_article_link_open), {
                //打开链接
                ARouter.getInstance().build(RoutePath.WEB)
                    .withString(WebActivity.WEB_URL, it)
                    .navigation()
            }, {
                //只显示链接，且不显示与上次相同的链接
                val show = UrlUtils.checkUrl(it.toString()) && lastClipboardText != it
                if (show) {
                    lastClipboardText = it.toString()
                }
                return@checkClipboard show
            })
        } else {
            ClipboardTip.hideTipView(this)
        }
    }

    /**
     * 初始化
     */
    private fun initView() {
        //viewpager
        mBinding.viewPager.adapter = CommonFragmentStateAdapter(fragments, this)
        mBinding.viewPager.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                pageSelected(position)
            }
        })
        //tab
        mBinding.bnvTab.setOnItemSelectedListener {
            tabSelected(it)
            return@setOnItemSelectedListener true
        }
        mBinding.bnvTab.setOnItemReselectedListener {
            //TODO 点击当前tab，刷新
        }
    }

    /**
     * 页面切换
     */
    private fun pageSelected(position: Int) {
        //修改选中tab
        mBinding.bnvTab.selectedItemId = tabIds[position]
    }

    /**
     * tab切换
     */
    private fun tabSelected(item: MenuItem) {
        //修改选中页面
        mBinding.viewPager.currentItem = tabIds.indexOf(item.itemId)
    }

}
