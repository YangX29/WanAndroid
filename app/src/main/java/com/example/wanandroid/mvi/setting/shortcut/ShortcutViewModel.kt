package com.example.wanandroid.mvi.setting.shortcut

import android.content.Intent
import android.os.Build
import com.example.module_common.utils.extension.getStringRes
import com.example.wanandroid.R
import com.example.wanandroid.base.BaseViewModel
import com.example.wanandroid.base.mvi.ViewEvent
import com.example.wanandroid.shortcut.WanShortcutManager
import com.example.wanandroid.ui.setting.SettingItem
import com.example.wanandroid.utils.extension.launch

/**
 * @author: Yang
 * @date: 2025/3/1
 * @description: 快捷方式页面ViewModel
 */
class ShortcutViewModel : BaseViewModel<ShortcutViewState, ShortcutViewIntent>() {

    private val dynamicShortcut = mutableMapOf(
        WanShortcutManager.SHORTCUT_ADD_TODO to getStringRes(R.string.shortcut_todo_add),
        WanShortcutManager.SHORTCUT_TODO_LIST to getStringRes(R.string.shortcut_todo_list),
        WanShortcutManager.SHORTCUT_COLLECTION to getStringRes(R.string.shortcut_collection)
    )

    override fun handleIntent(viewIntent: ShortcutViewIntent) {
        when (viewIntent) {
            is ShortcutViewIntent.InitShortcut -> {
                initShortcut()
            }

            is ShortcutViewIntent.JumpToShortcut -> {
                jumpToShortcut(viewIntent.id)
            }

            is ShortcutViewIntent.SwitchShortcut -> {
                switchShortcut(viewIntent.id, viewIntent.on)
            }
        }
    }

    /**
     * 初始化快捷方式
     */
    private fun initShortcut() {
        launch {
            val list = mutableListOf<SettingItem>()
            // 静态快捷方式
            list.add(SettingItem.SubTitle(getStringRes(R.string.shortcut_manifest)))
            WanShortcutManager.getShortcutById(WanShortcutManager.SHORTCUT_SEARCH)?.let {
                list.add(SettingItem.Common(it.id, it.shortLabel.toString()))
            }
            // 获取动态快捷方式
            val dynamicList = dynamicShortcut.map {
                val isOn = WanShortcutManager.getShortcutById(it.key)?.let { shortcut ->
                    shortcut.isEnabled && shortcut.isDynamic
                } ?: false
                SettingItem.Switch(it.key, it.value, isOn)
            }
            list.add(SettingItem.SubTitle(getStringRes(R.string.shortcut_dynamic)))
            list.addAll(dynamicList)
            // 获取固定快捷方式
            val pinedList = WanShortcutManager.getPinedShortcuts().map {
                SettingItem.Common(it.id, it.shortLabel.toString())
            }
            if (pinedList.isNotEmpty()) {
                list.add(SettingItem.SubTitle(getStringRes(R.string.shortcut_pined)))
                list.addAll(pinedList)
            }
            updateViewState(ShortcutViewState.ShortcutListState(list))
        }
    }

    /**
     * 跳转到快捷方式
     */
    private fun jumpToShortcut(id: String) {
        launch {
            WanShortcutManager.reportShortcutUsed(id)
            WanShortcutManager.getShortcutById(id)?.let {
                // 跳转到对应快捷方式页面
                emitViewEvent(ViewEvent.JumpToPageWithIntent(it.intent.apply {
                    // 移除静态快捷方式创建新的堆栈flag
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        removeFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        removeFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    }
                }))
            }
        }
    }

    /**
     * 切换快捷方式状态
     */
    private fun switchShortcut(id: String, on: Boolean) {
        launch {
            if (on) {
                // 尝试添加快捷方式
                when (id) {
                    WanShortcutManager.SHORTCUT_ADD_TODO -> {
                        WanShortcutManager.pushTodoCreatorShortcut()
                    }

                    WanShortcutManager.SHORTCUT_TODO_LIST -> {
                        WanShortcutManager.pushTodoListShortcut()
                    }

                    WanShortcutManager.SHORTCUT_COLLECTION -> {
                        WanShortcutManager.pushCollectionShortcut()
                    }

                    else -> {}
                }
                // 启用快捷方式
                WanShortcutManager.enableShortcut(listOf(id))
            } else {
                // 禁用快捷方式
                WanShortcutManager.disableShortcut(listOf(id))
                // 尝试删除快捷方式
                WanShortcutManager.removeDynamicShortcut(id)
            }

        }
    }


}