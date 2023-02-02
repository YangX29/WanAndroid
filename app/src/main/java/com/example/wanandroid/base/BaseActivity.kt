package com.example.wanandroid.base

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.example.module_common.base.BaseVBActivity
import com.example.module_common.utils.log.MLog
import com.example.wanandroid.base.mvi.ViewEffect
import com.example.wanandroid.base.mvi.ViewIntent
import com.example.wanandroid.base.mvi.ViewState
import com.gyf.immersionbar.ImmersionBar
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

/**
 * @author: Yang
 * @date: 2023/1/14
 * @description: WanAndroid项目Activity基类，继承[BaseVBActivity]
 */
abstract class BaseActivity<VB : ViewBinding, VS : ViewState, VI : ViewIntent, VM : BaseViewModel<VS, VI>> :
    BaseVBActivity<VB>() {

    abstract val viewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //设置沉浸式
        setImmersionBar()
        //适配沉浸式状态栏
        adaptImmersion()
        //处理界面状态
        handleViewState(viewModel) {
            handleViewState(it)
        }
        //处理通用界面操作
        handleViewEffect(viewModel) {
            dispatchViewEffect(it)
        }
    }

    /**
     * 设置沉浸式状态栏
     */
    open fun setImmersionBar() {
        ImmersionBar.with(this)
            .statusBarDarkFont(statusBarDarkFont(), 0.2f)
            .init()
    }

    /**
     * 沉浸式适配
     */
    open fun adaptImmersion() {
        //默认设置fitsSystemWindows
        mBinding.root.fitsSystemWindows = true
    }

    /**
     * 状态栏文字颜色，默认为深色模式
     */
    open fun statusBarDarkFont() = true

    /**
     * 自定义处理通用界面操作
     */
    open fun dispatchViewEffect(viewEffect: ViewEffect) = false

    /**
     * 处理当前界面状态
     */
    open fun handleViewState(viewState: VS) {

    }

    /**
     * 当前界面操作
     */
    fun sendIntent(intent: VI) {
        lifecycleScope.launch {
            viewModel.viewIntent.emit(intent)
        }
    }

    /**
     * 发送ViewIntent
     */
    protected fun <VS : ViewState, VI : ViewIntent> sendViewIntent(
        vm: BaseViewModel<VS, VI>,
        intent: VI
    ) {
        lifecycleScope.launch {
            vm.viewIntent.emit(intent)
        }
    }


    /**
     * 处理界面单向数据流
     */
    protected fun <VS : ViewState, VI : ViewIntent> handleViewState(
        vm: BaseViewModel<VS, VI>,
        handler: (VS) -> Unit
    ) {
        lifecycleScope.launch {
            vm.viewState.distinctUntilChanged().collect {
                handler.invoke(it)
            }
        }
    }

    /**
     * 处理界面通用操作
     */
    protected fun <VS : ViewState, VI : ViewIntent> handleViewEffect(
        vm: BaseViewModel<VS, VI>,
        dispatcher: ((ViewEffect) -> Boolean)? = null
    ) {
        lifecycleScope.launch {
            vm.viewEffect.distinctUntilChanged().collect {
                //如果不自定义处理方式，进行通用处理
                if (dispatcher?.invoke(it) != true) {
                    handleCommonViewEffect(it)
                }
            }
        }
    }

    /**
     * 界面通用操作处理
     */
    private fun handleCommonViewEffect(viewEffect: ViewEffect) {
        when (viewEffect) {
            is ViewEffect.Toast -> {
                Toast.makeText(this, viewEffect.msg, Toast.LENGTH_LONG).show()
            }
            is ViewEffect.JumpToPage -> {
                //TODO 路由跳转
            }
            is ViewEffect.Back -> {
                finish()
            }
        }
    }

}