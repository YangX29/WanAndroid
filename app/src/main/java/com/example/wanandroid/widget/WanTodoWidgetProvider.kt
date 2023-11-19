package com.example.wanandroid.widget

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.View
import android.widget.RemoteViews
import com.example.modele_net.scope_v1.ApiProvider
import com.example.module_common.utils.extension.getStringRes
import com.example.wanandroid.R
import com.example.wanandroid.net.WanAndroidApi
import com.example.wanandroid.ui.auth.AuthActivity
import com.example.wanandroid.ui.todo.TodoActivity
import com.example.wanandroid.ui.todo.TodoEditActivity
import com.example.wanandroid.utils.toast.ToastUtils
import com.example.wanandroid.utils.user.UserManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar

/**
 * @author: Yang
 * @date: 2023/11/5
 * @description: WanAndroid的待办清单Todo小组件
 */
class WanTodoWidgetProvider : AppWidgetProvider() {

    //协程实例
    private val scope: CoroutineScope by lazy { CoroutineScope(SupervisorJob() + Dispatchers.IO) }

    //接口服务对象
    private val apiService by lazy { ApiProvider.api(WanAndroidApi::class.java) }

    companion object {
        //刷新小组件
        const val ACTION_TODO_UPDATE = "com.example.wanandroid.widget.WanTodoWidgetProvider.UPDATE"

        //刷新按钮点击事件
        const val ACTION_TODO_REFRESH =
            "com.example.wanandroid.widget.WanTodoWidgetProvider.REFRESH"

        //列表Item点击事件
        const val ACTION_TODO_ITEM =
            "com.example.wanandroid.widget.WanTodoWidgetProvider.ITEM_CLICK"

        //新增todo小组件到桌面
        const val ACTION_ADD_WIDGET =
            "com.example.wanandroid.widget.WanTodoWidgetProvider.ADD_WIDGET"

        //列表点击事件数据
        const val EXTRA_ITEM_CLICK_ID = "item_click_id"
        const val EXTRA_ITEM_CLICK_STATUS = "item_click_status"
        const val EXTRA_ITEM_CLICK_VIEW_ID = "item_click_view_id"


        /**
         * 更新小组件
         */
        fun updateWidget(context: Context) {
            context.sendBroadcast(Intent(context, WanTodoWidgetProvider::class.java).apply {
                action = ACTION_TODO_UPDATE
                data = Uri.parse(toUri(Intent.URI_INTENT_SCHEME))
            })
        }
    }

    override fun onReceive(context: Context, intent: Intent) {
        when (intent.action) {
            ACTION_TODO_ITEM -> {//点击列表Item
                clickItemAction(context, intent)
            }

            ACTION_TODO_REFRESH -> {//点击刷新
                refreshAction(context, intent)
            }

            ACTION_TODO_UPDATE -> {//更新所有todo小组件
                refreshAllTodoWidgetAction(context, intent)
            }

            ACTION_ADD_WIDGET -> {//新增todo小组件到桌面成功
                addWidgetAction(context, intent)
            }
        }
        super.onReceive(context, intent)
    }

    override fun onUpdate(
        context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray
    ) {
        //获取数据并更新小组件
        getDataAndUpdateWidget(context, appWidgetManager, appWidgetIds)
    }

    /**
     * 新增小组件成功.[WanWidgetManager.addTodoWidgetToScreen]
     */
    private fun addWidgetAction(context: Context, intent: Intent) {
        val appWidgetId = intent.getIntExtra(
            AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID
        )
        ToastUtils.show(context, getStringRes(R.string.todo_widget_add_success, appWidgetId))
    }

    /**
     * 点击Todo列表item
     */
    private fun clickItemAction(context: Context, intent: Intent) {
        val viewId = intent.getIntExtra(EXTRA_ITEM_CLICK_VIEW_ID, 0)
        val todoId = intent.getLongExtra(EXTRA_ITEM_CLICK_ID, 0L)
        val status = intent.getIntExtra(EXTRA_ITEM_CLICK_STATUS, 0)
        //点击事件
        if (viewId == R.id.ivChecked) {
            //修改todo状态
            todoStatusChange(context, todoId, status)
        } else {
            //TODO 跳转到todo列表
            context.startActivity(Intent(context, TodoActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
            })
        }
    }

    /**
     * 刷新所有Todo小组件
     */
    private fun refreshAllTodoWidgetAction(context: Context, intent: Intent) {
        AppWidgetManager.getInstance(context).let {
            val ids = it.getAppWidgetIds(
                ComponentName(
                    context, WanTodoWidgetProvider::class.java
                )
            )
            getDataAndUpdateWidget(context, it, ids)
        }
    }

    /**
     * 刷新小组件
     */
    private fun refreshAction(context: Context, intent: Intent) {
        val appWidgetId = intent.getIntExtra(
            AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID
        )
        val appWidgetManager = AppWidgetManager.getInstance(context)
        getDataAndUpdateWidget(context, appWidgetManager, intArrayOf(appWidgetId))
    }

    /**
     * 更新todo状态
     */
    private fun todoStatusChange(context: Context, id: Long, status: Int) {
        scope.launch {
            //更新状态
            apiService.updateTodoStatus(id, if (status == 1) 0 else 1)
                .takeIf { it.isSuccess() }?.run {
                    //更新所有todo列表
                    AppWidgetManager.getInstance(context).let {
                        it.getAppWidgetIds(
                            ComponentName(context, WanTodoWidgetProvider::class.java)
                        ).forEach { id ->
                            it.notifyAppWidgetViewDataChanged(id, R.id.lvTodo)
                        }
                    }
                }
        }
    }

    /**
     * 获取接口数据并更新小组件
     */
    private fun getDataAndUpdateWidget(
        context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray
    ) {
        scope.launch {
            //检查是否登录
            val hasLogin = UserManager.isLogin().first()
            //遍历所有Todo小组件
            appWidgetIds.forEach { appWidgetId ->
                updateTodoWidget(context, appWidgetManager, appWidgetId, hasLogin)
            }
        }
    }

    /**
     * Todo小组件
     */
    @SuppressLint("SimpleDateFormat")
    private fun updateTodoWidget(
        context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int, hasLogin: Boolean
    ) {
        //创建pendingIntent
        val editIntent = Intent(context, TodoEditActivity::class.java).run {
            //singleTop
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
            PendingIntent.getActivity(context, 0, this, PendingIntent.FLAG_UPDATE_CURRENT)
        }
        val loginIntent = Intent(context, AuthActivity::class.java).run {
            //singleTop
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
            PendingIntent.getActivity(context, 0, this, PendingIntent.FLAG_UPDATE_CURRENT)
        }
        //列表点击事件
        val itemClickIntent = Intent(context, WanTodoWidgetProvider::class.java).run {
            action = ACTION_TODO_ITEM
            data = Uri.parse(toUri(Intent.URI_INTENT_SCHEME))
            putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
            PendingIntent.getBroadcast(context, 0, this, PendingIntent.FLAG_UPDATE_CURRENT)
        }
        //刷新列表
        val refreshIntent = Intent(context, WanTodoWidgetProvider::class.java).run {
            action = ACTION_TODO_REFRESH
            data = Uri.parse(toUri(Intent.URI_INTENT_SCHEME))
            putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
            PendingIntent.getBroadcast(context, 0, this, PendingIntent.FLAG_UPDATE_CURRENT)
        }
        //列表远程服务
        val serviceIntent = Intent(context, WanTodoRemoteViewsService::class.java).apply {
            putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
            data = Uri.parse(toUri(Intent.URI_INTENT_SCHEME))
        }
        //获取远程View
        val remoteViews = RemoteViews(
            context.packageName, R.layout.widget_todo
        ).apply {
            //日期
            SimpleDateFormat.getDateInstance()
            setTextViewText(
                R.id.tvDate,
                SimpleDateFormat("yyyy-MM-dd E").format(Calendar.getInstance().time)
            )
            //添加按钮
            setOnClickPendingIntent(R.id.ivAdd, editIntent)
            //登录按钮
            setOnClickPendingIntent(R.id.tvLogin, loginIntent)
            //刷新按钮
            setOnClickPendingIntent(R.id.ivRefresh, refreshIntent)
            //是否登录
            setViewVisibility(R.id.llLogin, if (hasLogin) View.INVISIBLE else View.VISIBLE)
            setViewVisibility(R.id.ivAdd, if (hasLogin) View.VISIBLE else View.INVISIBLE)
            setViewVisibility(R.id.lvTodo, if (hasLogin) View.VISIBLE else View.INVISIBLE)
            if (!hasLogin) {
                setViewVisibility(R.id.tvEmptyHint, View.INVISIBLE)
            } else {
                setViewVisibility(R.id.tvEmptyHint, View.INVISIBLE)
                //列表
                setRemoteAdapter(R.id.lvTodo, serviceIntent)
                //列表点击事件
                setPendingIntentTemplate(R.id.lvTodo, itemClickIntent)
                //空列表
                setEmptyView(R.id.lvTodo, R.id.tvEmptyHint)
            }
        }
        //更新
        appWidgetManager.updateAppWidget(appWidgetId, remoteViews)
        //更新列表
        if (hasLogin) {
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.lvTodo)
        }
    }

}