package com.example.wanandroid.ui.ad

import android.os.Bundle
import androidx.activity.viewModels
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.facade.callback.NavCallback
import com.alibaba.android.arouter.launcher.ARouter
import com.example.module_common.utils.extension.visible
import com.example.wanandroid.R
import com.example.wanandroid.base.BaseMVIActivity
import com.example.wanandroid.common.RoutePath
import com.example.wanandroid.databinding.ActivityAdBinding
import com.example.wanandroid.ui.web.WebActivity
import com.example.wanandroid.utils.extension.adaptImmersionByMargin
import com.example.wanandroid.utils.extension.loadWithDefault
import com.example.wanandroid.mvi.ad.AdViewIntent
import com.example.wanandroid.mvi.ad.AdViewModel
import com.example.wanandroid.mvi.ad.AdViewState
import com.example.wanandroid.mvi.ad.AdViewStatus

/**
 * @author: Yang
 * @date: 2023/2/5
 * @description: 广告页，包含图片广告和视频广告
 */
@Route(path = RoutePath.AD)
class AdActivity : BaseMVIActivity<ActivityAdBinding, AdViewState, AdViewIntent, AdViewModel>() {

    override val viewModel: AdViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //初始化
        initView()
    }

    override fun adaptImmersion() {
        mBinding.btSkip.adaptImmersionByMargin()
    }

    override fun handleViewState(viewState: AdViewState) {
        when (viewState.status) {
            is AdViewStatus.Start -> {
                //计时开始
                handleCountDown(viewState.status.time)
            }
            is AdViewStatus.CountDown -> {
                //倒计时
                handleCountDown(viewState.status.time)
            }
            is AdViewStatus.Finish -> {
                //倒计时结束，跳转到下一个页面
                jumpToHome(viewState.status.route)
            }
            is AdViewStatus.ShowAd -> {
                //处理广告
                handleAd(viewState.status.url)
            }
            is AdViewStatus.JumpToAd -> {
                //跳转到广告页面
                jumpToAdPage(viewState.status)
            }
            else -> {
            }
        }
    }




    /**
     * 初始化View
     */
    private fun initView() {
        //跳过按钮
        mBinding.btSkip.setOnClickListener {
            sendIntent(AdViewIntent.Skip)
        }
        //跳转到广告
        mBinding.ivAd.setOnClickListener {
            sendIntent(AdViewIntent.ClickAd)
        }
        //显示广告
        sendIntent(AdViewIntent.ShowAd)
    }

    /**
     * 处理倒计时
     */
    private fun handleCountDown(time: Int) {
        mBinding.btSkip.visible()
        mBinding.btSkip.text = getString(R.string.skip_button_with_time, time)
    }

    /**
     * TODO 处理广告
     */
    private fun handleAd(url: String) {
        mBinding.ivAd.loadWithDefault(url)
    }

    /**
     * 跳转到主页
     */
    private fun jumpToHome(route: String) {
        ARouter.getInstance()
            .build(route)
            .withTransition(R.anim.anim_page_fade_in, R.anim.anim_page_fade_out)
            .navigation(this, object : NavCallback() {
                override fun onArrival(postcard: Postcard?) {
                    // 跳转后结束当前页面，防止立即跳转导致页面跳转动画显示问题
                    finish()
                }
            })
    }

    /**
     * 跳转到广告页面
     */
    private fun jumpToAdPage(status: AdViewStatus.JumpToAd) {
        //TODO 处理从广告页返回怎么进入主页
        ARouter.getInstance().build(RoutePath.WEB)
            .withString(WebActivity.WEB_URL, status.ad)
            .withTransition(R.anim.anim_page_fade_in, R.anim.anim_page_fade_out)
            .navigation(this, object : NavCallback() {
                override fun onArrival(postcard: Postcard?) {
                    // 跳转后结束当前页面，防止立即跳转导致页面跳转动画显示问题
                    finish()
                }
            })
    }
}