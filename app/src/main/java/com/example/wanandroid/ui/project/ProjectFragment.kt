package com.example.wanandroid.ui.project

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.wanandroid.ui.tab.TabPageFragment
import com.example.wanandroid.viewmodel.project.ProjectViewModel

/**
 * @author: Yang
 * @date: 2023/2/21
 * @description: 项目页
 */
class ProjectFragment : TabPageFragment<ProjectViewModel>() {

    override val viewModel: ProjectViewModel by viewModels()

    override fun createPageFragment(position: Int): Fragment {
        return ProjectListFragment.newInstance(tabs[position].id)
    }

}