package com.example.wanandroid.mvi.todo

import com.example.wanandroid.base.mvi.ViewIntent
import com.example.wanandroid.model.TodoModel
import com.example.wanandroid.model.TodoSearchParams

/**
 * @author: Yang
 * @date: 2023/8/13
 * @description: todo页面ViewIntent
 */
sealed class TodoViewIntent : ViewIntent() {
    //刷新
    data class Refresh(val params: TodoSearchParams) : TodoViewIntent()

    //加载更多
    data class LoadMore(val params: TodoSearchParams) : TodoViewIntent()

    //删除
    data class Delete(val id: Long) : TodoViewIntent()

    //修改完成状态
    data class ModifyStatus(val id: Long, val done: Boolean) : TodoViewIntent()

    //添加
    data class AddTodo(val model: TodoModel) : TodoViewIntent()

    //更新
    data class UpdateTodo(val id: Long, val model: TodoModel) : TodoViewIntent()

    //筛选
    data class Filter(val params: TodoSearchParams) : TodoViewIntent()
}
