package com.example.wanandroid.ui.home

import androidx.fragment.app.viewModels
import com.example.wanandroid.ui.article.ArticleListFragment
import com.example.wanandroid.viewmodel.home.square.SquareViewModel

/**
 * @author: Yang
 * @date: 2023/2/22
 * @description: 广场页面
 */
class SquareFragment : ArticleListFragment<SquareViewModel>() {

    override val viewModel: SquareViewModel by viewModels()

}