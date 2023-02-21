package com.example.wanandroid.base

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.CallSuper
import androidx.viewbinding.ViewBinding
import com.example.module_common.base.BaseVBActivity
import com.example.module_common.base.BaseVBFragment
import com.example.wanandroid.base.mvi.ViewEvent
import com.example.wanandroid.base.mvi.ViewIntent
import com.example.wanandroid.base.mvi.ViewState
import com.example.wanandroid.utils.extension.launchWithLifecycle
import kotlinx.coroutines.flow.distinctUntilChanged

/**
 * @author: Yang
 * @date: 2023/1/14
 * @description: WanAndroid项目MVI架构Fragment基类，继承[BaseFragment]
 */
abstract class BaseMVIFragment<VB : ViewBinding, VS : ViewState, VI : ViewIntent, VM : BaseViewModel<VS, VI>> :
    BaseVBFragment<VB>() {

    abstract val viewModel: VM

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //处理界面状态
        handleViewState(viewModel) {
            handleViewState(it)
        }
        //处理通用界面操作
        handleViewEvent(viewModel) {
            dispatchViewEvent(it)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.release()
    }

    /**
     * 自定义处理通用界面操作，返回true表示自定义处理
     */
    open fun dispatchViewEvent(viewEvent: ViewEvent) = false

    /**
     * 处理当前界面状态
     */
    open fun handleViewState(viewState: VS) {

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
                Toast.makeText(requireContext(), viewEvent.msg, Toast.LENGTH_LONG).show()
            }
            is ViewEvent.JumpToPage -> {
                //TODO 路由跳转
            }
            is ViewEvent.Back -> {
                //TODO
                parentFragmentManager.popBackStack()
            }
        }
    }

}