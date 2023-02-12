package com.example.wanandroid.ui.web

import com.example.module_common.utils.log.logI
import com.tencent.smtt.sdk.WebChromeClient
import com.tencent.smtt.sdk.WebView

/**
 * @author: Yang
 * @date: 2023/2/12
 * @description: 本地WebChromeClient
 */
class CustomWebChromeClient : WebChromeClient() {

    companion object {
        private const val TAG = "CustomWebChromeClient"
    }

    override fun onProgressChanged(view: WebView?, progress: Int) {
        //TODO 加载进度回调
        logI(TAG, "onProgressChanged: $progress")
        super.onProgressChanged(view, progress)
    }

    override fun onReceivedTitle(view: WebView?, title: String?) {
        logI(TAG, "onReceivedTitle: $title")
        super.onReceivedTitle(view, title)
    }

}