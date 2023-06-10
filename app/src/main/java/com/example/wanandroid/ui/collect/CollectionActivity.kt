package com.example.wanandroid.ui.collect

import androidx.activity.viewModels
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.wanandroid.R
import com.example.wanandroid.common.LiveEventKey
import com.example.wanandroid.common.RoutePath
import com.example.wanandroid.model.Article
import com.example.wanandroid.mvi.collect.CollectionViewModel
import com.example.wanandroid.ui.article.ArticleListActivity
import com.example.wanandroid.utils.user.LoginInterceptor
import com.jeremyliao.liveeventbus.LiveEventBus

/**
 * @author: Yang
 * @date: 2023/4/15
 * @description: 我的收藏页面
 */
@Route(path = RoutePath.COLLECTION_LIST, extras = LoginInterceptor.INTERCEPTOR_PAGE)
class CollectionActivity : ArticleListActivity<CollectionViewModel>() {

    override val viewModel: CollectionViewModel by viewModels()

    override fun getPageTitle(): String {
        return getString(R.string.mine_collection)
    }

    override fun getRightMenu(): Int {
        return R.drawable.icon_add
    }

    override fun menuClick() {
        //TODO 添加站外收藏编辑页面
    }

    override fun observeLiveEvent() {
        //取消收藏
        LiveEventBus.get<Long>(LiveEventKey.KEY_UNCOLLECT_ARTICLE).observe(this) {
            //取消收藏移除item
            val item = adapter.data.find { article -> article.id == it } ?: return@observe
            val index = adapter.data.indexOf(item)
            adapter.removeAt(index)
        }
    }

    override fun clickCollect(article: Article) {
        article.run {
            viewModel.uncollectMine(id, originId ?: -1L)
        }
    }

}