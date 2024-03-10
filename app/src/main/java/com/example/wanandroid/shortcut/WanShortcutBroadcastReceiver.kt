package com.example.wanandroid.shortcut

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.wanandroid.common.LiveEventKey
import com.jeremyliao.liveeventbus.LiveEventBus

/**
 * @author: Yang
 * @date: 2023/12/17
 * @description:WanAndroid快捷方式相关广播接收器
 */
class WanShortcutBroadcastReceiver : BroadcastReceiver() {

    companion object {
        const val ACTION_ADD_PIN_SHORTCUT =
            "com.example.wanandroid.shortcut.WanShortcutBroadcastReceiver.ADD_PIN_SHORTCUT"

        const val KEY_SHORTCUT_ID = "KEY_SHORTCUT_ID"
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        when (intent?.action) {
            ACTION_ADD_PIN_SHORTCUT -> {
                addPinShortcut(context, intent)
            }
        }
    }

    /**
     * 添加固定快捷方式成功
     */
    private fun addPinShortcut(context: Context?, intent: Intent) {
        val id = intent.getStringExtra(KEY_SHORTCUT_ID)
        LiveEventBus.get<String>(LiveEventKey.KEY_ADD_WEB_SHORTCUT).post(id)
    }

}