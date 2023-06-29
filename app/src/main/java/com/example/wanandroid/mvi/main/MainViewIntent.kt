package com.example.wanandroid.mvi.main

import com.example.wanandroid.base.mvi.ViewIntent

/**
 * @author: Yang
 * @date: 2023/2/5
 * @description: 主页ViewIntent
 */
sealed class MainViewIntent : ViewIntent() {
    //检查登录状态
    object CheckLoginState : MainViewIntent()
}