package com.example.wanandroid.mvi.splash

import androidx.lifecycle.viewModelScope
import com.example.wanandroid.base.BaseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * @author: Yang
 * @date: 2023/1/31
 * @description:
 */
class SplashViewModel : BaseViewModel<SplashViewState, SplashViewIntent>() {

    companion object {
        //默认倒计时时长
        const val DEFAULT_TIME = 1000L
    }

    override fun handleIntent(viewIntent: SplashViewIntent) {
        if (viewIntent is SplashViewIntent.EnterApp) {
            viewModelScope.launch {
                delay(DEFAULT_TIME)
                mViewState.emit(SplashViewState(0))
            }
        }
    }

}