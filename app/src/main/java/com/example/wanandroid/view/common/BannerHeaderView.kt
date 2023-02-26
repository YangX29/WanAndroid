package com.example.wanandroid.view.common

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import coil.load
import com.alibaba.android.arouter.launcher.ARouter
import com.example.wanandroid.R
import com.example.wanandroid.common.RoutePath
import com.example.wanandroid.databinding.HeaderHomeBannerBinding
import com.example.wanandroid.model.Banner
import com.example.wanandroid.ui.web.WebActivity
import com.example.wanandroid.utils.extension.loadWithDefault
import com.youth.banner.adapter.BannerImageAdapter
import com.youth.banner.holder.BannerImageHolder
import com.youth.banner.indicator.CircleIndicator

/**
 * @author: Yang
 * @date: 2023/2/26
 * @description: 首页轮播图Header
 */
class BannerHeaderView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val mBinding: HeaderHomeBannerBinding
    private val mAdapter = BannerAdapter(listOf())

    init {
        val root = View.inflate(context, R.layout.header_home_banner, this)
        mBinding = HeaderHomeBannerBinding.bind(root)
        //初始化
        initView()
    }

    /**
     * 初始化View
     */
    private fun initView() {
        //banner
        mBinding.banner.apply {
            //adapter
            setAdapter(mAdapter)
            //可滑动
            setUserInputEnabled(true)
            //指示器
            indicator = CircleIndicator(context)
            //指示器选中颜色
            setIndicatorSelectedColorRes(R.color.banner_indicator_selected)
            //指示器默认颜色
            setIndicatorNormalColorRes(R.color.banner_indicator_unselected)
            //自动轮播
            isAutoLoop(true)
            //点击banner事件
            setOnBannerListener { _, position -> clickBanner(position) }
            //TODO 处理生命周期
        }
    }

    /**
     * 点击banner
     */
    private fun clickBanner(position: Int) {
        val banner = mAdapter.getData(position)
        //跳转到对应页面
        ARouter.getInstance().build(RoutePath.WEB)
            .withString(WebActivity.WEB_URL, banner.url)
            .navigation()
    }

    /**
     * banner点击事件
     */
    fun setOnBannerClick(callback: (Int) -> Unit) {

    }

    /**
     * 设置Banner数据
     */
    fun setBanner(banners: List<Banner>) {
        mAdapter.setDatas(banners)
    }

    /**
     * 轮播图Adapter
     */
    private inner class BannerAdapter(data: List<Banner>) : BannerImageAdapter<Banner>(data) {

        override fun onBindView(
            holder: BannerImageHolder,
            data: Banner,
            position: Int,
            size: Int
        ) {
            holder.imageView?.loadWithDefault(data.imagePath)
        }

    }

}