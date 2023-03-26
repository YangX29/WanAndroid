package com.example.wanandroid.ui.share

import androidx.activity.viewModels
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.example.wanandroid.R
import com.example.wanandroid.common.RoutePath
import com.example.wanandroid.ui.article.ArticleListAdapter
import com.example.wanandroid.ui.list.ListPageActivity
import com.example.wanandroid.ui.web.WebActivity
import com.example.wanandroid.viewmodel.article.ArticleListViewState
import com.example.wanandroid.viewmodel.share.ShareListViewModel

/**
 * @author: Yang
 * @date: 2023/3/25
 * @description: 我的分享列表页面
 */
@Route(path = RoutePath.SHARE_LIST)
class MineShareListActivity : ListPageActivity<ArticleListViewState, ShareListViewModel>() {

    override val viewModel: ShareListViewModel by viewModels()

    override val adapter = ArticleListAdapter()

    override fun getPageTitle(): String {
        return getString(R.string.mine_share)
    }

    override fun onLoadMore(viewState: ArticleListViewState) {
        adapter.addData(viewState.articles ?: mutableListOf())
    }

    override fun onRefresh(viewState: ArticleListViewState) {
        adapter.setList(viewState.articles ?: mutableListOf())
    }

    override fun onItemClick(position: Int) {
        val article = adapter.data.getOrNull(position) ?: return
        ARouter.getInstance().build(RoutePath.WEB)
            .withString(WebActivity.WEB_URL, article.link)
            .navigation()
    }
}