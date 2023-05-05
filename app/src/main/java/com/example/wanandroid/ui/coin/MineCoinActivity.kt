package com.example.wanandroid.ui.coin

import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.example.module_common.utils.extension.invisible
import com.example.module_common.utils.extension.visible
import com.example.wanandroid.R
import com.example.wanandroid.base.BaseMVIActivity
import com.example.wanandroid.common.Constants
import com.example.wanandroid.common.RoutePath
import com.example.wanandroid.databinding.ActivityMineCoinBinding
import com.example.wanandroid.mvi.coin.MineCoinViewModel
import com.example.wanandroid.mvi.coin.MineCoinViewState
import com.example.wanandroid.mvi.list.ListPageViewIntent
import com.example.wanandroid.mvi.list.ListPageViewStatus
import com.example.wanandroid.ui.web.WebActivity
import com.example.wanandroid.view.widget.CustomLoadMoreView

/**
 * @author: Yang
 * @date: 2023/4/16
 * @description: 我的积分页面
 */
@Route(path = RoutePath.COIN)
class MineCoinActivity :
    BaseMVIActivity<ActivityMineCoinBinding, MineCoinViewState, ListPageViewIntent, MineCoinViewModel>() {

    override val viewModel: MineCoinViewModel by viewModels()

    private val adapter = CoinListAdapter()

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

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //初始化
        initView()
        //初始化数据
        initData()
    }

    override fun handleViewState(viewState: MineCoinViewState) {
        when (val status = viewState.status) {
            is ListPageViewStatus.RefreshFinish -> {
                //更新数据
                onRefresh(viewState)
                updateLoadMore(status.finish)
            }

            is ListPageViewStatus.LoadMoreFinish -> {
                //更新数据
                onLoadMore(viewState)
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
        //开始loading
        mBinding.loading.visible()
        mBinding.rvCoin.invisible()
        //初始化数据
        refresh(true)
    }

    /**
     * 初始化View
     */
    private fun initView() {
        //toolbar
        mBinding.toolbar.apply {
            //返回按钮
            setOnLeftClick { finish() }
            //右侧菜单
            setOnRightClick { jumpToRank() }
            //说明
            setOnExtraClick { jumpToHelp() }
        }
        //列表
        mBinding.rvCoin.apply {
            adapter = this@MineCoinActivity.adapter
            layoutManager = LinearLayoutManager(context)
        }
        //空布局
        adapter.setEmptyView(R.layout.layout_empty_list)
        //上拉加载更多
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
        //返回顶部按钮
        mBinding.fabTop.visible()
        mBinding.fabTop.hide()
        mBinding.fabTop.setOnClickListener { backTop() }
        //监听列表滚动
        mBinding.rvCoin.addOnScrollListener(ScrollListener())
    }

    /**
     * 返回列表顶部
     */
    private fun backTop() {
        //隐藏按钮
        mBinding.fabTop.hide()
        //返回顶部
        mBinding.rvCoin.smoothScrollToPosition(0)
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
    private fun onRefresh(viewState: MineCoinViewState) {
        //停止loading
        mBinding.loading.invisible()
        mBinding.rvCoin.visible()
        //数据
        viewState.coinInfo?.apply {
            //TODO 动画，
            //积分
            mBinding.tvCoin.text = "$coinCount"
            //等级
            mBinding.tvLevel.text = getString(R.string.user_level, level)
            //排名
            mBinding.tvRank.text = getString(R.string.user_rank, rank)
        }
        //列表
        adapter.setNewInstance(viewState.historyList)
    }

    /**
     * 加载更多
     */
    private fun onLoadMore(viewState: MineCoinViewState) {
        adapter.addData(viewState.historyList)
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
     * 跳转到排名页
     */
    private fun jumpToRank() {
        ARouter.getInstance().build(RoutePath.RANK).navigation()
    }

    /**
     * 跳转到积分说明页面
     */
    private fun jumpToHelp() {
        ARouter.getInstance().build(RoutePath.WEB)
            .withString(WebActivity.WEB_URL, Constants.URL_COIN_HELP)
            .navigation()
    }


}