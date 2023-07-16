package com.example.wanandroid.mvi.guide.system

import com.example.wanandroid.mvi.guide.CategoryViewModel

/**
 * @author: Yang
 * @date: 2023/3/16
 * @description: 体系ViewModel
 */
class SystemViewModel : CategoryViewModel() {
    override suspend fun getCategoryList() = apiService.getSystemCategory()

}