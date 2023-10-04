package com.example.wanandroid.ui.mine

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.launcher.ARouter
import com.example.module_common.utils.extension.dp2px
import com.example.module_common.utils.log.logE
import com.example.module_common.utils.red_dot.RedDotManager
import com.example.wanandroid.R
import com.example.wanandroid.base.BaseFragment
import com.example.wanandroid.common.RoutePath
import com.example.wanandroid.databinding.FragmentMineBinding
import com.example.wanandroid.databinding.LayoutToolbarMineBinding
import com.example.wanandroid.mvi.todo.CalendarEventUtils
import com.example.wanandroid.utils.extension.launch
import com.example.wanandroid.utils.red_dot.WanRedDotType
import com.example.wanandroid.utils.user.UserManager
import com.example.wanandroid.view.dialog.date_picker.TimePickerDialog
import com.example.wanandroid.view.widget.wan.MineHeader
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.badge.BadgeUtils

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
        //观察红点
        observeRedDot()
    }

    /**
     * 观察红点
     */
    @SuppressLint("UnsafeOptInUsageError")
    private fun observeRedDot() {
        val badgeDrawable = BadgeDrawable.create(requireContext()).apply {
            backgroundColor = Color.RED
            badgeTextColor = Color.WHITE
            badgeGravity = BadgeDrawable.TOP_END
            maxCharacterCount = 3
            horizontalOffset = 15.dp2px()
            verticalOffset = 10.dp2px()
            isVisible = false
        }
        mBinding.toolbar.setBar<LayoutToolbarMineBinding> {
            ivMessage.post {
                BadgeUtils.attachBadgeDrawable(badgeDrawable, ivMessage)
            }
        }
        RedDotManager.observeRedDot(WanRedDotType.MINE_MESSAGE, viewLifecycleOwner) {
            logE("test_bug", "red dot:$it")
            badgeDrawable.apply {
                isVisible = (it > 0)
                number = it
            }
        }
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
                R.drawable.icon_ticket,
                R.string.mine_coin,
                "1000"
            ), MineAdapter.MineItem(
                MineAdapter.MineItemType.MINE_SHARE, R.drawable.icon_share, R.string.mine_share
            ), MineAdapter.MineItem(
                MineAdapter.MineItemType.MINE_COLLECTION,
                R.drawable.icon_like,
                R.string.mine_collection
            ), MineAdapter.MineItem(
                MineAdapter.MineItemType.MINE_HISTORY,
                R.drawable.icon_footprint,
                R.string.mine_history
            ), MineAdapter.MineItem(
                MineAdapter.MineItemType.MINE_TODO, R.drawable.icon_calendar, R.string.mine_todo
            ), MineAdapter.MineItem(
                MineAdapter.MineItemType.MINE_TOOL, R.drawable.icon_tool, R.string.mine_tool
            ), MineAdapter.MineItem(
                MineAdapter.MineItemType.MINE_SETTING,
                R.drawable.icon_settings,
                R.string.mine_setting
            ),
            MineAdapter.MineItem(
                MineAdapter.MineItemType.MINE_TEST,
                R.drawable.icon_settings,
                R.string.mine_test
            )
        )
        adapter.setNewInstance(list)
    }

    /**
     * TODO 列表点击
     */
    private fun itemClick(item: MineAdapter.MineItem) {
        when (item.type) {
            MineAdapter.MineItemType.MIME_COIN -> {//我的积分
                jumpToMineCoin()
            }

            MineAdapter.MineItemType.MINE_SHARE -> {//我的分享
                jumpToMineShare()
            }

            MineAdapter.MineItemType.MINE_COLLECTION -> {
                jumpToCollection()
            }

            MineAdapter.MineItemType.MINE_HISTORY -> {

            }

            MineAdapter.MineItemType.MINE_TODO -> {//todo页面
                jumpToTodo()
            }

            MineAdapter.MineItemType.MINE_TOOL -> {//工具
                jumpToTools()
            }

            MineAdapter.MineItemType.MINE_SETTING -> {//设置
                jumpToSetting()
            }

            MineAdapter.MineItemType.MINE_TEST -> {//测试
                jumpToTest()
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
                //更新积分item
                updateCoinCount(it?.coinCount)
            }
        }
        launch {
            UserManager.getUserCoinInfo().collect {
                it?.run {
                    header.updateCoinInfo(it)
                    //更新积分item
                    updateCoinCount(it.coinCount)
                }
            }
        }
    }

    /**
     * 更新积分item
     */
    private fun updateCoinCount(count: Int?) {
        val item = adapter.data.find { it.type == MineAdapter.MineItemType.MIME_COIN } ?: return
        item.desc = if (count == null) "-" else "$count"
        val index = adapter.data.indexOf(item)
        adapter.setData(index, item)
    }

    /**
     * 跳转到排名页
     */
    private fun jumpToRank() {
        ARouter.getInstance().build(RoutePath.RANK).navigation()
    }

    /**
     * 跳转到消息中心
     */
    private fun jumpToMessage() {
        //消除红点
        RedDotManager.syncRedDot(WanRedDotType.MINE_MESSAGE, 0)
        ARouter.getInstance().build(RoutePath.MESSAGE_CENTER)
            .navigation()
    }

    /**
     * 跳转到我的分享
     */
    private fun jumpToMineShare() {
        ARouter.getInstance().build(RoutePath.SHARE_LIST).navigation()
    }

    /**
     * 跳转到工具页
     */
    private fun jumpToTools() {
        ARouter.getInstance().build(RoutePath.TOOL)
            .navigation()
    }

    /**
     * 跳转到TODO页面
     */
    private fun jumpToTodo() {
        CalendarEventUtils.requestCalendarPermission(requireActivity()) {
            //申请权限成功
            if (it) {
                ARouter.getInstance().build(RoutePath.TODO)
                    .navigation()
            }
        }

    }

    /**
     * 跳转到设置
     */
    private fun jumpToSetting() {
        ARouter.getInstance().build(RoutePath.SETTING)
            .navigation()
    }

    /**
     * 跳转到我的收藏
     */
    private fun jumpToCollection() {
        ARouter.getInstance().build(RoutePath.COLLECTION_LIST).navigation()
    }

    /**
     * 跳转到我的积分
     */
    private fun jumpToMineCoin() {
        ARouter.getInstance().build(RoutePath.COIN).navigation()
    }

    /**
     * 跳转到测试
     */
    private fun jumpToTest() {
        ARouter.getInstance().build(RoutePath.TEST).navigation()
    }

}