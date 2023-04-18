package com.example.wanandroid.mvi.tool

import com.example.wanandroid.model.ListPage
import com.example.wanandroid.model.ToolInfo
import com.example.wanandroid.mvi.list.SimpleListViewModel
import com.example.wanandroid.net.ResponseResult

/**
 * @author: Yang
 * @date: 2023/4/18
 * @description: 工具列表ViewModel
 */
class ToolViewModel : SimpleListViewModel<ToolInfo>() {

    override suspend fun getList(): ResponseResult<ListPage<ToolInfo>> {
        val result = apiService.getToolList()
        return ResponseResult(
            result.errorCode,
            result.errorMsg,
            ListPage(1, result.data ?: mutableListOf(), 0, true, 1, 0, 0)
        )
    }

}