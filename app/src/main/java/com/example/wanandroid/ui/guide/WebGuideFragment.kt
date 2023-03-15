package com.example.wanandroid.ui.guide

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.alibaba.android.arouter.launcher.ARouter
import com.example.wanandroid.common.RoutePath
import com.example.wanandroid.model.WebCategory
import com.example.wanandroid.ui.list.ListPageFragment
import com.example.wanandroid.ui.web.WebActivity
import com.example.wanandroid.viewmodel.guide.web.WebGuideViewModel
import com.example.wanandroid.viewmodel.guide.web.WebGuideViewState

/**
 * @author: Yang
 * @date: 2023/3/14
 * @description: 网页导航页面
 */
class WebGuideFragment : ListPageFragment<WebGuideViewState, WebGuideViewModel>() {

    override val viewModel: WebGuideViewModel by viewModels()

    override val adapter = TagCategoryListAdapter<WebCategory>()

    override fun showDivider() = false

    override fun canLoadMore() = false

    override fun onRefresh(viewState: WebGuideViewState) {
        adapter.setNewInstance(viewState.articles ?: mutableListOf())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //设置标签点击事件
        adapter.setOnTagClick { webCategory, i ->
            val url = webCategory.articles[i].link
            ARouter.getInstance().build(RoutePath.WEB)
                .withString(WebActivity.WEB_URL, url)
                .navigation()
        }
    }

}