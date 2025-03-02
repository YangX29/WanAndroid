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

    //创建待办事件快捷方式
    const val SHORTCUT_ADD_TODO = "shortcut_add_todo"

    //待办事件列表快捷方式
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
        return requestPinShortcut(
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
        return getPinedShortcuts().find {
            it.id == "${SHORTCUT_ARTICLE}_$id"
        }.isNotNull()
    }

    /**
     * 发布创建待办事件快捷方式
     */
    fun pushTodoCreatorShortcut(): Boolean {
        val todoIntent = Intent(context, TodoEditActivity::class.java).apply {
            action = Intent.ACTION_VIEW
        }
        return pushDynamicShortcut(
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
     * 添加待办列表快捷方式
     */
    fun pushTodoListShortcut(): Boolean {
        val todoListIntent = Intent(context, TodoActivity::class.java).apply {
            action = Intent.ACTION_VIEW
        }
        return pushDynamicShortcut(
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
    fun pushCollectionShortcut(): Boolean {
        val collectionIntent = Intent(context, CollectionActivity::class.java).apply {
            action = Intent.ACTION_VIEW
        }
        return pushDynamicShortcut(
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
     * 通过id获取快捷方式信息
     */
    fun getShortcutById(id: String): ShortcutInfoCompat? {
        return ShortcutManagerCompat.getShortcuts(
            context,
            FLAG_MATCH_PINNED or FLAG_MATCH_DYNAMIC or FLAG_MATCH_MANIFEST
        ).find { it.id == id }
    }

    /**
     * 获取所有固定快捷方式
     */
    fun getPinedShortcuts(): MutableList<ShortcutInfoCompat> {
        return ShortcutManagerCompat.getShortcuts(context, FLAG_MATCH_PINNED)
    }

    /**
     * 获取所有动态快捷方式
     */
    fun getDynamicShortcuts(): MutableList<ShortcutInfoCompat> {
        return ShortcutManagerCompat.getShortcuts(context, FLAG_MATCH_DYNAMIC)
    }

    /**
     * 获取所有manifest静态快捷方式
     */
    fun getMainfestShortcuts(): MutableList<ShortcutInfoCompat> {
        return ShortcutManagerCompat.getShortcuts(context, FLAG_MATCH_MANIFEST)
    }

    /**
     * 获取最大动态快捷方式数量
     */
    fun getMaxShortcutCount(context: Context): Int {
        return ShortcutManagerCompat.getMaxShortcutCountPerActivity(context)
    }

    /**
     * 判断动态快捷方式是否存在
     */
    fun hasShortcut(id: String): Boolean {
        return ShortcutManagerCompat.getShortcuts(
            context,
            FLAG_MATCH_PINNED or FLAG_MATCH_DYNAMIC or FLAG_MATCH_MANIFEST
        ).find {
            it.id == "${SHORTCUT_ARTICLE}_$id"
        }.isNotNull()
    }

    /**
     * 移除收藏列表快捷方式
     */
    fun removeDynamicShortcut(id: String): Boolean {
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
     * 移除动态快捷方式
     */
    fun removeDynamicShortcut(ids: List<String>): Boolean {
        return kotlin.runCatching {
            ShortcutManagerCompat.removeDynamicShortcuts(context, ids)
        }.isSuccess
    }

    /**
     * 移除所有动态快捷方式
     */
    fun removeAllDynamicShortcuts() {
        ShortcutManagerCompat.removeAllDynamicShortcuts(context)
    }

    /**
     * 移除长生命周期快捷方式
     */
    fun removeLongLivedShortcuts(ids: List<String>): Boolean {
        return kotlin.runCatching {
            ShortcutManagerCompat.removeLongLivedShortcuts(context, ids)
        }.isSuccess
    }

    /**
     * 发布动态快捷方式到app启动图标上，如果已存在会更新
     */
    private fun pushDynamicShortcut(shortcutInfoCompat: ShortcutInfoCompat): Boolean {
        return kotlin.runCatching {
            ShortcutManagerCompat.pushDynamicShortcut(context, shortcutInfoCompat)
        }.isSuccess
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
    private fun requestPinShortcut(shortcutInfoCompat: ShortcutInfoCompat): Boolean {
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