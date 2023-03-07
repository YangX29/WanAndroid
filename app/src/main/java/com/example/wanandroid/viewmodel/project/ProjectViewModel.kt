package com.example.wanandroid.viewmodel.project

import com.example.wanandroid.base.BaseViewModel
import com.example.wanandroid.model.Category

/**
 * @author: Yang
 * @date: 2023/3/7
 * @description: 项目页面ViewModel
 */
class ProjectViewModel : BaseViewModel<ProjectViewState, ProjectViewIntent>() {

    override fun handleIntent(viewIntent: ProjectViewIntent) {
        if (viewIntent is ProjectViewIntent.InitTab) {
            initTab()
        }
    }

    /**
     * 初始化公众号列表
     */
    private fun initTab() {
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
                updateViewState(ProjectViewState(ProjectViewStatus.InitFinish, it))
            }
        }, {
            updateViewState(ProjectViewState(ProjectViewStatus.InitFailed))
        })
    }

}