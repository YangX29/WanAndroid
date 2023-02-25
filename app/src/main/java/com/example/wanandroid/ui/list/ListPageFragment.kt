package com.example.wanandroid.ui.list

import android.os.Bundle
import android.view.View
import com.example.wanandroid.base.BaseMVIFragment
import com.example.wanandroid.base.BaseViewModel
import com.example.wanandroid.base.mvi.ViewIntent
import com.example.wanandroid.base.mvi.ViewState
import com.example.wanandroid.databinding.FragmentListPageBinding

/**
 * @author: Yang
 * @date: 2023/2/25
 * @description: 通用列表Fragment基类
 */
abstract class ListPageFragment<VS : ViewState, VI : ViewIntent, VM : BaseViewModel<VS, VI>> :
    BaseMVIFragment<FragmentListPageBinding, VS, VI, VM>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //初始化
        initView()
    }

    /**
     * 初始化View
     */
    private fun initView() {

    }

}