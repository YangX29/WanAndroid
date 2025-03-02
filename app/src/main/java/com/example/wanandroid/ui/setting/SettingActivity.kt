package com.example.wanandroid.ui.setting

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.example.wanandroid.R
import com.example.wanandroid.base.BaseMVIActivity
import com.example.wanandroid.common.RoutePath
import com.example.wanandroid.databinding.ActivitySettingBinding
import com.example.wanandroid.mvi.setting.SettingConfig
import com.example.wanandroid.mvi.setting.SettingViewIntent
import com.example.wanandroid.mvi.setting.SettingViewModel
import com.example.wanandroid.mvi.setting.SettingViewState
import com.example.wanandroid.utils.toast.ToastUtils

/**
 * @author: Yang
 * @date: 2023/4/26
 * @description: 设置页
 */
@Route(path = RoutePath.SETTING)
class SettingActivity :
    BaseMVIActivity<ActivitySettingBinding, SettingViewState, SettingViewIntent, SettingViewModel>() {

    override val viewModel: SettingViewModel by viewModels()

    //列表adapter
    private val adapter = SettingAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //初始化
        initView()
        initData()
    }

    override fun handleViewState(viewState: SettingViewState) {
        when (viewState) {
            is SettingViewState.LogoutSuccess -> {
                logout()
            }

            is SettingViewState.UpdateConfig -> {
                updateConfig(viewState.config)
            }
        }
    }

    /**
     * 初始化View
     */
    private fun initView() {
        //toolbar
        mBinding.toolbar.setOnLeftClick { finish() }
        //列表
        mBinding.rvSetting.apply {
            layoutManager = LinearLayoutManager(this@SettingActivity)
            adapter = this@SettingActivity.adapter
        }
        adapter.setOnItemClickListener { _, _, position -> itemClick(position) }
        adapter.setOnItemChildClickListener { _, view, position -> itemChildClick(view, position) }
        adapter.setOnItemChildSwitchListener { item, on -> itemSwitch(item, on) }
    }

    /**
     * 初始化数据
     */
    private fun initData() {
        //TODO
        val list = mutableListOf(
            SettingItem.Common(SettingItem.SettingType.THEME.name, getString(R.string.setting_theme)),
            SettingItem.Common(SettingItem.SettingType.NOTIFY.name, getString(R.string.setting_notification)),
            SettingItem.Common(SettingItem.SettingType.SHORTCUT.name, getString(R.string.setting_shortcut)),
            SettingItem.Common(SettingItem.SettingType.WIDGET.name, getString(R.string.setting_widget)),
            SettingItem.Switch(
                SettingItem.SettingType.TOP_ARTICLE.name,
                getString(R.string.setting_top_article),
                true
            ),
            SettingItem.Switch(SettingItem.SettingType.BANNER.name, getString(R.string.setting_banner), true),
            SettingItem.Switch(SettingItem.SettingType.HISTORY.name, getString(R.string.setting_history), true),
            SettingItem.Common(
                SettingItem.SettingType.CLEAR_CACHE.name,
                getString(R.string.setting_clear_cache),
                "0M"
            ),
            SettingItem.Common(
                SettingItem.SettingType.VERSION.name,
                getString(R.string.setting_update),
                "1.0.0"
            ),
            SettingItem.Common(SettingItem.SettingType.ABOUT.name, getString(R.string.setting_about)),
            SettingItem.Common(SettingItem.SettingType.POLICY.name, getString(R.string.setting_policy)),
            SettingItem.Logout
        )
        adapter.setNewInstance(list)
    }

    /**
     * 点击item
     */
    private fun itemClick(position: Int) {
        adapter.data.getOrNull(position)?.let {
            if (it is SettingItem.Common) {
                when (SettingItem.SettingType.valueOf(it.tag)) {
                    SettingItem.SettingType.THEME -> {
                        //TODO
                    }

                    SettingItem.SettingType.NOTIFY -> {
                        //TODO
                    }

                    SettingItem.SettingType.SHORTCUT -> {// 快捷方式
                        jumpToShortcutSetting()
                    }

                    SettingItem.SettingType.WIDGET -> {
                        //TODO
                    }

                    SettingItem.SettingType.TOP_ARTICLE -> {
                        //TODO
                    }

                    SettingItem.SettingType.BANNER -> {
                        //TODO
                    }

                    SettingItem.SettingType.HISTORY -> {
                        //TODO
                    }

                    SettingItem.SettingType.CLEAR_CACHE -> {
                        //TODO
                    }

                    SettingItem.SettingType.VERSION -> {
                        //TODO
                    }

                    SettingItem.SettingType.ABOUT -> {
                        //TODO
                    }

                    SettingItem.SettingType.POLICY -> {
                        //TODO
                    }

                }
            }
        }
    }

    /**
     * 跳转到快捷方式设置页面
     */
    private fun jumpToShortcutSetting() {
        ARouter.getInstance().build(RoutePath.SHORTCUT_SETTING)
            .navigation()
    }

    /**
     * 点击Item子view
     */
    private fun itemChildClick(view: View, position: Int) {
        if (view.id == R.id.btLogout) {
            //退出登录
            sendIntent(SettingViewIntent.Logout)
        }
    }

    /**
     * 列表项切换开关
     */
    private fun itemSwitch(item: SettingItem.Switch, on: Boolean) {
        //TODO
    }

    /**
     * 退出登录
     */
    private fun logout() {
        ToastUtils.show(this, R.string.common_logout_success)
        finish()
    }

    /**
     * TODO 更新配置信息
     */
    private fun updateConfig(config: SettingConfig) {

    }

}