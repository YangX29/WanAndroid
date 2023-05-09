package com.example.wanandroid.ui.coin

import androidx.activity.viewModels
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.example.wanandroid.R
import com.example.wanandroid.common.RoutePath
import com.example.wanandroid.model.CoinInfo
import com.example.wanandroid.mvi.coin.RankViewModel
import com.example.wanandroid.ui.list.SimpleListActivity
import com.example.wanandroid.ui.user.UserPageActivity

/**
 * @author: Yang1`
 * @date: 2023/4/16
 * @description: 积分排名页面
 */
@Route(path = RoutePath.RANK)
class RankActivity : SimpleListActivity<CoinInfo, RankViewModel>() {

    override val adapter = RankListAdapter()

    override val viewModel: RankViewModel by viewModels()

    override fun showDivider() = false

    override fun getPageTitle(): String {
        return getString(R.string.coin_rank)
    }

    override fun onItemClick(position: Int) {
        val item = adapter.data[position]
        //对应用户分享列表
        ARouter.getInstance().build(RoutePath.USER_PAGE)
            .withLong(UserPageActivity.KEY_USER_ID, item.userId)
            .navigation()
    }

}