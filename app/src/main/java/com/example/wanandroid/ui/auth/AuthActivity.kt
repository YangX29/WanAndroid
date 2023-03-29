package com.example.wanandroid.ui.auth

import android.os.Bundle
import androidx.activity.viewModels
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.wanandroid.R
import com.example.wanandroid.base.BaseMVIActivity
import com.example.wanandroid.common.RoutePath
import com.example.wanandroid.databinding.ActivityAuthBinding
import com.example.wanandroid.viewmodel.auth.AuthViewIntent
import com.example.wanandroid.viewmodel.auth.AuthViewModel
import com.example.wanandroid.viewmodel.auth.AuthViewState

/**
 * @author: Yang
 * @date: 2023/3/27
 * @description: 登录注册页面
 */
@Route(path = RoutePath.AUTH)
class AuthActivity :
    BaseMVIActivity<ActivityAuthBinding, AuthViewState, AuthViewIntent, AuthViewModel>() {

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
        //初始化
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.flContainer, LoginFragment())
        transaction.commit()
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
        //TODO
        finish()
    }

}