package com.example.wanandroid.ui.search

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.wanandroid.R
import com.example.wanandroid.base.BaseMVIActivity
import com.example.wanandroid.common.RoutePath
import com.example.wanandroid.databinding.ActivitySearchBinding
import com.example.wanandroid.databinding.LayoutSearchBarBinding
import com.example.wanandroid.mvi.search.SearchViewIntent
import com.example.wanandroid.mvi.search.SearchViewModel
import com.example.wanandroid.mvi.search.SearchViewState
import com.example.wanandroid.view.widget.InputEditText

/**
 * @author: Yang
 * @date: 2023/3/21
 * @description: 搜索页
 */
@Route(path = RoutePath.SEARCH)
class SearchActivity :
    BaseMVIActivity<ActivitySearchBinding, SearchViewState, SearchViewIntent, SearchViewModel>() {

    override val viewModel: SearchViewModel by viewModels()
    private lateinit var etSearch: InputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //初始化
        initView()
    }

    override fun handleViewState(viewState: SearchViewState) {
        when (viewState) {
            is SearchViewState.SearchKey -> {
                search(viewState.key)
            }
            else -> {}
        }
    }

    /**
     * 初始化View
     */
    private fun initView() {
        //toolbar
        mBinding.toolbar.setBar<LayoutSearchBarBinding> {
            //返回
            ivBack.setOnClickListener { onBackPressed() }
            //搜索
            ivSearch.setOnClickListener { searchEt() }
            //搜索框
            this@SearchActivity.etSearch = etSearch
            etSearch.setImeOptions(EditorInfo.IME_ACTION_SEARCH)
            etSearch.setOnEditorActionListener { actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    searchEt()
                    return@setOnEditorActionListener true
                }
                return@setOnEditorActionListener false
            }
        }
        //初始化
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.flContainer, SearchHomeFragment())
        transaction.commit()
    }

    /**
     * 搜索文本框内容
     */
    private fun searchEt() {
        //隐藏键盘
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (imm.isActive) {
            imm.hideSoftInputFromWindow(etSearch.applicationWindowToken, 0);
        }
        //搜索
        val key = etSearch.getInputText().trim()
        sendIntent(SearchViewIntent.Search(key))
    }

    /**
     * 搜索
     */
    private fun search(key: String) {
        if (key.isBlank()) return
        //修改搜索框
        etSearch.setInputText(key)
        etSearch.setSelection(key.length)
        //搜索
        val top = supportFragmentManager.findFragmentById(R.id.flContainer)
        if (top is SearchResultFragment) {//栈顶页为搜索列表
            //重新搜索
            sendIntent(SearchViewIntent.UpdateSearchKey(key))
        } else {
            //跳转到搜索列表
            val transaction = supportFragmentManager.beginTransaction()
            transaction.add(R.id.flContainer, SearchResultFragment.newInstance(key))
            transaction.addToBackStack(SearchResultFragment::class.java.name)
            transaction.hide(top!!)
            transaction.commit()
        }
    }

}