package com.example.wanandroid.ui.auth

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.wanandroid.R
import com.example.wanandroid.base.BaseMVIFragment
import com.example.wanandroid.databinding.FragmentRegisterBinding
import com.example.wanandroid.utils.ime.ImeUtils
import com.example.wanandroid.utils.toast.ToastUtils
import com.example.wanandroid.mvi.auth.AuthViewIntent
import com.example.wanandroid.mvi.auth.AuthViewModel
import com.example.wanandroid.mvi.auth.register.RegisterViewIntent
import com.example.wanandroid.mvi.auth.register.RegisterViewModel
import com.example.wanandroid.mvi.auth.register.RegisterViewState

/**
 * @author: Yang
 * @date: 2023/3/28
 * @description: 注册页面
 */
class RegisterFragment :
    BaseMVIFragment<FragmentRegisterBinding, RegisterViewState, RegisterViewIntent, RegisterViewModel>() {

    override val viewModel: RegisterViewModel by viewModels()

    private val authViewModel: AuthViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //初始化
        initView()
    }

    override fun handleViewState(viewState: RegisterViewState) {
        when (viewState) {
            is RegisterViewState.RegisterSuccess -> {
                sendViewIntent(authViewModel, AuthViewIntent.LoginSuccess)
            }
            is RegisterViewState.RegisterFailed -> {
                //TODO
            }
        }
    }

    /**
     * 初始化view
     */
    private fun initView() {
        //用户名
        mBinding.etAccount.setImeOptions(EditorInfo.IME_ACTION_NEXT)
        mBinding.etAccount.setOnEditorActionListener { action, _ ->
            if (action == EditorInfo.IME_ACTION_NEXT) {
                mBinding.etPassword.requestFocus()
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
        //密码
        mBinding.etPassword.setImeOptions(EditorInfo.IME_ACTION_NEXT)
        mBinding.etPassword.setOnEditorActionListener { action, _ ->
            if (action == EditorInfo.IME_ACTION_NEXT) {
                mBinding.etEnsure.requestFocus()
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
        //确认密码
        mBinding.etEnsure.setImeOptions(EditorInfo.IME_ACTION_DONE)
        mBinding.etEnsure.setOnEditorActionListener { action, _ ->
            if (action == EditorInfo.IME_ACTION_DONE) {
                register()
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
        //注册按钮
        mBinding.btRegister.setOnClickListener { register() }
        //去注册按钮
        mBinding.btGoLogin.setOnClickListener { goLogin() }
    }

    /**
     * 注册
     */
    private fun register() {
        val account = mBinding.etAccount.getInputText().trim()
        val password = mBinding.etPassword.getInputText().trim()
        val ensurePassword = mBinding.etEnsure.getInputText().trim()
        //隐藏键盘
        ImeUtils.hideSoftInput(requireActivity())
        //校验
        when {
            account.isEmpty() -> {//用户名为空
                ToastUtils.show(requireContext(), R.string.toast_empty_account)
                ImeUtils.showSoftInput(mBinding.etAccount)
            }
            password.isEmpty() -> {//密码为空
                ToastUtils.show(requireContext(), R.string.toast_empty_password)
                ImeUtils.showSoftInput(mBinding.etPassword)
            }
            ensurePassword.isEmpty() -> {//确认密码为空
                ToastUtils.show(requireContext(), R.string.toast_empty_ensure_password)
                ImeUtils.showSoftInput(mBinding.etEnsure)
            }
            ensurePassword != password -> {//密码不一致
                ToastUtils.show(requireContext(), R.string.toast_password_not_same)
                ImeUtils.showSoftInput(mBinding.etEnsure)
            }
            else -> {//注册
                sendViewIntent(RegisterViewIntent.Register(account, password, ensurePassword))
            }
        }
    }

    /**
     * 跳转到登录
     */
    private fun goLogin() {
        sendViewIntent(authViewModel, AuthViewIntent.SwitchToLogin)
    }

}