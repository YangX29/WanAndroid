package com.example.wanandroid.viewmodel.ad

import com.example.wanandroid.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow

/**
 * @author: Yang
 * @date: 2023/2/5
 * @description: 广告页ViewModel
 */
class AdViewModel : BaseViewModel<AdViewState, AdViewIntent>() {

    override val mViewState = MutableStateFlow(AdViewState())

}