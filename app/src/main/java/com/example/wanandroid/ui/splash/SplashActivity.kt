package com.example.wanandroid.ui.splash

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.viewModels
import coil.load
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.example.wanandroid.base.BaseActivity
import com.example.wanandroid.common.RoutePath
import com.example.wanandroid.databinding.ActivitySplashBinding
import com.example.wanandroid.viewmodel.splash.SplashViewIntent
import com.example.wanandroid.viewmodel.splash.SplashViewModel
import com.example.wanandroid.viewmodel.splash.SplashViewState

/**
 * @author: Yang
 * @date: 2023/1/15
 * @description: 启动页，根据不同情况跳转到不同页面：广告页、引导页和主页
 */
@SuppressLint("CustomSplashScreen")
@Route(path = RoutePath.SPLASH)
class SplashActivity :
    BaseActivity<ActivitySplashBinding, SplashViewState, SplashViewIntent, SplashViewModel>() {

    override val viewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        //进入下一个页面
        jumpNext()
    }

    override fun adaptImmersion() {

    }

    override fun handleViewState(viewState: SplashViewState) {
        if (viewState.time == 0) {
            ARouter.getInstance().build(RoutePath.HOME).navigation(this)
            finish()
        }
    }

    private fun initView() {
        mBinding.ivAd.load("https://img1.mydrivers.com/img/20210226/s_ff489bbf9a884b83a72837c099f23e97.jpg") {
            placeholder(null)
            error(null)
        }
    }

    private fun jumpNext() {
        //跳转到另一个页面
        sendIntent(SplashViewIntent.EnterApp)
    }

}