package com.example.wanandroid.mvi.setting.shortcut

import com.example.wanandroid.base.mvi.ViewState
import com.example.wanandroid.ui.setting.SettingItem

/**
 * @author: Yang
 * @date: 2025/3/1
 * @description: 快捷方式ViewState
 */
sealed class ShortcutViewState : ViewState() {

    // 快捷方式列表
    data class ShortcutListState(val shortcutList: List<SettingItem>) : ShortcutViewState()

}