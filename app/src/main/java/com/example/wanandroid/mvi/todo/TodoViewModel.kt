package com.example.wanandroid.mvi.todo

import com.example.module_common.utils.extension.isNull
import com.example.wanandroid.R
import com.example.wanandroid.base.BaseViewModel
import com.example.wanandroid.base.mvi.ViewEvent
import com.example.wanandroid.common.LiveEventKey
import com.example.wanandroid.model.ListPage
import com.example.wanandroid.model.Page
import com.example.wanandroid.model.TodoInfo
import com.example.wanandroid.model.TodoModel
import com.example.wanandroid.model.TodoSearchParams
import com.example.wanandroid.utils.extension.executeCall
import com.example.wanandroid.utils.extension.launchByIo
import com.example.wanandroid.utils.view.LoadingManager
import com.jeremyliao.liveeventbus.LiveEventBus

/**
 * @author: Yang
 * @date: 2023/8/13
 * @description: todo页面ViewModel
 */
class TodoViewModel : BaseViewModel<TodoViewState, TodoViewIntent>() {

    private var page: Page? = null

    override fun handleIntent(viewIntent: TodoViewIntent) {
        viewIntent.apply {
            when (this) {
                is TodoViewIntent.Refresh -> {//刷新
                    //重置参数
                    page = null
                    getTodoList(params)
                }

                is TodoViewIntent.LoadMore -> {//加载更多
                    getTodoList(params)
                }

                is TodoViewIntent.ModifyStatus -> {//修改完成状态
                    modifyTodoStatus(id, done)
                }

                is TodoViewIntent.Delete -> {//删除
                    deleteTodo(id)
                }

                is TodoViewIntent.AddTodo -> {//添加
                    addTodo(model)
                }

                is TodoViewIntent.UpdateTodo -> {//更新
                    updateTodo(id, model)
                }

                is TodoViewIntent.Filter -> {//过滤搜索
                    //重置分页
                    page = null
                    getTodoList(params)
                }

                is TodoViewIntent.AddOrUpdateCalendarEvent -> {//添加到日历提醒中
                    addOrUpdateCalendarEvent(todo)
                }

                is TodoViewIntent.DeleteCalendarEvent -> {//删除日历事件提醒
                    deleteCalendarEvent(id)
                }

            }
        }
    }

    /**
     * 更新分页
     */
    private fun updatePage(listPage: ListPage<*>) {
        if (page == null) {
            page = Page(listPage.curPage + 1, listPage.pageCount)
        } else {
            page?.update(listPage)
        }
    }

    /**
     * 获取todo列表
     */
    private fun getTodoList(params: TodoSearchParams?) {
        val isRefresh = page.isNull()
        executeCall({
            apiService.getTodoList(
                page?.page ?: 1,
                params?.type, params?.status, params?.priority, params?.orderBy
            )
        }, {
            it?.apply {
                //更新分页
                updatePage(it)
                launchByIo {
                    val list = it.list.mapNotNull {item-> CalendarEventUtils.checkTodoCalendarEvent(item) }
                        .toMutableList()
                    //更新
                    if (isRefresh) {
                        updateViewState(TodoViewState.RefreshSuccess(page?.isFinish ?: false, list))
                    } else {
                        updateViewState(
                            TodoViewState.LoadMoreSuccess(
                                page?.isFinish ?: false,
                                list
                            )
                        )
                    }
                }
            }
        }, {
            //获取失败
            if (isRefresh) {
                updateViewState(TodoViewState.RefreshFailed)
            } else {
                updateViewState(TodoViewState.LoadMoreFailed)
            }
        })
    }

    /**
     * 更新todo
     */
    private fun updateTodo(id: Long, todo: TodoModel) {
        executeCall({
            apiService.updateTodo(
                id,
                todo.title,
                todo.content,
                todo.dateStr,
                todo.type,
                todo.status,
                todo.priority
            )
        }, {
            launchByIo {
                //更新日历提醒
                if (todo.hasSetClock) {
                    addOrUpdateCalendarEvent(todo)
                } else {
                    deleteCalendarEvent(todo.id)
                }
                //添加成功
                CalendarEventUtils.checkTodoCalendarEvent(it)?.apply {
                    updateViewState(TodoViewState.UpdateTodo(this))
                    LiveEventBus.get<TodoInfo>(LiveEventKey.KEY_TODO_UPDATE).post(this)
                }
            }
        }, {
            //添加
            emitViewEvent(ViewEvent.Toast(R.string.common_modify_failed))
        }, true)
    }

    /**
     * 修改任务完成状态
     */
    private fun modifyTodoStatus(id: Long, done: Boolean) {
        executeCall({
            apiService.updateTodoStatus(id, if (done) 1 else 0)
        }, {
            //修改成功
            launchByIo {
                CalendarEventUtils.checkTodoCalendarEvent(it)?.apply {
                    updateViewState(TodoViewState.DoneTodo(isDone, id))
                    LiveEventBus.get<TodoInfo>(LiveEventKey.KEY_TODO_UPDATE).post(this)
                }
            }
        }, {
            //修改失败
            emitViewEvent(ViewEvent.Toast(R.string.common_modify_failed))
        }, true)
    }

    /**
     * 添加Todo
     */
    private fun addTodo(todo: TodoModel) {
        executeCall({
            apiService.addTodo(todo.title, todo.content, todo.dateStr, todo.type, todo.priority)
        }, {
            //添加成功
            launchByIo {
                //更新日历提醒
                if (todo.hasSetClock) {
                    addOrUpdateCalendarEvent(todo)
                }
                CalendarEventUtils.checkTodoCalendarEvent(it)?.apply {
                    updateViewState(TodoViewState.AddTodo(this))
                    LiveEventBus.get<TodoInfo>(LiveEventKey.KEY_TODO_ADD).post(this)
                }
            }
        }, {
            //添加
            emitViewEvent(ViewEvent.Toast(R.string.common_add_failed))
        }, true)
    }

    /**
     * 删除Todo
     */
    private fun deleteTodo(id: Long) {
        executeCall({ apiService.deleteTodo(id) }, {
            launchByIo {
                //删除对应的日历待办
                deleteTodo(id)
                //删除成功
                updateViewState(TodoViewState.DeleteTodo(id))
                LiveEventBus.get<Long>(LiveEventKey.KEY_TODO_DELETE).post(id)
            }
        }, {
            //删除失败
            emitViewEvent(ViewEvent.Toast(R.string.common_delete_failed))
        }, true)
    }

    /**
     * 删除日历事件
     */
    private fun deleteCalendarEvent(id: Long) {
        launchByIo {
            CalendarEventUtils.deleteCalendarEventById(id)
        }
    }

    /**
     * 添加和更新日历事件
     */
    private fun addOrUpdateCalendarEvent(todo: TodoModel) {
        launchByIo {
            //loading
            LoadingManager.showCurrentLoading()
            //更新日历
            CalendarEventUtils.setCalendarEventByTodo(todo)
            //隐藏loading
            LoadingManager.dismissCurrentLoading()
        }
    }

}