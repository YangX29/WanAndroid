package com.example.wanandroid.viewmodel.official

import com.example.wanandroid.base.BaseViewModel

/**
 * @author: Yang
 * @date: 2023/3/5
 * @description: 公众号页面ViewModel
 */
class OfficialViewModel : BaseViewModel<OfficialViewState, OfficialViewIntent>() {

    override fun handleIntent(viewIntent: OfficialViewIntent) {
        if (viewIntent is OfficialViewIntent.InitTab) {
            initTab()
        }
    }

    /**
     * 初始化公众号列表
     */
    private fun initTab() {
        executeCall({ apiService.getOfficialList() }, {
            it?.apply {
                updateViewState(OfficialViewState(OfficialViewStatus.InitFinish, it))
            }
        }, {
            updateViewState(OfficialViewState(OfficialViewStatus.InitFailed))
        })
    }

}