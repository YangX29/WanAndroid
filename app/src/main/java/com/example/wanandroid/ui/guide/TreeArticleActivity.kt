package com.example.wanandroid.ui.guide

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.example.wanandroid.R
import com.example.wanandroid.base.BaseActivity
import com.example.wanandroid.common.RoutePath
import com.example.wanandroid.databinding.ActivityTreeArticleBinding
import com.example.wanandroid.model.Category
import com.example.wanandroid.ui.guide.adapter.TreeNodeAdapter
import com.example.wanandroid.ui.guide.model.TreeArticleNode
import com.example.wanandroid.ui.guide.model.TreeGroupNode
import com.example.wanandroid.ui.web.WebActivity
import com.example.wanandroid.utils.extension.loadWithDefault
import com.example.wanandroid.view.widget.SimpleDividerItemDecoration
import com.example.wanandroid.view.widget.helper.AppbarSateChangeListener
import com.google.android.material.appbar.AppBarLayout

/**
 * @author: Yang
 * @date: 2023/7/19
 * @description: 学习路径详情页面
 */
@Route(path = RoutePath.TREE_ARTICLE)
class TreeArticleActivity : BaseActivity<ActivityTreeArticleBinding>() {

    companion object {
        const val KEY_TREE = "key_tree"
    }

    @Autowired(name = KEY_TREE)
    @JvmField
    var tree: Category? = null

    //列表adapter
    private val adapter = TreeNodeAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //初始化
        initView()
        //初始化数据
        initData()
    }

    /**
     * 初始化数据
     */
    private fun initData() {
        //初始化数据
        tree?.apply {
            //转换为列表结点数据
            val data = children.map {
                TreeGroupNode(
                    it,
                    it.articleList.map { article -> TreeArticleNode(article) }.toMutableList()
                ).apply { isExpanded = false }
            }
            adapter.setNewInstance(data.toMutableList())
        }
    }

    /**
     * 初始化View
     */
    private fun initView() {
        //课程信息
        mBinding.ivCover.loadWithDefault(tree?.cover ?: "")
        mBinding.tvName.text = tree?.name
        mBinding.tvAuthor.text = tree?.author
        mBinding.tvDesc.text = tree?.desc
        mBinding.tvLicense.text = tree?.lisense
        mBinding.tvLicense.setOnClickListener { jumpToLicense() }
        //标题
        mBinding.toolbar.apply {
            setTitle(tree?.name ?: "")
            setOnLeftClick { finish() }

        }
        mBinding.appbar.addOnOffsetChangedListener(object : AppbarSateChangeListener() {
            override fun onStateChanged(appBarLayout: AppBarLayout, state: State) {
                mBinding.toolbar.showTitle(state == State.COLLAPSED)
            }
        })
        //列表
        mBinding.rvArticle.apply {
            adapter = this@TreeArticleActivity.adapter
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(SimpleDividerItemDecoration(context))
        }
        //空布局
        adapter.setEmptyView(R.layout.layout_empty_list)
    }

    /**
     * 更新加载更多状态
     */
    private fun updateLoadMore(finish: Boolean) {
        //修改加载状态
        if (finish) {
            adapter.loadMoreModule.loadMoreEnd()
        } else {
            adapter.loadMoreModule.loadMoreComplete()
        }
    }

    /**
     * 跳转到证书
     */
    private fun jumpToLicense() {
        ARouter.getInstance().build(RoutePath.WEB)
            .withString(WebActivity.WEB_URL, tree?.lisenseLink).navigation()
    }

}