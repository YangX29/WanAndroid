package com.example.wanandroid.mvi.share

import com.example.wanandroid.base.mvi.ViewIntent

/**
 * @author: Yang
 * @date: 2023/3/11
 * @description: 分享文章ViewIntent
 */
sealed class ShareArticleViewIntent : ViewIntent() {
    //刷新标题
    data class RefreshTitle(val link: String) : ShareArticleViewIntent()

    //打开链接
    data class OpenLink(val link: String) : ShareArticleViewIntent()

    //分享
    data class ShareArticle(val title: String, val link: String) : ShareArticleViewIntent()

    //检查剪切板内容
    object CheckClipboard : ShareArticleViewIntent()
}