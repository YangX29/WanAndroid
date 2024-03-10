package com.example.wanandroid.shortcut

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.content.pm.ShortcutInfoCompat
import androidx.core.content.pm.ShortcutManagerCompat
import androidx.core.content.pm.ShortcutManagerCompat.FLAG_MATCH_DYNAMIC
import androidx.core.content.pm.ShortcutManagerCompat.FLAG_MATCH_MANIFEST
import androidx.core.content.pm.ShortcutManagerCompat.FLAG_MATCH_PINNED
import androidx.core.graphics.drawable.IconCompat
import com.example.module_common.utils.app.ContextUtils
import com.example.module_common.utils.extension.getStringRes
import com.example.module_common.utils.extension.isNotNull
import com.example.module_common.utils.extension.md5
import com.example.wanandroid.R
import com.example.wanandroid.ui.collect.CollectionActivity
import com.example.wanandroid.ui.todo.TodoActivity
import com.example.wanandroid.ui.todo.TodoEditActivity
import com.example.wanandroid.ui.web.WebActivity

/**
 * @author: Yang
 * @date: 2023/11/26
 * @description: WanAndroid快捷方式管理类
 */
object WanShortcutManager {

    //搜索快捷方式
    const val SHORTCUT_SEARCH = "shortcut_search"

    //添加todo快捷方式
    const val SHORTCUT_ADD_TODO = "shortcut_add_todo"

    //todo列表快捷方式
    const val SHORTCUT_TODO_LIST = "shortcut_todo_list"

    //收藏快捷方式
    const val SHORTCUT_COLLECTION = "shortcut_collection"

    //指定文章快捷方式
    private const val SHORTCUT_ARTICLE = "shortcut_article"

    private val context by lazy { ContextUtils.getContext() }

    /**
     * 添加Web链接快捷方式固定到主屏幕，不可移除
     */
    fun addWebPinShortcut(context: Context, url: String, name: String): Boolean {
        val id = url.md5()
        val webIntent = Intent(context, WebActivity::class.java).apply {
            action = Intent.ACTION_VIEW
            putExtra(WebActivity.WEB_URL, url)
        }
        return addPinShortcut(
            ShortcutInfoCompat.Builder(context, "${SHORTCUT_ARTICLE}_$id")
                .setShortLabel(name)
                .setIcon(IconCompat.createWithResource(context, R.mipmap.ic_launcher))
                .setIntent(webIntent)
                .build()
        )
    }

    /**
     * 判断是否存在对应链接的固定快捷方式
     */
    fun checkWebPinShortcutHasExist(id: String): Boolean {
        return getAllPinShortcutList().find {
            it.id == "${SHORTCUT_ARTICLE}_$id"
        }.isNotNull()
    }

    /**
     * 获取所有固定快捷方式
     */
    fun getAllPinShortcutList(): MutableList<ShortcutInfoCompat> {
        return ShortcutManagerCompat.getShortcuts(context, FLAG_MATCH_PINNED)
    }

    /**
     * 添加创建Todo快捷方式
     */
    fun addTodoCreatorShortcut(context: Context): Boolean {
        val todoIntent = Intent(context, TodoEditActivity::class.java).apply {
            action = Intent.ACTION_VIEW
        }
        return addDynamicShortcut(
            ShortcutInfoCompat.Builder(context, SHORTCUT_ADD_TODO)
                .setShortLabel(getStringRes(R.string.shortcut_todo_add))
                .setLongLabel(getStringRes(R.string.shortcut_todo_add_des))
                .setDisabledMessage(getStringRes(R.string.shortcut_todo_add_disable))
                .setIcon(IconCompat.createWithResource(context, R.mipmap.ic_launcher))
                .setIntent(todoIntent)
                .build()
        )
    }

    /**
     * 添加Todo列表快捷方式
     */
    fun addTodoListShortcut(context: Context): Boolean {
        val todoListIntent = Intent(context, TodoActivity::class.java).apply {
            action = Intent.ACTION_VIEW
        }
        return addDynamicShortcut(
            ShortcutInfoCompat.Builder(context, SHORTCUT_TODO_LIST)
                .setShortLabel(getStringRes(R.string.shortcut_todo_list))
                .setLongLabel(getStringRes(R.string.shortcut_todo_list_des))
                .setDisabledMessage(getStringRes(R.string.shortcut_todo_list_disable))
                .setIcon(IconCompat.createWithResource(context, R.mipmap.ic_launcher))
                .setIntent(todoListIntent)
                .build()
        )
    }


    /**
     * 添加收藏列表快捷方式
     */
    fun addCollectionShortcut(context: Context): Boolean {
        val collectionIntent = Intent(context, CollectionActivity::class.java).apply {
            action = Intent.ACTION_VIEW
        }
        return addDynamicShortcut(
            ShortcutInfoCompat.Builder(context, SHORTCUT_COLLECTION)
                .setShortLabel(getStringRes(R.string.shortcut_collection))
                .setLongLabel(getStringRes(R.string.shortcut_collection_des))
                .setDisabledMessage(getStringRes(R.string.shortcut_collection_disable))
                .setIcon(IconCompat.createWithResource(context, R.mipmap.ic_launcher))
                .setIntent(collectionIntent)
                .build()
        )
    }

    /**
     * 判断动态快捷方式是否存在
     */
    fun hasWanShortcut(id: String): Boolean {
        return ShortcutManagerCompat.getShortcuts(context, FLAG_MATCH_PINNED or FLAG_MATCH_DYNAMIC)
            .find {
                it.id == "${SHORTCUT_ARTICLE}_$id"
            }.isNotNull()
    }

    /**
     * 移除收藏列表快捷方式
     */
    fun remoteWanShortcut(id: String): Boolean {
        return removeDynamicShortcut(listOf(id))
    }

    /**
     * 上报快捷方式的使用
     */
    fun reportShortcutUsed(id: String) {
        kotlin.runCatching {
            ShortcutManagerCompat.reportShortcutUsed(context, id)
        }
    }

    /**
     * 是否达到后台更新/修改快捷方式次数
     */
    fun isRateLimitingActive(): Boolean {
        return ShortcutManagerCompat.isRateLimitingActive(context)
    }

    /**
     * 获取最大快捷方式数量（静态+动态）
     */
    fun getMaxShortcutCount(): Int {
        return ShortcutManagerCompat.getMaxShortcutCountPerActivity(context)
    }

    /**
     * 禁用快捷方式，从静态/动态快捷方式列表移除，并禁用固定快捷方式
     */
    fun disableShortcut(ids: List<String>, errorMessage: String? = null): Boolean {
        return kotlin.runCatching {
            ShortcutManagerCompat.disableShortcuts(context, ids, errorMessage)
        }.isSuccess
    }

    /**
     * 启用快捷方式
     */
    fun enableShortcut(ids: List<String>): Boolean {
        return kotlin.runCatching {
            val list = ShortcutManagerCompat.getShortcuts(
                context, FLAG_MATCH_MANIFEST.or(FLAG_MATCH_DYNAMIC).or(
                    FLAG_MATCH_PINNED
                )
            ).filter { ids.contains(it.id) }
            ShortcutManagerCompat.enableShortcuts(context, list)
        }.isSuccess
    }

    /**
     * 添加动态快捷方式到app启动图标上
     */
    private fun addDynamicShortcut(shortcutInfoCompat: ShortcutInfoCompat): Boolean {
        return kotlin.runCatching {
            ShortcutManagerCompat.pushDynamicShortcut(context, shortcutInfoCompat)
        }.isSuccess
    }

    /**
     * 移除动态快捷方式
     */
    private fun removeDynamicShortcut(ids: List<String>): Boolean {
        return kotlin.runCatching {
            ShortcutManagerCompat.removeDynamicShortcuts(context, ids)
        }.isSuccess
    }

    /**
     * 移除所有动态快捷方式
     */
    private fun removeAllDynamicShortcut() {
        ShortcutManagerCompat.removeAllDynamicShortcuts(context)
    }

    /**
     * 设置动态快捷方式
     */
    private fun setDynamicShortcut(shortcuts: List<ShortcutInfoCompat>): Boolean {
        return kotlin.runCatching {
            ShortcutManagerCompat.setDynamicShortcuts(context, shortcuts)
        }.getOrDefault(false)
    }

    /**
     * 添加固定快捷方式到主屏幕，不可移除只能禁用
     */
    private fun addPinShortcut(shortcutInfoCompat: ShortcutInfoCompat): Boolean {
        if (ShortcutManagerCompat.isRequestPinShortcutSupported(context)) {
            //添加成功发送广播
            val callback = Intent(context, WanShortcutBroadcastReceiver::class.java).run {
                action = WanShortcutBroadcastReceiver.ACTION_ADD_PIN_SHORTCUT
                data = Uri.parse(toUri(Intent.URI_INTENT_SCHEME))
                putExtra(WanShortcutBroadcastReceiver.KEY_SHORTCUT_ID, shortcutInfoCompat.id)
                PendingIntent.getBroadcast(
                    context, 0,
                    this, PendingIntent.FLAG_UPDATE_CURRENT
                ).intentSender
            }
            return kotlin.runCatching {
                ShortcutManagerCompat.requestPinShortcut(context, shortcutInfoCompat, callback)
            }.isSuccess
        }
        return false
    }

    /**
     * 更新动态/固定快捷方式
     */
    private fun updateShortcut(shortcuts: List<ShortcutInfoCompat>): Boolean {
        return kotlin.runCatching {
            ShortcutManagerCompat.updateShortcuts(context, shortcuts)
        }.isSuccess
    }

}