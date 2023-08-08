package com.example.wanandroid.mvi.coin

import com.example.wanandroid.mvi.list.ListPageViewIntent
import com.example.wanandroid.mvi.list.ListPageViewModel
import com.example.wanandroid.mvi.list.ListPageViewStatus
import com.example.wanandroid.utils.extension.executeCallSuspend
import com.example.wanandroid.utils.extension.launchByIo
import com.example.wanandroid.utils.user.UserManager
import kotlinx.coroutines.async

/**
 * @author: Yang
 * @date: 2023/4/16
 * @description: 我的积分ViewModel
 */
class MineCoinViewModel : ListPageViewModel<MineCoinViewState, ListPageViewIntent>() {


    override fun refresh(isInit: Boolean) {
        loadData(true)
    }

    override fun loadMore() {
        loadData(false)
    }

    /**
     * 加载数据
     */
    private fun loadData(isRefresh: Boolean) {
        launchByIo {
            //获取积分信息
            val getCoinInfo = if (isRefresh) {
                async { executeCallSuspend({ apiService.getCoinInfo() }) }
            } else {
                null
            }
            //获取积分列表
            val getCoinHistory =
                async { executeCallSuspend({ apiService.getCoinHistoryList(page?.page ?: 0) }) }
            //获取数据
            val coinInfo = getCoinInfo?.await()
            val coinHistory = getCoinHistory.await()
            //检查接口回调结果
            if (coinHistory.isFailed() || coinHistory.isFailed()) {
                updateViewState(
                    MineCoinViewState(
                        ListPageViewStatus.RefreshFailed,
                        mutableListOf()
                    )
                )
            } else {
                //更新数据
                coinHistory.data?.apply {
                    //更新分页
                    updatePage(this)
                    //更新view
                    if (isRefresh) {
                        //更新用户积分信息
                        UserManager.updateCoinInfo(coinInfo?.data)
                        updateViewState(
                            MineCoinViewState(
                                ListPageViewStatus.RefreshFinish(page?.isFinish ?: false),
                                list,
                                coinInfo = coinInfo?.data
                            )
                        )
                    } else {
                        updateViewState(
                            MineCoinViewState(
                                ListPageViewStatus.LoadMoreFinish(page?.isFinish ?: false),
                                list
                            )
                        )
                    }
                }
            }
        }
    }

}