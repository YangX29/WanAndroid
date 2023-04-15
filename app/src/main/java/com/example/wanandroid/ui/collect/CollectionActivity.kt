package com.example.wanandroid.ui.collect

import androidx.activity.viewModels
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.wanandroid.R
import com.example.wanandroid.common.RoutePath
import com.example.wanandroid.mvi.collect.CollectionViewModel
import com.example.wanandroid.ui.article.ArticleListActivity
import com.example.wanandroid.utils.user.LoginInterceptor

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

}