package com.example.wanandroid.mvi.web

import com.example.wanandroid.base.mvi.ViewIntent

/**
 * @author: Yang
 * @date: 2023/2/12
 * @description: web页ViewIntent
 */
sealed class WebViewIntent : ViewIntent() {
    //点击菜单按钮
    object ClickMenu : WebViewIntent()
}