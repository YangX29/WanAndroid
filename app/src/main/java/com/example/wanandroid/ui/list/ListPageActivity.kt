package com.example.wanandroid.ui.list

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.example.module_common.utils.extension.invisible
import com.example.module_common.utils.extension.visible
import com.example.wanandroid.R
import com.example.wanandroid.base.BaseMVIActivity
import com.example.wanandroid.databinding.ActivityListPageBinding
import com.example.wanandroid.view.widget.CustomLoadMoreView
import com.example.wanandroid.view.widget.SimpleDividerItemDecoration
import com.example.wanandroid.mvi.list.ListPageViewIntent
import com.example.wanandroid.mvi.list.ListPageViewModel
import com.example.wanandroid.mvi.list.ListPageViewState
import com.example.wanandroid.mvi.list.ListPageViewStatus

/**
 * @author: Yang
 * @date: 2023/3/25
 * @description: 通用列表Activity
 */
abstract class ListPageActivity<VS : ListPageViewState, VM : ListPageViewModel<VS, ListPageViewIntent>> :
    BaseMVIActivity<ActivityListPageBinding, VS, ListPageViewIntent, VM>() {

    protected abstract val adapter: BaseQuickAdapter<*, *>

    private inner class ScrollListener : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val position = layoutManager.findFirstVisibleItemPosition()
            if (newState == RecyclerView.SCROLL_STATE_IDLE && position >= 5) {
                mBinding.fabTop.show()
            } else {
                mBinding.fabTop.hide()
            }
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //初始化
        initView()
        //初始化数据
        initData()
    }

    override fun handleViewState(viewState: VS) {
        when (val status = viewState.status) {
            is ListPageViewStatus.RefreshFinish -> {
                //更新数据
                refreshFinish(viewState)
            }
            is ListPageViewStatus.LoadMoreFinish -> {
                //修改加载状态
                if (status.finish) {
                    adapter.loadMoreModule.loadMoreEnd()
                } else {
                    adapter.loadMoreModule.loadMoreComplete()
                }
                //更新数据
                onLoadMore(viewState)
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
        //开始loading
        mBinding.loading.visible()
        mBinding.rv.invisible()
        //初始化数据
        refresh(true)
    }

    /**
     * 初始化View
     */
    private fun initView() {
        //toolbar
        mBinding.toolbar.setCommonTitle(getPageTitle()) {
            finish()
        }
        //列表
        mBinding.rv.apply {
            adapter = this@ListPageActivity.adapter
            layoutManager = LinearLayoutManager(context)
            if (showDivider() && itemDecorationCount <= 0) {
                addItemDecoration(SimpleDividerItemDecoration(context))
            }
        }
        adapter.setOnItemClickListener { _, _, position ->
            onItemClick(position)
        }
        //空布局
        adapter.setEmptyView(R.layout.layout_empty_list)
        //上拉加载更多
        if (adapter is LoadMoreModule && canLoadMore()) {
            adapter.loadMoreModule.apply {
                //加载更多
                setOnLoadMoreListener { loadMore() }
                //不自动加载更多
//                isAutoLoadMore = false
                //未满一页不自动加载
//                isEnableLoadMoreIfNotFullPage = false
                //自定义加载更多布局
                loadMoreView = CustomLoadMoreView()
            }
        }
        //下拉刷新
        mBinding.swipeRefresh.isEnabled = canRefresh()
        mBinding.swipeRefresh.setColorSchemeResources(R.color.common_refresh_scheme)
        mBinding.swipeRefresh.setOnRefreshListener {
            refresh(false)
        }
        //返回顶部按钮
        if (showTopButton()) {
            mBinding.fabTop.visible()
            mBinding.fabTop.hide()
            //监听列表滚动
            mBinding.rv.addOnScrollListener(ScrollListener())
        }
        mBinding.fabTop.setOnClickListener { backTop() }
    }

    /**
     * 返回列表顶部
     */
    private fun backTop() {
        //隐藏按钮
        mBinding.fabTop.hide()
        //返回顶部
        mBinding.rv.smoothScrollToPosition(0)
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
    private fun refreshFinish(viewState: VS) {
        //停止loading
        mBinding.loading.invisible()
        mBinding.rv.visible()
        mBinding.swipeRefresh.isRefreshing = false
        //回调
        onRefresh(viewState)
    }

    /**
     * 获取标题
     */
    abstract fun getPageTitle(): String

    /**
     * 是否显示返回顶部按钮，默认显示
     */
    open fun showTopButton() = true

    /**
     * 是否可以加载更多，默认为true
     */
    open fun canLoadMore() = true

    /**
     * 是否可以刷新，默认为true
     */
    open fun canRefresh() = true

    /**
     * 是否显示分割线
     */
    open fun showDivider() = true

    /**
     * 刷新回调
     */
    open fun onRefresh(viewState: VS) {}

    /**
     * 加载更多回调
     */
    open fun onLoadMore(viewState: VS) {}

    /**
     * 列表item点击事件
     */
    open fun onItemClick(position: Int) {}

}