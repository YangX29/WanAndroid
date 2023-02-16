package com.example.wanandroid.viewmodel.web

import com.example.wanandroid.base.mvi.ViewState

/**
 * @author: Yang
 * @date: 2023/2/12
 * @description: Web页ViewState
 */
sealed class WebViewState : ViewState() {

    //显示文章菜单弹窗
    object ShowArticleMenu : WebViewState()
}