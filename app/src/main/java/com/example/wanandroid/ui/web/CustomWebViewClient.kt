package com.example.wanandroid.ui.web

import android.graphics.Bitmap
import com.example.module_common.utils.log.logI
import com.tencent.smtt.export.external.interfaces.SslError
import com.tencent.smtt.export.external.interfaces.SslErrorHandler
import com.tencent.smtt.export.external.interfaces.WebResourceError
import com.tencent.smtt.export.external.interfaces.WebResourceRequest
import com.tencent.smtt.sdk.WebView
import com.tencent.smtt.sdk.WebViewClient

/**
 * @author: Yang
 * @date: 2023/2/12
 * @description: 本地WebViewClient
 */
class CustomWebViewClient(private val callback: Callback) : WebViewClient() {

    companion object {
        private const val TAG = "CustomWebViewClient"
    }

    override fun shouldOverrideUrlLoading(view: WebView?, url: String): Boolean {
        logI(TAG, "shouldOverrideUrlLoading: $url")
        return callback.shouldOverrideUrlLoading(url)
    }

    override fun onPageStarted(view: WebView?, url: String, bitmap: Bitmap?) {
        logI(TAG, "onPageStarted: $url")
        //回调
        callback.onPageStart(url)
        super.onPageStarted(view, url, bitmap)
    }

    override fun onPageFinished(view: WebView?, url: String) {
        logI(TAG, "onPageFinished: $url")
        //回调
        callback.onPageFinished(url)
        super.onPageFinished(view, url)
    }

    override fun onReceivedSslError(view: WebView?, handler: SslErrorHandler?, error: SslError?) {
        //回调
        callback.onReceivedSslError(handler, error)
        super.onReceivedSslError(view, handler, error)
    }

    override fun onReceivedError(view: WebView?, code: Int, des: String?, url: String) {
        //回调
        callback.onReceivedError(code, des ?: "", url)
        super.onReceivedError(view, code, des, url)
    }

    override fun onReceivedError(
        view: WebView?,
        request: WebResourceRequest?,
        error: WebResourceError?
    ) {
        //回调
        callback.onReceivedError(request, error)
        super.onReceivedError(view, request, error)
    }

    override fun onLoadResource(view: WebView?, url: String?) {
        super.onLoadResource(view, url)
    }


    /**
     * WebViewClient回调
     */
    interface Callback {
        /**
         * 是否拦截url
         */
        fun shouldOverrideUrlLoading(url: String): Boolean = false

        /**
         * 开始加载网页
         */
        fun onPageStart(url: String) {}

        /**
         * 网页加载结束
         */
        fun onPageFinished(url: String) {}

        /**
         * ssl错误
         */
        fun onReceivedSslError(handler: SslErrorHandler?, error: SslError?) {}

        /**
         * 错误处理， API23之前
         */
        fun onReceivedError(code: Int, des: String, url: String) {}

        /**
         * 错误处理， API23之后
         */
        fun onReceivedError(request: WebResourceRequest?, error: WebResourceError?) {}

    }

}