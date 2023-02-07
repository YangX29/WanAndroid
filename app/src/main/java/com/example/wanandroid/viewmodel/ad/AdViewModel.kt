package com.example.wanandroid.viewmodel.ad

import android.os.CountDownTimer
import com.example.wanandroid.base.BaseViewModel
import com.example.wanandroid.common.RoutePath
import kotlinx.coroutines.flow.MutableStateFlow
import kotlin.math.roundToInt

/**
 * @author: Yang
 * @date: 2023/2/5
 * @description: 广告页ViewModel
 */
class AdViewModel : BaseViewModel<AdViewState, AdViewIntent>() {

    companion object {
        //默认广告倒计时时长
        const val DEFAULT_AD_TIME = 5 * 1000L

        //倒计时间隔
        const val AD_TIME_INTERVAL = 1000L
    }

    //计时器
    private var timer: AdCountDownTimer? = null

    override fun handleIntent(viewIntent: AdViewIntent) {
        when (viewIntent) {
            is AdViewIntent.ShowAd -> {
                //显示广告
                showAd()
            }
            is AdViewIntent.Skip -> {
                //跳过
                skip()
            }
        }
    }

    /**
     * 显示广告
     */
    private fun showAd() {
        //TODO 获取广告信息，广告类型、显示时长
        //显示广告
        updateViewState(AdViewState(AdViewStatus.ShowAd("https://img0.baidu.com/it/u=986706212,1128151217&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=889")))
        //开始倒计时
        timer = AdCountDownTimer(DEFAULT_AD_TIME, AD_TIME_INTERVAL)
        timer?.start()
        //存在数据丢失问题
        val defaultTime = (DEFAULT_AD_TIME / 1000).toInt()
        updateViewState(AdViewState(AdViewStatus.Start(defaultTime)))
    }

    /**
     * 跳过广告
     */
    private fun skip() {
        timer?.cancel()
        timer?.onFinish()
    }

    /**
     * 广告计时器
     */
    private inner class AdCountDownTimer(millisInFuture: Long, countDownInterval: Long) :
        CountDownTimer(millisInFuture, countDownInterval) {

        override fun onTick(millisUntilFinished: Long) {
            // 修改倒计时
            val time = (millisUntilFinished / 1000f).roundToInt()
            this@AdViewModel.updateViewState(
                AdViewState(AdViewStatus.CountDown(time))
            )
        }

        override fun onFinish() {
            // 结束倒计时
            this@AdViewModel.updateViewState(
                AdViewState(AdViewStatus.Finish(RoutePath.HOME))
            )
        }

    }

}