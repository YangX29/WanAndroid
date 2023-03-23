package com.example.wanandroid.ui.ad

import android.os.Bundle
import androidx.activity.viewModels
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.example.module_common.utils.extension.visible
import com.example.wanandroid.R
import com.example.wanandroid.base.BaseMVIActivity
import com.example.wanandroid.common.RoutePath
import com.example.wanandroid.databinding.ActivityAdBinding
import com.example.wanandroid.ui.web.WebActivity
import com.example.wanandroid.utils.extension.adaptImmersionByMargin
import com.example.wanandroid.utils.extension.loadWithDefault
import com.example.wanandroid.viewmodel.ad.AdViewIntent
import com.example.wanandroid.viewmodel.ad.AdViewModel
import com.example.wanandroid.viewmodel.ad.AdViewState
import com.example.wanandroid.viewmodel.ad.AdViewStatus

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
                ARouter.getInstance()
                    .build(viewState.status.route)
                    .navigation()
                finish()
            }
            is AdViewStatus.ShowAd -> {
                //处理广告
                handleAd(viewState.status.url)
            }
            is AdViewStatus.JumpToAd -> {
                //跳转到广告页面
                //TODO 处理从广告页返回怎么进入主页
                ARouter.getInstance().build(RoutePath.WEB)
                    .withString(WebActivity.WEB_URL, viewState.status.ad)
                    .navigation()
                finish()
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

}