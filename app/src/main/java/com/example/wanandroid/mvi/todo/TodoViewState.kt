package com.example.wanandroid.mvi.todo

import com.example.wanandroid.base.mvi.ViewState
import com.example.wanandroid.model.TodoInfo

/**
 * @author: Yang
 * @date: 2023/8/13
 * @description: todo页面ViewState
 */
sealed class TodoViewState : ViewState() {

    //刷新
    data class RefreshSuccess(val isFinish: Boolean, val data: MutableList<TodoInfo>) :
        TodoViewState()

    //加载更多成功
    data class LoadMoreSuccess(val isFinish: Boolean, val data: MutableList<TodoInfo>) :
        TodoViewState()

    //更新失败
    object RefreshFailed : TodoViewState()

    //加载更多失败
    object LoadMoreFailed : TodoViewState()

    //删除任务
    data class DeleteTodo(val id: Long) : TodoViewState()

    //任务完成状态修改
    data class DoneTodo(val done: Boolean, val id: Long) : TodoViewState()

    //添加任务
    data class AddTodo(val info: TodoInfo) : TodoViewState()

    //更新任务
    data class UpdateTodo(val info: TodoInfo) : TodoViewState()

}