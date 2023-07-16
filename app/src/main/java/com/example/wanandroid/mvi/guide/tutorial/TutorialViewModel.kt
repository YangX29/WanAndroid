package com.example.wanandroid.mvi.guide.tutorial

import com.example.wanandroid.mvi.guide.CategoryViewModel

/**
 * @author: Yang
 * @date: 2023/3/16
 * @description: 教程ViewModel
 */
class TutorialViewModel : CategoryViewModel() {
    override suspend fun getCategoryList() = apiService.getTutorialList()

}