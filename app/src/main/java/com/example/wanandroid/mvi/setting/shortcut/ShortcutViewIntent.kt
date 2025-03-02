package com.example.wanandroid.mvi.setting.shortcut

import com.example.wanandroid.base.mvi.ViewIntent

/**
 * @author: Yang
 * @date: 2025/3/1
 * @description: 快捷方式ViewIntent
 */
sealed class ShortcutViewIntent : ViewIntent() {

    // 初始化快捷方式
    object InitShortcut : ShortcutViewIntent()

    // 跳转到快捷方式
    data class JumpToShortcut(val id: String) : ShortcutViewIntent()

    // 切换快捷方式开关状态
    data class SwitchShortcut(val id: String, val on: Boolean) : ShortcutViewIntent()

}