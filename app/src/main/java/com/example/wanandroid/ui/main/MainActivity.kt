package com.example.wanandroid.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.wanandroid.base.BaseActivity
import com.example.wanandroid.common.RoutePath
import com.example.wanandroid.databinding.ActivityMainBinding
import com.example.wanandroid.viewmodel.main.MainViewIntent
import com.example.wanandroid.viewmodel.main.MainViewModel
import com.example.wanandroid.viewmodel.main.MainViewState

/**
 * 首页
 */
@Route(path = RoutePath.HOME)
class MainActivity :
    BaseActivity<ActivityMainBinding, MainViewState, MainViewIntent, MainViewModel>() {

    override val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // TODO 沉浸式
    }

}
