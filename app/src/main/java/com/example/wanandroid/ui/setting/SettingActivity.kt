package com.example.wanandroid.ui.setting

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
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
        mBinding.toolbar.setCommonTitle(R.string.mine_setting) {
            finish()
        }
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
            SettingItem.Common(SettingItem.Type.THEME, getString(R.string.setting_theme)),
            SettingItem.Switch(
                SettingItem.Type.TOP_ARTICLE,
                getString(R.string.setting_top_article),
                true
            ),
            SettingItem.Switch(SettingItem.Type.BANNER, getString(R.string.setting_banner), true),
            SettingItem.Switch(SettingItem.Type.HISTORY, getString(R.string.setting_history), true),
            SettingItem.Common(
                SettingItem.Type.CLEAR_CACHE,
                getString(R.string.setting_clear_cache),
                "0M"
            ),
            SettingItem.Common(
                SettingItem.Type.VERSION,
                getString(R.string.setting_update),
                "1.0.0"
            ),
            SettingItem.Common(SettingItem.Type.ABOUT, getString(R.string.setting_about)),
            SettingItem.Common(SettingItem.Type.POLICY, getString(R.string.setting_policy)),
            SettingItem.Logout
        )
        adapter.setNewInstance(list)
    }

    /**
     * 点击item
     */
    private fun itemClick(position: Int) {
        //TODO
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
    private fun itemSwitch(item: SettingItem, on: Boolean) {
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