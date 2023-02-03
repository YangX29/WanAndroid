package com.example.wanandroid.ui.main

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.module_common.base.BaseVBActivity
import com.example.wanandroid.common.RoutePath
import com.example.wanandroid.databinding.ActivityMainBinding

/**
 * 首页
 */
@Route(path = RoutePath.HOME)
class MainActivity : BaseVBActivity<ActivityMainBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // TODO 沉浸式
    }

}
