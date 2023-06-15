package com.example.wanandroid.mvi.share

import com.example.wanandroid.base.mvi.ViewState

/**
 * @author: Yang
 * @date: 2023/3/11
 * @description: 分享文章ViewState
 */
sealed class ShareArticleViewState : ViewState() {
    //显示剪切板内容
    data class ShowClipboardContent(val content: String, val isUrl: Boolean) : ShareArticleViewState()

    //打开链接
    data class JumpToLink(val link: String) : ShareArticleViewState()

    //刷新标题
    data class RefreshTitle(val title: String) : ShareArticleViewState()

    //分享成功
    object ShareSuccess : ShareArticleViewState()
}