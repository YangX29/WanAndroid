package com.example.wanandroid.ui.user

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.example.module_common.utils.extension.invisible
import com.example.module_common.utils.extension.visible
import com.example.wanandroid.R
import com.example.wanandroid.base.BaseMVIActivity
import com.example.wanandroid.common.LiveEventKey
import com.example.wanandroid.common.RoutePath
import com.example.wanandroid.databinding.ActivityUserPageBinding
import com.example.wanandroid.model.Article
import com.example.wanandroid.mvi.list.ListPageViewIntent
import com.example.wanandroid.mvi.list.ListPageViewStatus
import com.example.wanandroid.mvi.user.UserPageViewModel
import com.example.wanandroid.mvi.user.UserPageViewState
import com.example.wanandroid.ui.article.ArticleListAdapter
import com.example.wanandroid.ui.web.WebActivity
import com.example.wanandroid.view.widget.CustomLoadMoreView
import com.example.wanandroid.view.widget.SimpleDividerItemDecoration
import com.example.wanandroid.view.widget.helper.AppbarSateChangeListener
import com.google.android.material.appbar.AppBarLayout
import com.jeremyliao.liveeventbus.LiveEventBus

/**
 * @author: Yang
 * @date: 2023/4/19
 * @description: 用户主页页面
 */
@Route(path = RoutePath.USER_PAGE)
class UserPageActivity :
    BaseMVIActivity<ActivityUserPageBinding, UserPageViewState, ListPageViewIntent, UserPageViewModel>() {

    companion object {
        const val KEY_USER_ID = "user_id"
    }

    override val viewModel: UserPageViewModel by viewModels {
        UserPageViewModel.Factory(userId)
    }

    @Autowired(name = KEY_USER_ID)
    @JvmField
    var userId: Long = 0L

    //列表adapter
    private val adapter = ArticleListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //初始化
        initView()
        //初始化数据
        initData()
        //监听LiveEvent
        observeLiveEvent()
    }

    override fun handleViewState(viewState: UserPageViewState) {
        when (val status = viewState.status) {
            is ListPageViewStatus.RefreshFinish -> {
                //更新数据
                refreshFinish(viewState)
                updateLoadMore(status.finish)
            }

            is ListPageViewStatus.LoadMoreFinish -> {
                //更新数据
                adapter.addData(viewState.data ?: mutableListOf())
                updateLoadMore(status.finish)
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
        refresh()
    }

    /**
     * 初始化View
     */
    private fun initView() {
        //标题
        mBinding.toolbar.setOnLeftClick { finish() }
        mBinding.appbar.addOnOffsetChangedListener(object : AppbarSateChangeListener() {
            override fun onStateChanged(appBarLayout: AppBarLayout, state: State) {
                mBinding.toolbar.showTitle(state == State.COLLAPSED)
            }
        })
        //列表
        mBinding.rvArticle.apply {
            adapter = this@UserPageActivity.adapter
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(SimpleDividerItemDecoration(context))
        }
        adapter.setOnItemClickListener { _, _, position ->
            onItemClick(position)
        }
        adapter.setOnItemChildClickListener { _, view, position ->
            onItemChildClick(view, position)
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
    }

    /**
     * 页面刷新
     */
    private fun refresh() {
        sendIntent(ListPageViewIntent.Refresh(true))
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
    private fun refreshFinish(viewState: UserPageViewState) {
        mBinding.loading.invisible()
        mBinding.rvArticle.visible()
        //更新用户信息
        viewState.coinInfo?.apply {
            //标题
            mBinding.toolbar.setTitle(username)
            //等级
            mBinding.header.setUserPage(this)
        }
        //回调
        adapter.setNewInstance(viewState.data ?: mutableListOf())
    }

    /**
     * 更新加载更多状态
     */
    private fun updateLoadMore(finish: Boolean) {
        //修改加载状态
        if (finish) {
            adapter.loadMoreModule.loadMoreEnd()
        } else {
            adapter.loadMoreModule.loadMoreComplete()
        }
    }

    /**
     * 点击item
     */
    private fun onItemClick(position: Int) {
        val article = adapter.data.getOrNull(position) ?: return
        ARouter.getInstance().build(RoutePath.WEB).withString(WebActivity.WEB_URL, article.link)
            .navigation()
    }


    /**
     * 子View点击事件
     */
    private fun onItemChildClick(view: View, position: Int) {
        val article = adapter.data.getOrNull(position) ?: return
        when (view.id) {
            R.id.ivCollect -> {
                clickCollect(article)
            }
        }
    }

    /**
     * 监听LiveEvent
     */
    private fun observeLiveEvent() {
        //收藏
        LiveEventBus.get<Long>(LiveEventKey.KEY_COLLECT_ARTICLE).observe(this) {
            updateArticle(it) { collect = true }
        }
        //取消收藏
        LiveEventBus.get<Long>(LiveEventKey.KEY_UNCOLLECT_ARTICLE).observe(this) {
            updateArticle(it) { collect = false }
        }
    }

    /**
     * 点击收藏
     */
    private fun clickCollect(article: Article) {
        article.run {
            viewModel.collectArticle(!collect, id)
        }
    }

    /**
     * 更新文章
     */
    private fun updateArticle(
        id: Long,
        playLoad: String = ArticleListAdapter.PAYLOAD_COLLECT,
        block: Article.() -> Unit
    ) {
        adapter.data.find { article -> article.id == id }?.run {
            //获取index
            val index = adapter.data.indexOf(this)
            //更新article
            block.invoke(this)
            //更新列表
            adapter.notifyItemChanged(index + adapter.headerLayoutCount, playLoad)
        }
    }


}