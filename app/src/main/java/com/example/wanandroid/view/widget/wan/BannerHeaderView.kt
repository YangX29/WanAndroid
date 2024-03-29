package com.example.wanandroid.view.widget.wan

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import coil.load
import com.alibaba.android.arouter.launcher.ARouter
import com.example.wanandroid.R
import com.example.wanandroid.common.RoutePath
import com.example.wanandroid.databinding.HeaderHomeBannerBinding
import com.example.wanandroid.databinding.ItemHomeBannerBinding
import com.example.wanandroid.model.Banner
import com.example.wanandroid.ui.web.WebActivity
import com.zhpan.bannerview.BaseBannerAdapter
import com.zhpan.bannerview.BaseViewHolder

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

    private val mBinding = HeaderHomeBannerBinding.inflate(LayoutInflater.from(context), this)
    private val mAdapter = BannerAdapter()

    init {
        //初始化
        initView()
    }

    /**
     * 初始化View
     */
    private fun initView() {
        //banner
        mBinding.banner.apply {
            setUserInputEnabled(true)
            disallowParentInterceptDownEvent(false)
            //adapter
            setAdapter(mAdapter)
            //点击banner事件
            setOnPageClickListener { _, position -> clickBanner(position) }
            //TODO 处理生命周期

            //创建无数据banner
            create()
        }
    }

    /**
     * 点击banner
     */
    private fun clickBanner(position: Int) {
        val banner = mBinding.banner.data[position] as Banner
        //跳转到对应页面
        ARouter.getInstance().build(RoutePath.WEB)
            .withString(WebActivity.WEB_URL, banner.url)
            .navigation()
    }

    /**
     * 设置Banner数据
     */
    fun setBanner(banners: List<Banner>) {
        //TODO 设置缓存三个页面，修改重复加载图片问题
        mBinding.banner.setOffScreenPageLimit(banners.size)
        //加载数据
        mBinding.banner.refreshData(banners)
    }

    /**
     * 轮播图Adapter
     */
    private inner class BannerAdapter : BaseBannerAdapter<Banner>() {
        override fun bindData(
            holder: BaseViewHolder<Banner>,
            data: Banner,
            position: Int,
            pageSize: Int
        ) {
            val binding = ItemHomeBannerBinding.bind(holder.itemView)
            binding.ivBanner.load(data.imagePath)
        }

        override fun getLayoutId(viewType: Int): Int {
            return R.layout.item_home_banner
        }

    }

}