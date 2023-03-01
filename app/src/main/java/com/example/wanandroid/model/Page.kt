package com.example.wanandroid.model

/**
 * @author: Yang
 * @date: 2023/3/1
 * @description: 通用分页模型
 */
data class Page(
    var page: Int,
    var count: Int,
    val pageSize: Int = 40
) {
    //分页结束
    val isFinish: Boolean
        get() = (page >= count)

    /**
     * 更新页数
     */
    fun <T : Any> update(listPage: ListPage<T>) {
        page = listPage.curPage
        count = listPage.pageCount
    }
}
