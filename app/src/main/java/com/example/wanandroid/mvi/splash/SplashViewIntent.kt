package com.example.wanandroid.mvi.splash

import com.example.wanandroid.base.mvi.ViewIntent

/**
 * @author: Yang
 * @date: 2023/2/2
 * @description: 闪屏页ViewIntent
 */
sealed class SplashViewIntent: ViewIntent() {
    //进入app
    object EnterApp: SplashViewIntent()
}