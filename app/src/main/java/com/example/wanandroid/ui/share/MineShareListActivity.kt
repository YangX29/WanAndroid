package com.example.wanandroid.ui.share

import androidx.activity.viewModels
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.example.wanandroid.R
import com.example.wanandroid.common.RoutePath
import com.example.wanandroid.mvi.share.ShareListViewModel
import com.example.wanandroid.ui.article.ArticleListActivity
import com.example.wanandroid.utils.user.LoginInterceptor

/**
 * @author: Yang
 * @date: 2023/3/25
 * @description: 我的分享列表页面
 */
@Route(path = RoutePath.SHARE_LIST, extras = LoginInterceptor.INTERCEPTOR_PAGE)
class MineShareListActivity : ArticleListActivity<ShareListViewModel>() {

    companion object {
        const val KEY_FROM_SHARE_PAGE = "from_share_page"
    }

    @Autowired(name = KEY_FROM_SHARE_PAGE)
    @JvmField
    var fromSharePage: Boolean = false

    override val viewModel: ShareListViewModel by viewModels()

    override fun getPageTitle(): String {
        return getString(R.string.mine_share)
    }

    override fun getRightMenu(): Int {
        return if (fromSharePage) -1 else R.drawable.icon_add
    }

    override fun menuClick() {
        ARouter.getInstance().build(RoutePath.SHARE)
            .withBoolean(ShareArticleActivity.KEY_FROM_MINE_SHARE, true)
            .navigation()
    }

}