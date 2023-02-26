package com.example.wanandroid.ui.list

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.example.module_common.utils.extension.invisible
import com.example.module_common.utils.extension.visible
import com.example.wanandroid.R
import com.example.wanandroid.base.BaseMVIFragment
import com.example.wanandroid.databinding.FragmentListPageBinding

/**
 * @author: Yang
 * @date: 2023/2/25
 * @description: 通用列表Fragment基类
 */
abstract class ListPageFragment<VS : ListPageViewState, VM : ListPageViewModel<VS>> :
    BaseMVIFragment<FragmentListPageBinding, VS, ListPageViewIntent, VM>() {

    protected abstract val adapter: BaseQuickAdapter<*, *>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //初始化
        initView()
        //初始化数据
        initData()
    }

    override fun handleViewState(viewState: VS) {
        when (viewState.status) {
            is ListPageViewStatus.RefreshFinish -> {
                refreshFinish(viewState)
            }
            is ListPageViewStatus.LoadMoreFinish -> {
                onLoadMore(viewState)
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
        //列表
        mBinding.rv.apply {
            adapter = this@ListPageFragment.adapter
            layoutManager = LinearLayoutManager(context)
        }
        adapter.setOnItemClickListener { _, _, position ->
            clickItem(position)
        }
        //空布局
        adapter.setEmptyView(R.layout.layout_empty_list)
        //上拉加载更多
        if (adapter is LoadMoreModule) {
            adapter.loadMoreModule.apply {
                //加载更多
                setOnLoadMoreListener { loadMore() }
                //不自动加载更多
//                isAutoLoadMore = false
                //未满一页不自动加载
                isEnableLoadMoreIfNotFullPage = false
            }
        }
        //TODO 自定义分割线和LoadMoreView
        //下拉刷新
        mBinding.swipeRefresh.setColorSchemeResources(R.color.common_refresh_scheme)
        mBinding.swipeRefresh.setOnRefreshListener {
            refresh(false)
        }
    }

    /**
     * 页面刷新
     */
    private fun refresh(isInit: Boolean) {
        sendViewIntent(ListPageViewIntent.Refresh(isInit))
    }

    /**
     * 下拉加载更多
     */
    private fun loadMore() {
        sendViewIntent(ListPageViewIntent.LoadMore)
    }

    /**
     * 点击item
     */
    private fun clickItem(position: Int) {
        sendViewIntent(ListPageViewIntent.ItemClick(position))
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
     * 刷新回调
     */
    abstract fun onRefresh(viewState: VS)

    /**
     * 加载更多回调
     */
    abstract fun onLoadMore(viewState: VS)

}