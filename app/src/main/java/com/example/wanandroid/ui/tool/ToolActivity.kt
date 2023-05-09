package com.example.wanandroid.ui.tool

import androidx.activity.viewModels
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.example.wanandroid.R
import com.example.wanandroid.common.RoutePath
import com.example.wanandroid.model.ToolInfo
import com.example.wanandroid.mvi.tool.ToolViewModel
import com.example.wanandroid.ui.list.SimpleListActivity
import com.example.wanandroid.ui.web.WebActivity

/**
 * @author: Yang
 * @date: 2023/4/18
 * @description: 工具列表页面
 */
@Route(path = RoutePath.TOOL)
class ToolActivity : SimpleListActivity<ToolInfo, ToolViewModel>() {

    override val viewModel: ToolViewModel by viewModels()

    override val adapter = ToolAdapter()

    override fun showDivider() = false

    override fun canLoadMore() = false

    override fun canRefresh() = false

    override fun getPageTitle(): String {
        return getString(R.string.mine_tool)
    }

    override fun onItemClick(position: Int) {
        //跳转到对应工具页
        ARouter.getInstance().build(RoutePath.WEB)
            .withString(WebActivity.WEB_URL, adapter.data[position].link)
            .navigation()
    }

}