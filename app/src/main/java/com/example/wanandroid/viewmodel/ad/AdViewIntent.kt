package com.example.wanandroid.viewmodel.ad

import com.example.wanandroid.base.mvi.ViewIntent

/**
 * @author: Yang
 * @date: 2023/2/5
 * @description: 广告页ViewIntent
 */
sealed class AdViewIntent : ViewIntent() {
    //显示广告
    object ShowAd : AdViewIntent()

    //跳过广告
    object Skip : AdViewIntent()

    //点击广告
    object ClickAd : AdViewIntent()
}