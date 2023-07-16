package com.example.wanandroid.mvi.guide.tree

import com.example.wanandroid.model.Category
import com.example.wanandroid.mvi.guide.CategoryViewModel
import com.example.wanandroid.net.ResponseResult

/**
 * @author: Yang
 * @date: 2023/3/16
 * @description: 学习路径ViewModel
 */
class TreeViewModel : CategoryViewModel() {
    override suspend fun getCategoryList(): ResponseResult<MutableList<Category>> {
        return apiService.getSystemCategory().apply {
            //过滤学习路线的类型
            data?.removeAll { !it.isTree() }
        }
    }

}