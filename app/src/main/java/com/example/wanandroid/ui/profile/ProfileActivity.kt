package com.example.wanandroid.ui.profile

import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.module_common.utils.extension.invisible
import com.example.module_common.utils.extension.visible
import com.example.wanandroid.R
import com.example.wanandroid.base.BaseMVIActivity
import com.example.wanandroid.common.RoutePath
import com.example.wanandroid.databinding.ActivityProfileBinding
import com.example.wanandroid.model.UserProfile
import com.example.wanandroid.mvi.profile.ProfileViewIntent
import com.example.wanandroid.mvi.profile.ProfileViewModel
import com.example.wanandroid.mvi.profile.ProfileViewState
import com.example.wanandroid.utils.user.LoginInterceptor
import com.example.wanandroid.view.widget.wan.MineHeader

/**
 * @author: Yang
 * @date: 2023/4/24
 * @description: 用户信息页面
 */
@Route(path = RoutePath.PROFILE, extras = LoginInterceptor.INTERCEPTOR_PAGE)
class ProfileActivity :
    BaseMVIActivity<ActivityProfileBinding, ProfileViewState, ProfileViewIntent, ProfileViewModel>() {

    override val viewModel: ProfileViewModel by viewModels()

    private val adapter = ProfileAdapter()

    //header
    private val header by lazy { MineHeader(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //初始化
        initView()
        initData()
    }

    override fun handleViewState(viewState: ProfileViewState) {
        when (viewState) {
            is ProfileViewState.InitSuccess -> {
                setProfile(viewState.profile)
            }

            is ProfileViewState.InitFailed -> {
                //TODO
            }
        }
    }

    /**
     * 初始化View
     */
    private fun initView() {
        //toolbar
        mBinding.toolbar.setOnLeftClick { finish() }
        //信息
        mBinding.rvProfile.apply {
            layoutManager = LinearLayoutManager(this@ProfileActivity)
            adapter = this@ProfileActivity.adapter
        }
        adapter.setHeaderView(header)
    }

    /**
     * 设置用户信息
     */
    private fun setProfile(profile: UserProfile) {
        profile.apply {
            //header
            header.setUserName(userInfo.username)
            //item
            val list = mutableListOf<ProfileAdapter.Item>()
            //id
            list.add(ProfileAdapter.Item(getString(R.string.profile_id), userInfo.id.toString()))
            //用户名
            list.add(ProfileAdapter.Item(getString(R.string.profile_username), userInfo.username))
            //积分
            list.add(
                ProfileAdapter.Item(
                    getString(R.string.profile_coin),
                    coinInfo.coinCount.toString()
                )
            )
            //等级
            list.add(
                ProfileAdapter.Item(
                    getString(R.string.profile_level),
                    coinInfo.level.toString()
                )
            )
            //排名
            list.add(
                ProfileAdapter.Item(
                    getString(R.string.profile_rank),
                    coinInfo.rank.toString()
                )
            )
            //邮箱
            list.add(
                ProfileAdapter.Item(
                    getString(R.string.profile_email),
                    userInfo.email.ifEmpty { "-" })
            )
            adapter.setNewInstance(list)
        }
        mBinding.loading.invisible()
        mBinding.rvProfile.visible()
    }

    /**
     * 初始化数据
     */
    private fun initData() {
        sendIntent(ProfileViewIntent.Init)
    }
}