package com.example.wanandroid.ui.web

import com.example.module_common.utils.log.logI
import com.tencent.smtt.sdk.WebChromeClient
import com.tencent.smtt.sdk.WebView

/**
 * @author: Yang
 * @date: 2023/2/12
 * @description: 本地WebChromeClient
 */
class CustomWebChromeClient(private val callback: Callback) : WebChromeClient() {

    companion object {
        private const val TAG = "CustomWebChromeClient"
    }

    override fun onProgressChanged(view: WebView?, progress: Int) {
        logI(TAG, "onProgressChanged: $progress")
        //回调
        callback.onProgressChanged(progress)
        super.onProgressChanged(view, progress)
    }

    override fun onReceivedTitle(view: WebView?, title: String?) {
        logI(TAG, "onReceivedTitle: $title")
        //回调
        callback.onReceivedTitle(title)
        super.onReceivedTitle(view, title)
    }

    /**
     * WebChromeClient回调
     */
    interface Callback {
        /**
         * 网页加载进度回调
         */
        fun onProgressChanged(progress: Int) {}

        /**
         * 网页标题
         */
        fun onReceivedTitle(title: String?) {}

    }

}