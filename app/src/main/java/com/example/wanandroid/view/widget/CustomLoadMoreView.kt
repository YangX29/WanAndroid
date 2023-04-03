package com.example.wanandroid.view.widget

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chad.library.adapter.base.loadmore.BaseLoadMoreView
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.wanandroid.R

/**
 * @author: Yang
 * @date: 2023/3/2
 * @description: 通用列表加载更多View
 */
class CustomLoadMoreView : BaseLoadMoreView() {

    override fun getLoadComplete(holder: BaseViewHolder): View {
        return holder.getView(R.id.tvLoadComplete)
    }

    override fun getLoadEndView(holder: BaseViewHolder): View {
        return holder.getView(R.id.tvLoadEnd)
    }

    override fun getLoadFailView(holder: BaseViewHolder): View {
        return holder.getView(R.id.tvLoadFailed)
    }

    override fun getLoadingView(holder: BaseViewHolder): View {
        return holder.getView(R.id.clLoading)
    }

    override fun getRootView(parent: ViewGroup): View {
        return LayoutInflater.from(parent.context).inflate(R.layout.layout_load_more, parent, false)
    }
}