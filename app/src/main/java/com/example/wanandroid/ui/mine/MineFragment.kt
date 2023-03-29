package com.example.wanandroid.ui.mine

import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.launcher.ARouter
import com.example.wanandroid.base.BaseFragment
import com.example.wanandroid.common.RoutePath
import com.example.wanandroid.databinding.FragmentMineBinding

/**
 * @author: Yang
 * @date: 2023/2/21
 * @description: 我的页
 */
class MineFragment : BaseFragment<FragmentMineBinding>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.btLogin.setOnClickListener {
            ARouter.getInstance().build(RoutePath.AUTH)
                .navigation()
        }
    }

}