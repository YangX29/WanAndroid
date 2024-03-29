package com.example.wanandroid.ui.share

import android.os.Bundle
import androidx.activity.viewModels
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.example.wanandroid.R
import com.example.wanandroid.base.BaseMVIActivity
import com.example.wanandroid.common.RoutePath
import com.example.wanandroid.databinding.ActivityShareArticleBinding
import com.example.wanandroid.mvi.share.ShareArticleViewIntent
import com.example.wanandroid.mvi.share.ShareArticleViewModel
import com.example.wanandroid.mvi.share.ShareArticleViewState
import com.example.wanandroid.ui.web.WebActivity
import com.example.wanandroid.utils.view.ClipboardTip

/**
 * @author: Yang
 * @date: 2023/3/10
 * @description: 分享文章页面
 */
@Route(path = RoutePath.SHARE)
class ShareArticleActivity :
    BaseMVIActivity<ActivityShareArticleBinding, ShareArticleViewState, ShareArticleViewIntent, ShareArticleViewModel>() {

    companion object {
        const val KEY_FROM_MINE_SHARE = "from_mine_share"
    }

    @Autowired(name = KEY_FROM_MINE_SHARE)
    @JvmField
    var fromMineShare: Boolean = false

    override val viewModel: ShareArticleViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
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

            is ShareArticleViewState.ShowClipboardContent -> {
                showClipboardContent(viewState.content, viewState.isUrl)
            }
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        //检查剪切板内容，获取焦点后再获取，否则拿不到剪切板内容
        if (hasFocus) {
            sendIntent(ShareArticleViewIntent.CheckClipboard)
        }
    }

    /**
     * 初始化
     */
    private fun initView() {
        //toolbar
        mBinding.toolbar.apply {
            setOnLeftClick { finish() }
            setOnRightClick { jumpToShareList() }
            //从我的分享进入隐藏入口
            showRightButton(!fromMineShare)
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
     * 跳转到我的分享列表
     */
    private fun jumpToShareList() {
        ARouter.getInstance().build(RoutePath.SHARE_LIST)
            .withBoolean(MineShareListActivity.KEY_FROM_SHARE_PAGE, true)
            .navigation()
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
        finish()
    }

    /**
     * 显示剪切板内容
     */
    private fun showClipboardContent(content: String, url: Boolean) {
        ClipboardTip.showTipView(
            this,
            content,
            getString(if (url) R.string.share_copy_to_link else R.string.share_copy_to_title)
        ) {
            if (url) {
                mBinding.etLink.setText(content)
            } else {
                mBinding.etTitle.setText(content)
            }
        }
    }

}