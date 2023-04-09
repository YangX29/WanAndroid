package com.example.wanandroid.ui.auth

import android.os.Bundle
import androidx.activity.viewModels
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.example.wanandroid.R
import com.example.wanandroid.base.BaseMVIActivity
import com.example.wanandroid.common.RoutePath
import com.example.wanandroid.databinding.ActivityAuthBinding
import com.example.wanandroid.mvi.auth.AuthViewIntent
import com.example.wanandroid.mvi.auth.AuthViewModel
import com.example.wanandroid.mvi.auth.AuthViewState

/**
 * @author: Yang
 * @date: 2023/3/27
 * @description: 登录注册页面
 */
@Route(path = RoutePath.AUTH)
class AuthActivity :
    BaseMVIActivity<ActivityAuthBinding, AuthViewState, AuthViewIntent, AuthViewModel>() {

    companion object {
        const val KEY_TARGET_PATH = "target_path"
        const val KEY_TARGET_BUNDLE = "target_bundle"
    }

    @Autowired(name = KEY_TARGET_PATH)
    @JvmField
    var targetPath: String? = null

    @Autowired(name = KEY_TARGET_BUNDLE)
    @JvmField
    var targetBundle: Bundle? = null

    override val viewModel: AuthViewModel by viewModels()

    override fun adaptImmersion() {
        mBinding.root.fitsSystemWindows = true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //初始化
        initView()
    }

    override fun handleViewState(viewState: AuthViewState) {
        when (viewState) {
            is AuthViewState.SwitchToLogin -> {
                switchToLogin()
            }
            is AuthViewState.SwitchToRegister -> {
                switchToRegister()
            }
            is AuthViewState.LoginSuccess -> {
                loginSuccess()
            }
        }
    }

    /**
     * 初始化view
     */
    private fun initView() {
        //返回按钮
        mBinding.ivClose.setOnClickListener { finish() }
    }

    /**
     * 切换到登录页
     */
    private fun switchToLogin() {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.flContainer, LoginFragment())
        transaction.commitNow()
    }

    /**
     * 切换到注册页
     */
    private fun switchToRegister() {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.flContainer, RegisterFragment())
        transaction.commitNow()
    }

    /**
     * 登录成功
     */
    private fun loginSuccess() {
        if (targetPath.isNullOrEmpty()) {
            finish()
        } else {
            //处理登录拦截，跳转登录前到目标页面
            ARouter.getInstance().build(targetPath)
                .with(targetBundle)
                .navigation()
            finish()
        }
    }

}