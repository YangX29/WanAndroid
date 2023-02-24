package com.example.wanandroid.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.viewbinding.ViewBinding
import com.example.wanandroid.base.BaseMVIFragment
import com.example.wanandroid.base.BaseViewModel
import com.example.wanandroid.base.mvi.ViewIntent
import com.example.wanandroid.base.mvi.ViewState
import com.example.wanandroid.utils.extension.launchWithLifecycle
import com.example.wanandroid.viewmodel.home.HomeViewIntent
import com.example.wanandroid.viewmodel.home.HomeViewModel
import com.example.wanandroid.viewmodel.home.TabViewState

/**
 * @author: Yang
 * @date: 2023/2/23
 * @description: 首页子tabFragment基类,用于处理通用刷新等逻辑
 */
abstract class HomeSubTabFragment<VB : ViewBinding, VS : ViewState, VI : ViewIntent, VM : BaseViewModel<VS, VI>> :
    BaseMVIFragment<VB, VS, VI, VM>() {

    //首页共享ViewModel
    protected val homeViewModel: HomeViewModel by activityViewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //处理首页ViewState
        launchWithLifecycle {
            homeViewModel.tabState.collect {
                handleTabViewState(it)
            }
        }
    }

    /**
     * 处理首页Tab的ViewState
     */
    open fun handleTabViewState(tabViewState: TabViewState) {
        when (tabViewState) {
            is TabViewState.StartRefresh -> {
                refresh()
            }
        }
    }

    /**
     * 发送首页intent
     */
    protected fun sendHomeIntent(intent: HomeViewIntent) {
        launchWithLifecycle {
            homeViewModel.viewIntent.emit(intent)
        }
    }

    /**
     * 刷新
     */
    abstract fun refresh()

}