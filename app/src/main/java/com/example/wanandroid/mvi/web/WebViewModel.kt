package com.example.wanandroid.mvi.web

import com.example.wanandroid.base.BaseViewModel

/**
 * @author: Yang
 * @date: 2023/2/12
 * @description: Web页ViewModel
 */
class WebViewModel : BaseViewModel<WebViewState, WebViewIntent>() {

    override fun handleIntent(viewIntent: WebViewIntent) {
        when(viewIntent) {
            is WebViewIntent.ClickMenu -> {
                clickMenu()
            }
        }
    }

    /**
     * 点击菜单
     */
    private fun clickMenu() {
        //TODO 判断是否为广告页
        updateViewState(WebViewState.ShowArticleMenu)
    }

}