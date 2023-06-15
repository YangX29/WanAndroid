package com.example.wanandroid.mvi.share

import com.example.module_common.utils.ime.ClipboardUtils
import com.example.wanandroid.R
import com.example.wanandroid.base.BaseViewModel
import com.example.wanandroid.base.mvi.ViewEvent
import com.example.wanandroid.utils.extension.executeCall
import com.example.wanandroid.utils.extension.launchByIo
import com.example.wanandroid.utils.net.UrlUtils

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

            is ShareArticleViewIntent.CheckClipboard -> {
                checkClipboard()
            }
        }
    }

    /**
     * 分享文章
     */
    private fun shareArticle(title: String, link: String) {
        //判断标题和链接格式
        if (title.isEmpty() || link.isEmpty()) {
            emitViewEvent(ViewEvent.Toast(R.string.toast_share_not_empty_hint))
            return
        }
        executeCall({ apiService.shareArticle(title, link) }, {
            updateViewState(ShareArticleViewState.ShareSuccess)
        }, {
            emitViewEvent(ViewEvent.Toast(R.string.toast_shared_failed))
        }, true)
    }

    /**
     * 更新标题
     */
    private fun refreshTitle(link: String) {
        //loading
        emitViewEvent(ViewEvent.Loading(true))
        launchByIo {
            //根据url获取网页标题
            val title = UrlUtils.getTitleFromUrl(link)
            if (title != null) {
                //刷新标题
                updateViewState(ShareArticleViewState.RefreshTitle(title))
            } else {
                emitViewEvent(ViewEvent.Toast(R.string.toast_get_title_failed))
            }
            //loading
            emitViewEvent(ViewEvent.Loading(false))
        }
    }

    /**
     * 打开链接
     */
    private fun openLink(link: String) {
        //校验url正确性
        if (UrlUtils.checkUrl(link)) {
            //跳转到网页
            updateViewState(ShareArticleViewState.JumpToLink(link))
        } else {
            emitViewEvent(ViewEvent.Toast(R.string.toast_url_check_failed))
        }
    }

    /**
     * 检查剪切板
     */
    private fun checkClipboard() {
        //获取剪切板文本
        ClipboardUtils.getTextCoerced()?.let {
            val isUrl = UrlUtils.checkUrl(it.toString())
            updateViewState(ShareArticleViewState.ShowClipboardContent(it.toString(), isUrl))
        }
    }

}