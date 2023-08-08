package com.example.wanandroid.ui.project

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.alibaba.android.arouter.launcher.ARouter
import com.example.wanandroid.R
import com.example.wanandroid.common.RoutePath
import com.example.wanandroid.model.Article
import com.example.wanandroid.mvi.project.ProjectListViewModel
import com.example.wanandroid.ui.list.SimpleListFragment
import com.example.wanandroid.ui.web.WebActivity

/**
 * @author: Yang
 * @date: 2023/3/7
 * @description: 项目列表tab
 */
class ProjectListFragment : SimpleListFragment<Article, ProjectListViewModel>() {

    companion object {

        private const val PROJECT_CID = "cid"

        //实例化Fragment对象
        fun newInstance(id: Int): ProjectListFragment {
            val fragment = ProjectListFragment()
            val bundle = Bundle()
            bundle.putInt(PROJECT_CID, id)
            fragment.arguments = bundle
            return fragment
        }
    }

    //项目分类id
    private var cid = 0

    override val adapter = ProjectListAdapter()
    override val viewModel: ProjectListViewModel by viewModels {
        ProjectListViewModel.Factory(cid)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //获取id
        cid = arguments?.getInt(PROJECT_CID) ?: 0
    }

    override fun onItemClick(position: Int) {
        val article = adapter.data.getOrNull(position) ?: return
        ARouter.getInstance().build(RoutePath.WEB)
            .withString(WebActivity.WEB_URL, article.link)
            .navigation()
    }

    override fun onItemChildClick(view: View, position: Int) {
        val article = adapter.data.getOrNull(position) ?: return
        if (view.id == R.id.ivCollect) {
            viewModel.collectArticle(!article.collect, article.id)
        }
    }

}