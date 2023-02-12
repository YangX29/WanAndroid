package com.example.wanandroid.ui.web

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.viewModels
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.wanandroid.base.BaseActivity
import com.example.wanandroid.common.RoutePath
import com.example.wanandroid.databinding.ActivityWebBinding
import com.example.wanandroid.viewmodel.web.WebViewIntent
import com.example.wanandroid.viewmodel.web.WebViewModel
import com.example.wanandroid.viewmodel.web.WebViewState
import com.tencent.smtt.sdk.WebViewClient

/**
 * @author: Yang
 * @date: 2023/2/12
 * @description: WebView承载页面
 */
@SuppressLint("SetJavaScriptEnabled")
@Route(path = RoutePath.WEB)
class WebActivity : BaseActivity<ActivityWebBinding, WebViewState, WebViewIntent, WebViewModel>() {

    companion object {
        const val WEB_URL = "url"
    }

    private var url: String? = null

    override val viewModel: WebViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //初始化数据
        initData()
        //初始化
        initView()
    }

    override fun onBackPressed() {
        //是否可以返回网页上一级
        if (mBinding.webView.canGoBack()) {
            //返回上一级
            mBinding.webView.goBack()
        } else {
            super.onBackPressed()
        }
    }

    /**
     * 初始化数据
     */
    private fun initData() {
        url = intent.getStringExtra(WEB_URL)
    }

    /**
     * 初始化
     */
    private fun initView() {
        //初始webView
        initWebView()
        //加载网页
        mBinding.webView.loadUrl(url)
    }

    /**
     * 初始化WebView
     */
    private fun initWebView() {
        mBinding.webView.apply {
            //启用js
//            settings.javaScriptEnabled = true
//            addJavascriptInterface(JsInterface, "WanAndroid")
            //设置WebViewClient
            webViewClient = CustomWebViewClient()
            //设置WebChromeClient
            webChromeClient = CustomWebChromeClient()
        }
    }

}