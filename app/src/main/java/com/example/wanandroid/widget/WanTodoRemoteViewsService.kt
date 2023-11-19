package com.example.wanandroid.widget

import android.appwidget.AppWidgetManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import com.example.modele_net.scope_v1.ApiProvider
import com.example.module_common.utils.log.logE
import com.example.wanandroid.R
import com.example.wanandroid.model.TodoInfo
import com.example.wanandroid.net.WanAndroidApi
import kotlinx.coroutines.runBlocking

/**
 * @author: Yang
 * @date: 2023/11/5
 * @description: WanAndroid待办事件小组件service
 */
class WanTodoRemoteViewsService : RemoteViewsService() {

    override fun onGetViewFactory(intent: Intent): RemoteViewsFactory {
        return WanTodoRemoveViewsFactory(applicationContext, intent)
    }

}

/**
 * @author: Yang
 * @date: 2023/11/5
 * @description: WanAndroid待办事件列表适配器
 */
internal class WanTodoRemoveViewsFactory(
    context: Context, intent: Intent
) : RemoteViewsService.RemoteViewsFactory {

    companion object {
        private const val TAG = "WanTodoRemoveViewsFactory"
    }

    private val mContext = context
    private val mAppWidgetId = intent.getIntExtra(
        AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID
    )

    //todo列表
    private var todoList = mutableListOf<TodoInfo>()

    //接口服务对象
    private val apiService by lazy { ApiProvider.api(WanAndroidApi::class.java) }

    override fun onCreate() {
        logE(TAG, "onCreate")
    }

    override fun onDataSetChanged() {
        //获取TODO数据
        todoList = runBlocking {
            val list = mutableListOf<TodoInfo>()
            var isFinish = false
            var page = 0
            while (!isFinish) {
                apiService.getTodoList(page).apply {
                    isFinish = data?.isFinish ?: true
                    page = (data?.curPage ?: 0) + 1
                    list.addAll(data?.list ?: mutableListOf())
                }
            }
            list
        }
    }

    override fun getViewAt(position: Int): RemoteViews {
        return RemoteViews(mContext.packageName, R.layout.item_widget_todo_list).apply {
            val todoInfo = todoList[position]
            //待办事件名称
            setTextViewText(R.id.tvTitle, todoInfo.title)
            //日期
            setTextViewText(R.id.tvDate, todoInfo.dateStr)
            //是否选中
//            setBoolean(R.id.ivChecked, "setSelected", todoInfo.isDone)
            setImageViewResource(
                R.id.ivChecked,
                if (todoInfo.isDone) R.drawable.icon_checked_circle else R.drawable.icon_unchecked_circle
            )
            //点击事件
            val checkIntent = Intent().apply {
                putExtras(Bundle().also {
                    it.putInt(WanTodoWidgetProvider.EXTRA_ITEM_CLICK_VIEW_ID, R.id.ivChecked)
                    it.putLong(WanTodoWidgetProvider.EXTRA_ITEM_CLICK_ID, todoInfo.id)
                    it.putInt(WanTodoWidgetProvider.EXTRA_ITEM_CLICK_STATUS, todoInfo.status)
                })
            }
            setOnClickFillInIntent(R.id.ivChecked, checkIntent)
            //item点击事件
            val itemIntent = Intent().apply {
                putExtras(Bundle().also {
                    it.putInt(WanTodoWidgetProvider.EXTRA_ITEM_CLICK_VIEW_ID, R.id.llItem)
                    it.putLong(WanTodoWidgetProvider.EXTRA_ITEM_CLICK_ID, todoInfo.id)
                })
            }
            setOnClickFillInIntent(R.id.llItem, itemIntent)
        }
    }

    override fun getLoadingView(): RemoteViews? {
        return null
    }

    override fun onDestroy() {
        todoList.clear()
    }

    override fun getCount(): Int {
        return todoList.size
    }

    override fun getViewTypeCount(): Int {
        return 1
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun hasStableIds(): Boolean {
        return true
    }


}