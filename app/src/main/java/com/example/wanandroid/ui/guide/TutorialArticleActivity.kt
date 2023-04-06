package com.example.wanandroid.ui.guide

import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.example.module_common.utils.extension.visible
import com.example.wanandroid.R
import com.example.wanandroid.base.BaseMVIActivity
import com.example.wanandroid.common.RoutePath
import com.example.wanandroid.databinding.ActivityTutorialArticleBinding
import com.example.wanandroid.databinding.ViewCommonToolBarBinding
import com.example.wanandroid.model.Article
import com.example.wanandroid.model.Category
import com.example.wanandroid.ui.guide.adapter.TutorialArticleAdapter
import com.example.wanandroid.ui.web.WebActivity
import com.example.wanandroid.utils.extension.loadWithDefault
import com.example.wanandroid.view.widget.helper.AppbarSateChangeListener
import com.example.wanandroid.view.widget.CustomLoadMoreView
import com.example.wanandroid.view.widget.SimpleDividerItemDecoration
import com.example.wanandroid.mvi.article.ArticleListViewState
import com.example.wanandroid.mvi.article.ArticleSubListViewModel
import com.example.wanandroid.mvi.list.ListPageViewIntent
import com.example.wanandroid.mvi.list.ListPageViewStatus
import com.google.android.material.appbar.AppBarLayout

/**
 * @author: Yang
 * @date: 2023/3/19
 * @description: 课程文章列表页面
 */
@Route(path = RoutePath.TUTORIAL_ARTICLE)
class TutorialArticleActivity :
    BaseMVIActivity<ActivityTutorialArticleBinding, ArticleListViewState, ListPageViewIntent, ArticleSubListViewModel>() {

    companion object {
        const val KEY_TUTORIAL = "key_tutorial"
    }

    override val viewModel: ArticleSubListViewModel by viewModels {
        ArticleSubListViewModel.Factory(tutorial?.id ?: 0, 1)
    }

    @Autowired(name = KEY_TUTORIAL)
    @JvmField
    var tutorial: Category? = null

    //列表adapter
    private val adapter = TutorialArticleAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //初始化
        initView()
        //初始化数据
        initData()
    }

    override fun handleViewState(viewState: ArticleListViewState) {
        when (val status = viewState.status) {
            is ListPageViewStatus.RefreshFinish -> {
                //更新数据
                refreshFinish(viewState.articles ?: mutableListOf())
            }
            is ListPageViewStatus.LoadMoreFinish -> {
                //修改加载状态
                if (status.finish) {
                    adapter.loadMoreModule.loadMoreEnd()
                } else {
                    adapter.loadMoreModule.loadMoreComplete()
                }
                //更新数据
                adapter.addData(viewState.articles ?: mutableListOf())
            }
            is ListPageViewStatus.LoadMoreFailed -> {
                //加载失败
                adapter.loadMoreModule.loadMoreFail()
            }
            is ListPageViewStatus.RefreshFailed -> {
                //TODO
            }
        }
    }

    /**
     * 初始化数据
     */
    private fun initData() {
        //初始化数据
        refresh(true)
    }

    /**
     * 初始化View
     */
    private fun initView() {
        //课程信息
        mBinding.ivCover.loadWithDefault(tutorial?.cover ?: "")
        mBinding.tvName.text = tutorial?.name
        mBinding.tvAuthor.text = tutorial?.author
        mBinding.tvDesc.text = tutorial?.desc
        mBinding.tvLicense.text = tutorial?.lisense
        mBinding.tvLicense.setOnClickListener { jumpToLicense() }
        //标题
        mBinding.toolbar.setBar<ViewCommonToolBarBinding> {
            tvTitle.text = tutorial?.name
            ivBack.setOnClickListener { finish() }

        }
        mBinding.appbar.addOnOffsetChangedListener(object : AppbarSateChangeListener() {
            override fun onStateChanged(appBarLayout: AppBarLayout, state: State) {
                mBinding.toolbar.setBar<ViewCommonToolBarBinding> {
                    tvTitle.visible(state == State.COLLAPSED)
                }
            }
        })
        //列表
        mBinding.rvArticle.apply {
            adapter = this@TutorialArticleActivity.adapter
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(SimpleDividerItemDecoration(context))
        }
        adapter.setOnItemClickListener { _, _, position ->
            onItemClick(position)
        }
        //空布局
        adapter.setEmptyView(R.layout.layout_empty_list)
        //上拉加载更多
        adapter.loadMoreModule.apply {
            //加载更多
            setOnLoadMoreListener { loadMore() }
            //自定义加载更多布局
            loadMoreView = CustomLoadMoreView()
        }
        //TODO 下拉刷新
//        mBinding.swipeRefresh.setColorSchemeResources(R.color.common_refresh_scheme)
//        mBinding.swipeRefresh.setOnRefreshListener {
//            refresh(false)
//        }
    }

    /**
     * 页面刷新
     */
    private fun refresh(isInit: Boolean) {
        sendIntent(ListPageViewIntent.Refresh(isInit))
    }

    /**
     * 下拉加载更多
     */
    private fun loadMore() {
        sendIntent(ListPageViewIntent.LoadMore)
    }

    /**
     * 刷新结束
     */
    private fun refreshFinish(list: MutableList<Article>) {
        //停止loading
//        mBinding.swipeRefresh.isRefreshing = false
        //回调
        adapter.setList(list)
    }

    /**
     * 点击item
     */
    private fun onItemClick(position: Int) {
        val article = adapter.data.getOrNull(position) ?: return
        ARouter.getInstance().build(RoutePath.WEB)
            .withString(WebActivity.WEB_URL, article.link)
            .navigation()
    }

    /**
     * 跳转到证书
     */
    private fun jumpToLicense() {
        ARouter.getInstance().build(RoutePath.WEB)
            .withString(WebActivity.WEB_URL, tutorial?.lisenseLink)
            .navigation()
    }

}