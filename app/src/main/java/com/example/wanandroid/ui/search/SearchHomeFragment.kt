package com.example.wanandroid.ui.search

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.module_common.utils.extension.invisible
import com.example.module_common.utils.extension.visible
import com.example.wanandroid.base.BaseMVIFragment
import com.example.wanandroid.databinding.FragmentSearchHomeBinding
import com.example.wanandroid.model.HotKey
import com.example.wanandroid.mvi.search.SearchViewIntent
import com.example.wanandroid.mvi.search.SearchViewModel
import com.example.wanandroid.mvi.search.SearchViewState
import com.example.wanandroid.mvi.search.home.SearchHomeViewIntent
import com.example.wanandroid.mvi.search.home.SearchHomeViewModel
import com.example.wanandroid.mvi.search.home.SearchHomeViewState
import com.google.android.flexbox.FlexboxLayoutManager

/**
 * @author: Yang
 * @date: 2023/3/22
 * @description: 搜索首页，热词和历史记录
 */
class SearchHomeFragment :
    BaseMVIFragment<FragmentSearchHomeBinding, SearchHomeViewState, SearchHomeViewIntent, SearchHomeViewModel>() {

    override val viewModel: SearchHomeViewModel by viewModels()

    //搜索viewModel
    private val searchViewModel: SearchViewModel by activityViewModels()

    //热词adapter
    private val hotKeyAdapter by lazy { SearchKeyAdapter() }

    //历史记录adapter
    private val historyAdapter by lazy { SearchKeyAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //初始化
        initView()
        //初始化数据
        initData()
        //监听search事件
        handleSearchViewState()
    }

    override fun handleViewState(viewState: SearchHomeViewState) {
        when (viewState) {
            is SearchHomeViewState.InitSuccess -> {
                setUpData(viewState.hotKeys, viewState.histories)
            }
            is SearchHomeViewState.InitFailed -> {
                mBinding.loading.invisible()
            }
            is SearchHomeViewState.ClearSuccess -> {
                //清空
                historyAdapter.setList(mutableListOf())
            }
        }
    }

    /**
     * 处理searchViewModel的viewState
     */
    private fun handleSearchViewState() {
        handleViewState(searchViewModel) {
            if (it is SearchViewState.UpdateHistory) {
                addHistory(it.key)
            }
        }
    }

    /**
     * 初始化
     */
    private fun initView() {
        //热门搜索
        mBinding.rvHotKey.apply {
            layoutManager = FlexboxLayoutManager(requireContext())
            adapter = hotKeyAdapter
        }
        hotKeyAdapter.setOnItemClickListener { _, _, position ->
            search(hotKeyAdapter.data[position])
        }
        //历史记录
        mBinding.rvHistory.apply {
            layoutManager = FlexboxLayoutManager(requireContext())
            adapter = historyAdapter
        }
        historyAdapter.setOnItemClickListener { _, _, position ->
            search(historyAdapter.data[position])
        }
        //清除数据
        mBinding.tvClear.setOnClickListener { clear() }
    }


    /**
     * 初始化数据
     */
    private fun initData() {
        sendViewIntent(SearchHomeViewIntent.InitData)
    }

    /**
     * 清除历史记录
     */
    private fun clear() {
        sendViewIntent(SearchHomeViewIntent.ClearHistory)
    }

    /**
     * 搜索
     */
    private fun search(key: String) {
        sendViewIntent(searchViewModel, SearchViewIntent.Search(key))
    }

    /**
     * 设置数据
     */
    private fun setUpData(hotKeys: MutableList<HotKey>, history: MutableList<String>) {
        //热门搜索
        val hotKeyList = hotKeys.map { it.name }.toMutableList()
        hotKeyAdapter.setNewInstance(hotKeyList)
        //历史记录
        historyAdapter.setNewInstance(history)
        //隐藏loading，显示数据
        mBinding.loading.invisible()
        mBinding.clContainer.visible()
    }

    /**
     * 添加历史记录
     */
    private fun addHistory(key: String) {
        //加历史记录，如果已存在，调整位置到最前面
        if (historyAdapter.data.contains(key)) {
            historyAdapter.remove(key)
        }
        historyAdapter.addData(0, key)
    }

}