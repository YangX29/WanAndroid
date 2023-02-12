package com.example.wanandroid.ui.web

import com.example.module_common.utils.log.logI
import com.tencent.smtt.sdk.WebView
import com.tencent.smtt.sdk.WebViewClient

/**
 * @author: Yang
 * @date: 2023/2/12
 * @description: 本地WebViewClient
 */
class CustomWebViewClient : WebViewClient() {

    companion object {
        private const val TAG = "CustomWebViewClient"
    }

    override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
        logI(TAG, "shouldOverrideUrlLoading: $url")
        return super.shouldOverrideUrlLoading(view, url)
    }

}