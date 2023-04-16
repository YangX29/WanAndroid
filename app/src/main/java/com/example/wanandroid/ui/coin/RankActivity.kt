package com.example.wanandroid.ui.coin

import androidx.activity.viewModels
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.wanandroid.R
import com.example.wanandroid.common.RoutePath
import com.example.wanandroid.model.CoinInfo
import com.example.wanandroid.mvi.coin.RankViewModel
import com.example.wanandroid.mvi.list.SimpleListViewState
import com.example.wanandroid.ui.list.ListPageActivity

/**
 * @author: Yang1`
 * @date: 2023/4/16
 * @description: 积分排名页面
 */
@Route(path = RoutePath.RANK)
class RankActivity : ListPageActivity<SimpleListViewState<CoinInfo>, RankViewModel>() {

    override val adapter = RankListAdapter()

    override val viewModel: RankViewModel by viewModels()

    override fun showDivider() = false

    override fun getPageTitle(): String {
        return getString(R.string.coin_rank)
    }

    override fun onLoadMore(viewState: SimpleListViewState<CoinInfo>) {
        adapter.addData(viewState.data ?: mutableListOf())
    }

    override fun onRefresh(viewState: SimpleListViewState<CoinInfo>) {
        adapter.setNewInstance(viewState.data ?: mutableListOf())
    }

    override fun onItemClick(position: Int) {
        //TODO 对应用户分享列表
    }

}