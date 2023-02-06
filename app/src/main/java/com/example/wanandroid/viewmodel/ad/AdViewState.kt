package com.example.wanandroid.viewmodel.ad

import com.example.wanandroid.base.mvi.ViewState

/**
 * @author: Yang
 * @date: 2023/2/5
 * @description: 广告页ViewState
 */
data class AdViewState(
    val status: AdViewStatus? = null
) : ViewState() {

}

/**
 * 广告页显示
 */
sealed class AdViewStatus {
    //开始
    data class Start(val time: Int) : AdViewStatus()

    //倒计时
    data class CountDown(val time: Int) : AdViewStatus()

    //结束
    data class Finish(val route: String) : AdViewStatus()

    //TODO 显示广告，传入广告信息
    data class ShowAd(val url: String) : AdViewStatus()
}