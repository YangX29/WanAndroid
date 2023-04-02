package com.example.wanandroid.ui.mine

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.alibaba.android.arouter.launcher.ARouter
import com.example.modele_net.scope_v1.ApiProvider
import com.example.wanandroid.base.BaseFragment
import com.example.wanandroid.common.RoutePath
import com.example.wanandroid.databinding.FragmentMineBinding
import com.example.wanandroid.net.WanAndroidApi
import com.example.wanandroid.net.executeWACall
import com.example.wanandroid.utils.extension.launch
import com.example.wanandroid.utils.toast.ToastUtils
import com.example.wanandroid.utils.user.UserManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

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
        mBinding.btLogout.setOnClickListener {
            executeWACall(lifecycleScope, {
                ApiProvider.api(WanAndroidApi::class.java).logout()
            }, {
                ToastUtils.show(requireContext(), "logout")
                UserManager.logout()
            })
        }
        launch {
            UserManager.getUserInfo().collect {
                withContext(Dispatchers.Main) {
                    mBinding.tvUser.text = "$it"
                }
            }
        }
    }

}