package com.example.wanandroid.ui.web

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.viewModels
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.module_common.utils.extension.invisible
import com.example.module_common.utils.extension.visible
import com.example.module_common.utils.log.logI
import com.example.wanandroid.base.BaseActivity
import com.example.wanandroid.common.RoutePath
import com.example.wanandroid.databinding.ActivityWebBinding
import com.example.wanandroid.utils.extension.adaptImmersionByMargin
import com.example.wanandroid.view.dialog.ArticleMenuDialog
import com.example.wanandroid.view.dialog.MenuType
import com.example.wanandroid.viewmodel.web.WebViewIntent
import com.example.wanandroid.viewmodel.web.WebViewModel
import com.example.wanandroid.viewmodel.web.WebViewState

/**
 * @author: Yang
 * @date: 2023/2/12
 * @description: WebView承载页面
 */
@SuppressLint("SetJavaScriptEnabled")
@Route(path = RoutePath.WEB)
class WebActivity : BaseActivity<ActivityWebBinding, WebViewState, WebViewIntent, WebViewModel>(),
    CustomWebChromeClient.Callback, CustomWebViewClient.Callback, ArticleMenuDialog.Callback {

    companion object {
        const val WEB_URL = "url"
        private const val TAG = "WebActivity"
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

    override fun adaptImmersion() {
        //适配沉浸式
        mBinding.spTop.adaptImmersionByMargin()
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

    override fun handleViewState(viewState: WebViewState) {
        when (viewState) {
            is WebViewState.ShowArticleMenu -> {
                showMenu()
            }
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
        //标题获取焦点，
        mBinding.tvTitle.requestFocus()
        //返回按钮
        mBinding.ivBack.setOnClickListener { onBackPressed() }
        //菜单按钮
        mBinding.ivMenu.setOnClickListener { showMenu() }
    }

    /**
     * 初始化WebView
     */
    private fun initWebView() {
        //初始化设置
        mBinding.webView.apply {
            //启用js
//            settings.javaScriptEnabled = true
//            addJavascriptInterface(JsInterface, "WanAndroid")
            //设置WebViewClient
            webViewClient = CustomWebViewClient(this@WebActivity)
            //设置WebChromeClient
            webChromeClient = CustomWebChromeClient(this@WebActivity)
        }
        //加载网页
        mBinding.webView.loadUrl(url)
    }

    /**
     * 显示菜单
     */
    private fun showMenu() {
        //显示功能菜单弹窗
        val dialog = ArticleMenuDialog(this, this)
        dialog.registerLifecycle(this)
        dialog.show()
    }

    override fun shouldOverrideUrlLoading(url: String): Boolean {
        //TODO 可处理短信，电话等外部应用uri
        return super.shouldOverrideUrlLoading(url)
    }

    override fun onReceivedTitle(title: String?) {
        //网页标题
        mBinding.tvTitle.text = title
    }

    override fun onPageStart(url: String) {
        //加载开始，显示进度条
        mBinding.progressBar.visible()
    }

    override fun onPageFinished(url: String) {
        //加载完成，隐藏进度条
        mBinding.progressBar.invisible()
    }

    override fun onProgressChanged(progress: Int) {
        //进度条更新
        mBinding.progressBar.update(progress)
    }

    override fun clickMenu(type: MenuType) {
        //TODO 菜单点击事件处理
        logI(TAG, "click menu: $type")
    }

    override fun hasFavored(): Boolean {
        //TODO 是否已收藏
        return false
    }

    override fun isDark(): Boolean {
        //TODO 是否为黑夜模式
        return false
    }

}