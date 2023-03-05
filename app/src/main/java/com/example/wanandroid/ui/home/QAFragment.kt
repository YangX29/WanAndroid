package com.example.wanandroid.ui.home

import androidx.fragment.app.viewModels
import com.example.wanandroid.ui.article.ArticleListFragment
import com.example.wanandroid.viewmodel.home.qa.QAViewModel

/**
 * @author: Yang
 * @date: 2023/2/22
 * @description: 问答页面
 */
class QAFragment : ArticleListFragment<QAViewModel>() {

    override val viewModel: QAViewModel by viewModels()

}