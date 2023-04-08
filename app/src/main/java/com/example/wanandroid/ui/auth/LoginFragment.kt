package com.example.wanandroid.ui.auth

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.alibaba.android.arouter.launcher.ARouter
import com.example.wanandroid.R
import com.example.wanandroid.base.BaseMVIFragment
import com.example.wanandroid.common.Constants
import com.example.wanandroid.common.RoutePath
import com.example.wanandroid.databinding.FragmentLoginBinding
import com.example.wanandroid.ui.web.WebActivity
import com.example.wanandroid.utils.ime.ImeUtils
import com.example.wanandroid.utils.toast.ToastUtils
import com.example.wanandroid.mvi.auth.AuthViewIntent
import com.example.wanandroid.mvi.auth.AuthViewModel
import com.example.wanandroid.mvi.auth.login.LoginViewIntent
import com.example.wanandroid.mvi.auth.login.LoginViewModel
import com.example.wanandroid.mvi.auth.login.LoginViewState

/**
 * @author: Yang
 * @date: 2023/3/28
 * @description: 登录页
 */
class LoginFragment :
    BaseMVIFragment<FragmentLoginBinding, LoginViewState, LoginViewIntent, LoginViewModel>() {

    override val viewModel: LoginViewModel by viewModels()

    private val authViewModel: AuthViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //初始化
        initView()
        //初始化数据
        initData()
    }

    override fun handleViewState(viewState: LoginViewState) {
        when (viewState) {
            is LoginViewState.LoginSuccess -> {
                sendViewIntent(authViewModel, AuthViewIntent.LoginSuccess)
            }
            is LoginViewState.LoginFailed -> {
                //TODO
            }
            is LoginViewState.ShowOldMsg -> {
                showOldLoginMsg(viewState.account, viewState.password)
            }
        }
    }

    /**
     * 初始化view
     */
    private fun initView() {
        //用户名
        mBinding.etAccount.setImeOptions(EditorInfo.IME_ACTION_NEXT)
        mBinding.etAccount.setOnEditorActionListener { actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_NEXT) {
                mBinding.etPassword.requestFocus()
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
        //密码
        mBinding.etPassword.setImeOptions(EditorInfo.IME_ACTION_DONE)
        mBinding.etPassword.setOnEditorActionListener { actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                login()
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
        //记住密码
        mBinding.ivRemember.setOnClickListener { switchRemember() }
        //忘记密码
        mBinding.tvForget.setOnClickListener { jumpToForgetPage() }
        //登录按钮
        mBinding.btLogin.setOnClickListener { login() }
        //去注册按钮
        mBinding.btGoRegister.setOnClickListener { goRegister() }
    }

    /**
     * 初始化数据
     */
    private fun initData() {
        sendViewIntent(LoginViewIntent.Init)
    }

    /**
     * 登录
     */
    private fun login() {
        val account = mBinding.etAccount.getInputText().trim()
        val password = mBinding.etPassword.getInputText().trim()
        val remember = mBinding.ivRemember.isSelected
        //隐藏键盘
        ImeUtils.hideSoftInput(requireActivity())
        //校验
        if (account.isEmpty()) {//用户名为空
            ToastUtils.show(requireContext(), R.string.toast_empty_account)
            ImeUtils.showSoftInput(mBinding.etAccount)
        } else if (password.isEmpty()) {//密码为空
            ToastUtils.show(requireContext(), R.string.toast_empty_password)
            ImeUtils.showSoftInput(mBinding.etPassword)
        } else {
            //登录
            sendViewIntent(LoginViewIntent.Login(account, password, remember))
        }
    }

    /**
     * 跳转到注册
     */
    private fun goRegister() {
        sendViewIntent(authViewModel, AuthViewIntent.SwitchToRegister)
    }

    /**
     * 跳转到忘记密码页面
     */
    private fun jumpToForgetPage() {
        ARouter.getInstance().build(RoutePath.WEB)
            .withString(WebActivity.WEB_URL, Constants.URL_FORGET_PASSWORD).navigation()
    }

    /**
     * 切换记住密码
     */
    private fun switchRemember() {
        mBinding.ivRemember.apply {
            isSelected = !isSelected
        }
    }

    /**
     * 显示上次登录信息
     */
    private fun showOldLoginMsg(account: String?, password: String?) {
        //用户名
        mBinding.etAccount.setInputText(account)
        //密码
        mBinding.etPassword.setInputText(password)
        //记住密码
        mBinding.ivRemember.isSelected = !password.isNullOrEmpty()
    }

}