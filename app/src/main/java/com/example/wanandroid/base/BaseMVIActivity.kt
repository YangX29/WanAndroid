package com.example.wanandroid.base

import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.viewbinding.ViewBinding
import com.alibaba.android.arouter.launcher.ARouter
import com.example.wanandroid.base.mvi.ViewEvent
import com.example.wanandroid.base.mvi.ViewIntent
import com.example.wanandroid.base.mvi.ViewState
import com.example.wanandroid.common.RoutePath
import com.example.wanandroid.ui.web.WebActivity
import com.example.wanandroid.utils.extension.launchWhenCreated
import com.example.wanandroid.utils.toast.ToastUtils
import com.example.wanandroid.utils.view.LoadingManager

/**
 * @author: Yang
 * @date: 2023/1/14
 * @description: WanAndroid项目MVI架构Activity基类，继承[BaseActivity]
 */
abstract class BaseMVIActivity<VB : ViewBinding, VS : ViewState, VI : ViewIntent, VM : BaseViewModel<VS, VI>> :
    BaseActivity<VB>() {

    abstract val viewModel: VM

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        launchWhenCreated {
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
        launchWhenCreated {
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
        launchWhenCreated {
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
        launchWhenCreated {
            vm.viewEvent.collect {
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
                ToastUtils.show(viewEvent.res)
            }

            is ViewEvent.JumpToPage -> {
                //路由跳转
                ARouter.getInstance().build(viewEvent.route)
                    .with(viewEvent.bundle)
                    .navigation()
            }

            is ViewEvent.Back -> {
                finish()
            }

            is ViewEvent.JumpToWeb -> {
                ARouter.getInstance().build(RoutePath.WEB)
                    .withString(WebActivity.WEB_URL, viewEvent.url)
                    .navigation()
            }

            is ViewEvent.Loading -> {
                //通用loading
                if (viewEvent.show) {
                    LoadingManager.showCurrentLoading()
                } else {
                    LoadingManager.dismissCurrentLoading()
                }
            }

            is ViewEvent.JumpToPageWithIntent -> {
                startActivity(viewEvent.intent)
            }
        }
    }

}