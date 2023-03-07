package com.example.wanandroid.ui.project

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.example.wanandroid.ui.list.ListPageFragment
import com.example.wanandroid.viewmodel.article.ArticleListViewState
import com.example.wanandroid.viewmodel.project.ProjectListViewModel

/**
 * @author: Yang
 * @date: 2023/3/7
 * @description: 项目列表tab
 */
class ProjectListFragment : ListPageFragment<ArticleListViewState, ProjectListViewModel>() {

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

    override fun onLoadMore(viewState: ArticleListViewState) {
        adapter.addData(viewState.articles ?: mutableListOf())
    }

    override fun onRefresh(viewState: ArticleListViewState) {
        adapter.setList(viewState.articles ?: mutableListOf())
    }

}