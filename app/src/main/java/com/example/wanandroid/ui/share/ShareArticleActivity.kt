package com.example.wanandroid.ui.share

import android.os.Bundle
import androidx.activity.viewModels
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.example.wanandroid.R
import com.example.wanandroid.base.BaseMVIActivity
import com.example.wanandroid.common.RoutePath
import com.example.wanandroid.databinding.ActivityShareArticleBinding
import com.example.wanandroid.ui.web.WebActivity
import com.example.wanandroid.viewmodel.share.ShareArticleViewIntent
import com.example.wanandroid.viewmodel.share.ShareArticleViewModel
import com.example.wanandroid.viewmodel.share.ShareArticleViewState

/**
 * @author: Yang
 * @date: 2023/3/10
 * @description: 分享文章页面
 */
@Route(path = RoutePath.SHARE)
class ShareArticleActivity :
    BaseMVIActivity<ActivityShareArticleBinding, ShareArticleViewState, ShareArticleViewIntent, ShareArticleViewModel>() {

    override val viewModel: ShareArticleViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
    }

    override fun adaptImmersion() {
    }

    override fun handleViewState(viewState: ShareArticleViewState) {
        when (viewState) {
            is ShareArticleViewState.JumpToLink -> {
                jumpToLink(viewState.link)
            }
            is ShareArticleViewState.RefreshTitle -> {
                refreshTitle(viewState.title)
            }
            is ShareArticleViewState.ShareSuccess -> {
                shareSuccess()
            }
        }
    }

    /**
     * 初始化
     */
    private fun initView() {
        //返回
        mBinding.toolbar.setCommonTitle(R.string.title_share_article) {
            finish()
        }
        //刷新标题
        mBinding.tvRefresh.setOnClickListener { clickRefresh() }
        //打开链接
        mBinding.tvOpen.setOnClickListener { clickOpen() }
        //分享
        mBinding.btShare.setOnClickListener { share() }
    }

    /**
     * 分享
     */
    private fun share() {
        val title = mBinding.etTitle.text.toString()
        val link = mBinding.etLink.text.toString()
        sendIntent(ShareArticleViewIntent.ShareArticle(title, link))
    }

    /**
     * 点击打开链接
     */
    private fun clickOpen() {
        val link = mBinding.etLink.text.toString()
        sendIntent(ShareArticleViewIntent.OpenLink(link))
    }

    /**
     * 点击刷新标题
     */
    private fun clickRefresh() {
        val link = mBinding.etLink.text.toString()
        sendIntent(ShareArticleViewIntent.RefreshTitle(link))
    }

    /**
     * 刷新标题
     */
    private fun refreshTitle(title: String) {
        //更新标题
        mBinding.etTitle.setText(title)
    }

    /**
     * 打开链接
     */
    private fun jumpToLink(link: String) {
        //打开链接
        ARouter.getInstance().build(RoutePath.WEB)
            .withString(WebActivity.WEB_URL, link)
            .navigation()
    }

    /**
     * 分享成功
     */
    private fun shareSuccess() {
        //TODO
        finish()
    }


}