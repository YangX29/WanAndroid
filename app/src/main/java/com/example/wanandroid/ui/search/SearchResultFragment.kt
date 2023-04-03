package com.example.wanandroid.ui.search

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.wanandroid.ui.article.ArticleListFragment
import com.example.wanandroid.mvi.search.SearchViewModel
import com.example.wanandroid.mvi.search.SearchViewState
import com.example.wanandroid.mvi.search.result.SearchResultViewModel

/**
 * @author: Yang
 * @date: 2023/3/22
 * @description: 搜索结果页面
 */
class SearchResultFragment : ArticleListFragment<SearchResultViewModel>() {

    companion object {
        const val SEARCH_KEY = "search_key"

        /**
         * 创建Fragment
         */
        fun newInstance(key: String): SearchResultFragment {
            val fragment = SearchResultFragment()
            val args = Bundle()
            args.putString(SEARCH_KEY, key)
            fragment.arguments = args
            return fragment
        }
    }

    override val viewModel: SearchResultViewModel by viewModels {
        SearchResultViewModel.Factory(key)
    }

    //搜索viewModel
    private val searchViewModel: SearchViewModel by activityViewModels()

    //搜索关键词
    private var key = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        key = arguments?.getString(SEARCH_KEY) ?: ""
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //处理searchViewModel
        handleSearchViewState()
    }

    /**
     * 处理SearchViewModel
     */
    private fun handleSearchViewState() {
        handleViewState(searchViewModel) {
            if (it is SearchViewState.RefreshSearchList) {
                search(it.key)
            }
        }
    }

    /**
     * 搜索
     */
    private fun search(key: String) {
        if (this.key == key) return
        this.key = key
        mBinding.swipeRefresh.isRefreshing = true
        //TODO 使用ViewIntent
        viewModel.searchWithKey(key)
    }
}