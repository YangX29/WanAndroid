package com.example.wanandroid.ui.ad

import android.os.Bundle
import androidx.activity.viewModels
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.wanandroid.base.BaseActivity
import com.example.wanandroid.common.RoutePath
import com.example.wanandroid.databinding.ActivityAdBinding
import com.example.wanandroid.viewmodel.ad.AdViewIntent
import com.example.wanandroid.viewmodel.ad.AdViewModel
import com.example.wanandroid.viewmodel.ad.AdViewState

/**
 * @author: Yang
 * @date: 2023/2/5
 * @description: 广告页，包含图片广告和视频广告
 */
@Route(path = RoutePath.AD)
class AdActivity : BaseActivity<ActivityAdBinding, AdViewState, AdViewIntent, AdViewModel>() {

    override val viewModel: AdViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

}