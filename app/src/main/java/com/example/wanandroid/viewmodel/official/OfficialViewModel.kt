package com.example.wanandroid.viewmodel.official

import com.example.wanandroid.viewmodel.tab.TabPageViewModel

/**
 * @author: Yang
 * @date: 2023/3/5
 * @description: 公众号页面ViewModel
 */
class OfficialViewModel : TabPageViewModel() {

    /**
     * 初始化公众号列表
     */
    override suspend fun getTabCategories() = apiService.getOfficialCategories()


}