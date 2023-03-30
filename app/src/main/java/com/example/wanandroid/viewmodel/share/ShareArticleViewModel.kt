package com.example.wanandroid.viewmodel.share

import com.example.wanandroid.base.BaseViewModel
import com.example.wanandroid.base.mvi.ViewEvent
import com.example.wanandroid.utils.extension.executeCall

/**
 * @author: Yang
 * @date: 2023/3/11
 * @description: 分享文章ViewModel
 */
class ShareArticleViewModel : BaseViewModel<ShareArticleViewState, ShareArticleViewIntent>() {

    override fun handleIntent(viewIntent: ShareArticleViewIntent) {
        when (viewIntent) {
            is ShareArticleViewIntent.RefreshTitle -> {
                refreshTitle(viewIntent.link)
            }
            is ShareArticleViewIntent.OpenLink -> {
                openLink(viewIntent.link)
            }
            is ShareArticleViewIntent.ShareArticle -> {
                shareArticle(viewIntent.title, viewIntent.link)
            }
        }
    }

    /**
     * 分享文章
     */
    private fun shareArticle(title: String, link: String) {
        //TODO 判断标题和链接格式
        executeCall({ apiService.shareArticle(title, link) }, {
            //TODO 是否需要通知刷新列表，登录状态处理
            updateViewState(ShareArticleViewState.ShareSuccess)
        }, {
            //TODO
            emitViewEvent(ViewEvent.Toast("分享失败"))
        })
    }

    /**
     * 更新标题
     */
    private fun refreshTitle(link: String) {
        //TODO 根据url获取网页标题
    }

    /**
     *
     */
    private fun openLink(link: String) {
        //TODO 校验url正确性
    }

}