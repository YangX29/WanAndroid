package com.example.wanandroid.viewmodel.project

import com.example.wanandroid.model.Category
import com.example.wanandroid.viewmodel.tab.TabPageViewModel
import com.example.wanandroid.viewmodel.tab.TabPageViewState
import com.example.wanandroid.viewmodel.tab.TabPageViewStatus

/**
 * @author: Yang
 * @date: 2023/3/7
 * @description: 项目页面ViewModel
 */
class ProjectViewModel : TabPageViewModel() {
    /**
     * 获取项目列表
     */
    override suspend fun getTabCategories() = apiService.getProjectCategories()

    override fun initTab() {
        executeCall({ apiService.getProjectCategories() }, {
            it?.apply {
                if (it.isNotEmpty()) {
                    //添加最新tab
                    it.add(
                        0,
                        it[0].copy(
                            id = Category.TAB_ID_NEWEST_PROJECT,
                            name = Category.TAB_NAME_NEWEST_PROJECT
                        )
                    )
                }
                //更新数据
                updateViewState(TabPageViewState(TabPageViewStatus.InitFinish, it))
            }
        }, {
            updateViewState(TabPageViewState(TabPageViewStatus.InitFailed))
        })
    }

}