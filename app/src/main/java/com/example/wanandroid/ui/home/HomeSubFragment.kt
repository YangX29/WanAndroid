package com.example.wanandroid.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.wanandroid.databinding.FragmentHomeSubBinding
import com.example.wanandroid.viewmodel.home.HomeViewIntent
import com.example.wanandroid.viewmodel.home.sub.HomeSubViewIntent
import com.example.wanandroid.viewmodel.home.sub.HomeSubViewModel
import com.example.wanandroid.viewmodel.home.sub.HomeSubViewState

/**
 * @author: Yang
 * @date: 2023/2/22
 * @description: 首页的首页子tab
 */
class HomeSubFragment :
    HomeSubTabFragment<FragmentHomeSubBinding, HomeSubViewState, HomeSubViewIntent, HomeSubViewModel>() {

    override val viewModel: HomeSubViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun handleViewState(viewState: HomeSubViewState) {
        when(viewState) {
            is HomeSubViewState.RefreshFinish -> {
                sendHomeIntent(HomeViewIntent.RefreshFinish)
            }
        }
    }

    override fun refresh() {
        sendViewIntent(HomeSubViewIntent.Refresh)
    }

}