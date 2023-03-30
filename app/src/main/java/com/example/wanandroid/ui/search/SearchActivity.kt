package com.example.wanandroid.ui.search

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.module_common.utils.extension.invisible
import com.example.module_common.utils.extension.visible
import com.example.wanandroid.R
import com.example.wanandroid.base.BaseMVIActivity
import com.example.wanandroid.common.RoutePath
import com.example.wanandroid.databinding.ActivitySearchBinding
import com.example.wanandroid.databinding.LayoutSearchBarBinding
import com.example.wanandroid.viewmodel.search.SearchViewIntent
import com.example.wanandroid.viewmodel.search.SearchViewModel
import com.example.wanandroid.viewmodel.search.SearchViewState

/**
 * @author: Yang
 * @date: 2023/3/21
 * @description: 搜索页
 */
@Route(path = RoutePath.SEARCH)
class SearchActivity :
    BaseMVIActivity<ActivitySearchBinding, SearchViewState, SearchViewIntent, SearchViewModel>() {

    override val viewModel: SearchViewModel by viewModels()
    private lateinit var etSearch: EditText
    private lateinit var ivClear: ImageView

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
            //清除
            this@SearchActivity.ivClear = ivClear
            ivClear.setOnClickListener { clearEt() }
            //搜索框
            this@SearchActivity.etSearch = etSearch
            etSearch.addTextChangedListener {
                ivClear.visible(!it.isNullOrEmpty())
            }
            etSearch.setOnEditorActionListener { _, actionId, _ ->
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
     * 清空搜索框
     */
    private fun clearEt() {
        ivClear.invisible()
        etSearch.text.clear()
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
        val key = etSearch.text.toString().trim()
        sendIntent(SearchViewIntent.Search(key))
    }

    /**
     * 搜索
     */
    private fun search(key: String) {
        if (key.isBlank()) return
        //修改搜索框
        etSearch.setText(key)
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