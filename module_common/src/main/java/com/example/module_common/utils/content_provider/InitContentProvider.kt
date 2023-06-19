package com.example.module_common.utils.content_provider

import android.app.Application
import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import com.example.module_common.utils.app.ContextUtil
import com.example.module_common.utils.lifecycle.AppLifecycleManager
import com.example.module_common.utils.sp.SPUtils

/**
 * @author: Yang
 * @date: 2023/5/10
 * @description: 初始化ContentProvider，用于自动初始化
 */
class InitContentProvider : ContentProvider() {

    /**
     * 初始化工具类
     */
    private fun initLibraries() {
        context?.let {
            //初始化context
            ContextUtil.init(it)
            //SP工具类
            SPUtils.init(it)
            //初始化生命周期管理类
            AppLifecycleManager.register(it.applicationContext as Application)
        }
    }

    override fun onCreate(): Boolean {
        //初始化
        initLibraries()
        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        return null
    }

    override fun getType(uri: Uri): String? {
        return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        return null
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        return 0
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        return 0
    }
}