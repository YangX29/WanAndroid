package com.example.wanandroid.ui.article

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.alibaba.android.arouter.launcher.ARouter
import com.example.wanandroid.common.RoutePath
import com.example.wanandroid.ui.web.WebActivity
import com.example.wanandroid.viewmodel.article.ArticleListViewState
import com.example.wanandroid.viewmodel.article.ArticleSubListViewModel

/**
 * @author: Yang
 * @date: 2023/3/17
 * @description: 二级文章列表Fragment
 */
class ArticleSubListFragment : ArticleListFragment<ArticleSubListViewModel>() {

    companion object {

        private const val ARTICLE_CID = "cid"

        //实例化Fragment对象
        fun newInstance(id: Int): ArticleSubListFragment {
            val fragment = ArticleSubListFragment()
            val bundle = Bundle()
            bundle.putInt(ARTICLE_CID, id)
            fragment.arguments = bundle
            return fragment
        }
    }

    override val viewModel: ArticleSubListViewModel by viewModels {
        ArticleSubListViewModel.Factory(cid)
    }

    //二级列表id
    private var cid = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //获取id
        cid = arguments?.getInt(ARTICLE_CID) ?: 0
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