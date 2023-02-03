package com.example.wanandroid.base

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.example.module_common.base.BaseVBActivity
import com.example.module_common.base.BaseVBFragment
import com.example.wanandroid.base.mvi.ViewEvent
import com.example.wanandroid.base.mvi.ViewIntent
import com.example.wanandroid.base.mvi.ViewState
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

/**
 * @author: Yang
 * @date: 2023/1/14
 * @description: WanAndroid项目Fragment基类，继承[BaseVBActivity]
 */
abstract class BaseFragment<VB : ViewBinding, VS : ViewState, VI : ViewIntent, VM : BaseViewModel<VS, VI>> :
    BaseVBFragment<VB>() {

    abstract val viewModel: VM

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
        lifecycleScope.launch {
            vm.viewEffect.distinctUntilChanged().collect {
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