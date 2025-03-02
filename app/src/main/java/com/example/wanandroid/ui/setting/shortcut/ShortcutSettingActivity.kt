package com.example.wanandroid.ui.setting.shortcut

import android.os.Bundle
import androidx.activity.viewModels
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.module_common.utils.extension.divider
import com.example.module_common.utils.extension.dp2px
import com.example.module_common.utils.extension.linearLayout
import com.example.wanandroid.R
import com.example.wanandroid.base.BaseMVIActivity
import com.example.wanandroid.common.RoutePath
import com.example.wanandroid.databinding.ActivityShortcutSettingBinding
import com.example.wanandroid.mvi.setting.shortcut.ShortcutViewIntent
import com.example.wanandroid.mvi.setting.shortcut.ShortcutViewModel
import com.example.wanandroid.mvi.setting.shortcut.ShortcutViewState
import com.example.wanandroid.ui.setting.SettingAdapter
import com.example.wanandroid.ui.setting.SettingItem


/**
 * @author: Yang
 * @date: 2023/12/17
 * @description: 快捷方式设置页面
 */
@Route(path = RoutePath.SHORTCUT_SETTING)
class ShortcutSettingActivity :
    BaseMVIActivity<ActivityShortcutSettingBinding, ShortcutViewState, ShortcutViewIntent, ShortcutViewModel>() {

    override val viewModel: ShortcutViewModel by viewModels()

    private val mAdapter by lazy { SettingAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 初始化
        initView()
        initData()
    }

    override fun handleViewState(viewState: ShortcutViewState) {
        when (viewState) {
            is ShortcutViewState.ShortcutListState -> {
                mAdapter.setList(viewState.shortcutList)
            }
        }
    }

    /**
     * 初始化view
     */
    private fun initView() {
        // 导航栏
        mBinding.toolbar.apply {
            setOnLeftClick { finish() }
        }
        // 列表
        mBinding.rvShortcut.apply {
            linearLayout()
            divider(R.color.common_divider, 0.5f.dp2px().toInt(), 10.dp2px())
            adapter = mAdapter.apply {
                setOnItemClickListener { _, _, position -> itemClick(position) }
                setOnItemChildSwitchListener { item, on -> itemSwitch(item, on) }
            }
        }
    }

    /**
     * 初始化数据
     */
    private fun initData() {
        sendIntent(ShortcutViewIntent.InitShortcut)
    }

    /**
     * 点击快捷方式
     */
    private fun itemClick(position: Int) {
        mAdapter.getItemOrNull(position)?.let {
            if (it is SettingItem.Common) {
                sendIntent(ShortcutViewIntent.JumpToShortcut(it.tag))
            }
        }
    }

    /**
     * 切换快捷方式开关
     */
    private fun itemSwitch(item: SettingItem.Switch, on: Boolean) {
        sendIntent(ShortcutViewIntent.SwitchShortcut(item.tag, on))
    }


}