package com.example.wanandroid.ui.splash

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.viewModels
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.facade.callback.NavCallback
import com.alibaba.android.arouter.launcher.ARouter
import com.example.wanandroid.R
import com.example.wanandroid.base.BaseMVIActivity
import com.example.wanandroid.common.RoutePath
import com.example.wanandroid.databinding.ActivitySplashBinding
import com.example.wanandroid.mvi.splash.SplashViewIntent
import com.example.wanandroid.mvi.splash.SplashViewModel
import com.example.wanandroid.mvi.splash.SplashViewState
import com.example.wanandroid.utils.extension.loadWithDefault

/**
 * @author: Yang
 * @date: 2023/1/15
 * @description: 启动页，根据不同情况跳转到不同页面：广告页、引导页和主页
 */
@SuppressLint("CustomSplashScreen")
@Route(path = RoutePath.SPLASH)
class SplashActivity :
    BaseMVIActivity<ActivitySplashBinding, SplashViewState, SplashViewIntent, SplashViewModel>() {

    companion object {
        private const val TAG = "SplashActivity"
        private const val SPLASH_ICON =
            "https://img1.mydrivers.com/img/20210226/s_ff489bbf9a884b83a72837c099f23e97.jpg"
    }

    override val viewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        //进入下一个页面
        jumpNext()
    }

    override fun handleViewState(viewState: SplashViewState) {
        if (viewState.time == 0) {
            ARouter.getInstance().build(RoutePath.AD)
                .withTransition(R.anim.anim_page_fade_in, R.anim.anim_page_fade_out)
                .navigation(this, object : NavCallback() {
                    override fun onArrival(postcard: Postcard?) {
                        // 跳转后结束当前页面，防止立即跳转导致页面跳转动画显示问题
                        finish()
                    }
                })
        }
    }

    private fun initView() {
        mBinding.ivAd.loadWithDefault(SPLASH_ICON)
    }

    private fun jumpNext() {
        //跳转到另一个页面
        sendIntent(SplashViewIntent.EnterApp)
    }

}