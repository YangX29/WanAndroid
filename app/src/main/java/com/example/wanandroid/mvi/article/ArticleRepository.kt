package com.example.wanandroid.mvi.article

import com.example.wanandroid.R
import com.example.wanandroid.common.LiveEventKey
import com.example.wanandroid.utils.toast.ToastUtils
import com.jeremyliao.liveeventbus.LiveEventBus

/**
 * @author: Yang
 * @date: 2023/6/5
 * @description: Article数据repository
 */
class ArticleRepository {

    //数据来源
    private val dataSource by lazy { ArticleDataSource() }

    /**
     * 收藏文章
     */
    suspend fun collectArticle(id: Long) {
        val result = dataSource.collectArticle(id)
        //收藏成功，发送通知
        if (result.isSuccess()) {
            LiveEventBus.get<Long>(LiveEventKey.KEY_COLLECT_ARTICLE).post(id)
        } else {
            ToastUtils.show(R.string.toast_collect_failed)
        }
    }

    /**
     * 取消收藏文章，文章列表
     */
    suspend fun uncollectArticle(id: Long) {
        val result = dataSource.uncollectArticle(id)
        //收藏成功，发送通知
        if (result.isSuccess()) {
            LiveEventBus.get<Long>(LiveEventKey.KEY_UNCOLLECT_ARTICLE).post(id)
        } else {
            ToastUtils.show(R.string.toast_uncollect_failed)
        }
    }

    /**
     * 取消我的收藏
     */
    suspend fun uncollectMine(id: Long, originId: Long) {
        val result = dataSource.uncollectMine(id, originId)
        //收藏成功，发送通知
        if (result.isSuccess()) {
            LiveEventBus.get<Long>(LiveEventKey.KEY_UNCOLLECT_ARTICLE).post(id)
        } else {
            ToastUtils.show(R.string.toast_uncollect_failed)
        }
    }

}