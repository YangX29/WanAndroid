package com.example.wanandroid.ui.mine

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.launcher.ARouter
import com.example.wanandroid.R
import com.example.wanandroid.base.BaseFragment
import com.example.wanandroid.common.RoutePath
import com.example.wanandroid.databinding.FragmentMineBinding
import com.example.wanandroid.databinding.LayoutToolbarMineBinding
import com.example.wanandroid.utils.extension.launch
import com.example.wanandroid.utils.user.UserManager
import com.example.wanandroid.view.widget.wan.MineHeader

/**
 * @author: Yang
 * @date: 2023/2/21
 * @description: 我的页
 */
class MineFragment : BaseFragment<FragmentMineBinding>() {

    //列表adapter
    private val adapter by lazy { MineAdapter() }

    //header
    private val header by lazy { MineHeader(requireContext()) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //初始化
        initView()
    }

    /**
     * 初始化view
     */
    private fun initView() {
        //toolbar
        mBinding.toolbar.setBar<LayoutToolbarMineBinding> {
            ivRank.setOnClickListener { jumpToRank() }
            ivMessage.setOnClickListener { jumpToMessage() }
        }
        //列表
        mBinding.rvMine.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@MineFragment.adapter
        }
        adapter.setHeaderView(header)
        adapter.setOnItemClickListener { _, _, position ->
            itemClick(adapter.data[position])
        }
        //初始化列表
        initRvItems()
        //监听用户信息
        observeUserInfo()
    }

    /**
     * 初始化列表项
     */
    private fun initRvItems() {
        val list = mutableListOf(
            MineAdapter.MineItem(
                MineAdapter.MineItemType.MIME_COIN,
                R.drawable.icon_share,
                R.string.mine_coin,
                "1000"
            ),
            MineAdapter.MineItem(
                MineAdapter.MineItemType.MINE_SHARE,
                R.drawable.icon_share,
                R.string.mine_share
            ),
            MineAdapter.MineItem(
                MineAdapter.MineItemType.MINE_COLLECTION,
                R.drawable.icon_share,
                R.string.mine_collection
            ),
            MineAdapter.MineItem(
                MineAdapter.MineItemType.MINE_HISTORY,
                R.drawable.icon_share,
                R.string.mine_history
            ),
            MineAdapter.MineItem(
                MineAdapter.MineItemType.MINE_TODO,
                R.drawable.icon_share,
                R.string.mine_todo
            ),
            MineAdapter.MineItem(
                MineAdapter.MineItemType.MINE_TOOL,
                R.drawable.icon_share,
                R.string.mine_tool
            ),
            MineAdapter.MineItem(
                MineAdapter.MineItemType.MINE_SETTING,
                R.drawable.icon_share,
                R.string.mine_setting
            )
        )
        adapter.setNewInstance(list)
    }

    /**
     * TODO 列表点击
     */
    private fun itemClick(item: MineAdapter.MineItem) {
        when (item.type) {
            MineAdapter.MineItemType.MIME_COIN -> {

            }
            MineAdapter.MineItemType.MINE_SHARE -> {
                jumpToMineShare()
            }
            MineAdapter.MineItemType.MINE_COLLECTION -> {

            }
            MineAdapter.MineItemType.MINE_HISTORY -> {

            }
            MineAdapter.MineItemType.MINE_TODO -> {

            }
            MineAdapter.MineItemType.MINE_TOOL -> {

            }
            MineAdapter.MineItemType.MINE_SETTING -> {

            }
        }
    }

    /**
     * 监听用户信息变化
     */
    private fun observeUserInfo() {
        launch {
            UserManager.getUserInfo().collect {
                header.setData(it)
                //TODO 更新积分item
            }
        }
    }

    /**
     * TODO 跳转到排名页
     */
    private fun jumpToRank() {

    }

    /**
     * TODO 跳转到消息中心
     */
    private fun jumpToMessage() {

    }

    /**
     * 跳转到我的分享
     */
    private fun jumpToMineShare() {
        ARouter.getInstance().build(RoutePath.SHARE_LIST)
            .navigation()
    }

}