package com.example.wanandroid.ui.official

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.example.wanandroid.ui.article.ArticleListFragment
import com.example.wanandroid.mvi.official.OfficialListViewModel

/**
 * @author: Yang
 * @date: 2023/3/5
 * @description: 公众号列表Fragment
 */
class OfficialListFragment : ArticleListFragment<OfficialListViewModel>() {

    override val viewModel: OfficialListViewModel by viewModels {
        OfficialListViewModel.Factory(id)
    }

    private var id = 0

    companion object {

        private const val OFFICIAL_ID = "id"

        //实例化Fragment对象
        fun newInstance(id: Int): OfficialListFragment {
            val fragment = OfficialListFragment()
            val bundle = Bundle()
            bundle.putInt(OFFICIAL_ID, id)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //获取id
        id = arguments?.getInt(OFFICIAL_ID) ?: 0
    }

}