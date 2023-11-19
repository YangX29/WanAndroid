package com.example.wanandroid.ui.todo

import android.app.Activity
import android.appwidget.AppWidgetManager
import android.content.Intent
import android.os.Bundle
import android.widget.RemoteViews
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.wanandroid.R
import com.example.wanandroid.base.BaseActivity
import com.example.wanandroid.common.RoutePath
import com.example.wanandroid.databinding.ActivityTodoWidgetConfigBinding

/**
 * @author: Yang
 * @date: 2023/11/5
 * @description: Todo小组件配置页面
 */
@Route(path = RoutePath.TODO_WIDGET_CONFIG)
class TodoWidgetConfigActivity : BaseActivity<ActivityTodoWidgetConfigBinding>() {

    private val appWidgetId by lazy {
        intent?.getIntExtra(
            AppWidgetManager.EXTRA_APPWIDGET_ID,
            AppWidgetManager.INVALID_APPWIDGET_ID
        ) ?: AppWidgetManager.INVALID_APPWIDGET_ID
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private fun updateWidget() {
        val appWidgetManager = AppWidgetManager.getInstance(this)
        RemoteViews(packageName, R.layout.widget_todo).also { views ->
            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }

    private fun saveConfig() {
        mBinding.root.isSelected = false
        val resultValue = Intent().apply {
            putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
        }
        setResult(Activity.RESULT_OK, resultValue)
        finish()
    }

}