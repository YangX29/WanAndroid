package com.example.wanandroid.base

import android.os.Bundle
import android.widget.Toast
import androidx.annotation.CallSuper
import androidx.viewbinding.ViewBinding
import com.example.module_common.base.BaseVBActivity
import com.example.wanandroid.base.mvi.ViewEvent
import com.example.wanandroid.base.mvi.ViewIntent
import com.example.wanandroid.base.mvi.ViewState
import com.example.wanandroid.utils.extension.launchWithLifecycle
import com.gyf.immersionbar.ImmersionBar
import kotlinx.coroutines.flow.distinctUntilChanged

/**
 * @author: Yang
 * @date: 2023/1/14
 * @description: WanAndroid项目Activity基类，继承[BaseVBActivity]
 */
abstract class BaseActivity<VB : ViewBinding, VS : ViewState, VI : ViewIntent, VM : BaseViewModel<VS, VI>> :
    BaseVBActivity<VB>() {

    abstract val viewModel: VM

    @CallSuper
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
        handleViewEvent(viewModel) {
            dispatchViewEvent(it)
        }
    }

    @CallSuper
    override fun onDestroy() {
        super.onDestroy()
        viewModel.release()
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
    open fun dispatchViewEvent(viewEvent: ViewEvent) = false

    /**
     * 处理当前界面状态
     */
    open fun handleViewState(viewState: VS) {

    }

    /**
     * 当前界面操作
     */
    fun sendIntent(intent: VI) {
        launchWithLifecycle {
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
        launchWithLifecycle {
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
        launchWithLifecycle {
            vm.viewState.collect {
                handler.invoke(it)
            }
        }
    }

    /**
     * 处理界面通用操作
     */
    protected fun <VS : ViewState, VI : ViewIntent> handleViewEvent(
        vm: BaseViewModel<VS, VI>,
        dispatcher: ((ViewEvent) -> Boolean)? = null
    ) {
        launchWithLifecycle {
            vm.viewEvent.distinctUntilChanged().collect {
                //如果不自定义处理方式，进行通用处理
                if (dispatcher?.invoke(it) != true) {
                    handleCommonViewEvent(it)
                }
            }
        }
    }

    /**
     * 界面通用操作处理
     */
    private fun handleCommonViewEvent(viewEvent: ViewEvent) {
        when (viewEvent) {
            is ViewEvent.Toast -> {
                Toast.makeText(this, viewEvent.msg, Toast.LENGTH_LONG).show()
            }
            is ViewEvent.JumpToPage -> {
                //TODO 路由跳转
            }
            is ViewEvent.Back -> {
                finish()
            }
        }
    }

}