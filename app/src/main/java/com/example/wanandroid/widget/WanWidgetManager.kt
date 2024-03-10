package com.example.wanandroid.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import com.example.module_common.utils.app.ContextUtils
import com.example.wanandroid.common.LiveEventKey
import com.example.wanandroid.model.TodoInfo
import com.example.wanandroid.utils.user.UserManager
import com.jeremyliao.liveeventbus.LiveEventBus
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

/**
 * @author: Yang
 * @date: 2023/11/19
 * @description: WanAndroid小组件管理器
 */
object WanWidgetManager {

    private val scope by lazy { MainScope() }
    private val appWidgetManager by lazy { AppWidgetManager.getInstance(ContextUtils.getApp()) }


    /**
     * 初始化
     */
    fun initWidget() {
        //监听应用状态
        observeLoginState()
        observeTodoWidget()
    }

    /**
     * 是否支持动态添加小组件
     */
    fun isSupportPinAppWidget(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            appWidgetManager.isRequestPinAppWidgetSupported
        } else {
            false
        }
    }

    /**
     * 添加Todo小组件到桌面
     */
    fun addTodoWidgetToScreen(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (appWidgetManager.isRequestPinAppWidgetSupported) {
                val callbackIntent = Intent(context, WanTodoWidgetProvider::class.java).run {
                    action = WanTodoWidgetProvider.ACTION_ADD_WIDGET
                    data = Uri.parse(toUri(Intent.URI_INTENT_SCHEME))
                    PendingIntent.getBroadcast(context, 0, this, PendingIntent.FLAG_UPDATE_CURRENT)
                }
                appWidgetManager.requestPinAppWidget(
                    ComponentName(context, WanTodoWidgetProvider::class.java), null, callbackIntent
                )
            }
        }
    }

    /**
     * 观察登录状态
     */
    private fun observeLoginState() {
        scope.launch {
            UserManager.loginState.collect {
                //登录状态变化，刷新所有小组件状态
                updateAllWidget()
            }
        }
    }

    /**
     * 观察todo数据变化，更新todo小组件
     */
    private fun observeTodoWidget() {
        //更新
        LiveEventBus.get<TodoInfo>(LiveEventKey.KEY_TODO_UPDATE).observeForever {
            updateTodoWidget()
        }
        //删除
        LiveEventBus.get<Long>(LiveEventKey.KEY_TODO_DELETE).observeForever {
            updateTodoWidget()
        }
        //添加
        LiveEventBus.get<TodoInfo>(LiveEventKey.KEY_TODO_ADD).observeForever {
            updateTodoWidget()
        }
    }

    /**
     * 更新Todo小组件
     */
    private fun updateTodoWidget() {
        WanTodoWidgetProvider.updateWidget(ContextUtils.getApp())
    }

    /**
     * 更新所有小组件
     */
    private fun updateAllWidget() {
        updateTodoWidget()
    }

}