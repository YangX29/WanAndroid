package com.example.wanandroid.view.widget.wan

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.alibaba.android.arouter.launcher.ARouter
import com.example.module_common.utils.extension.visible
import com.example.wanandroid.R
import com.example.wanandroid.common.RoutePath
import com.example.wanandroid.databinding.HeaderMineBinding
import com.example.wanandroid.model.UserInfo

/**
 * @author: Yang
 * @date: 2023/4/12
 * @description: 我的页面Header
 */
class MineHeader @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val mBinding by lazy {
        HeaderMineBinding.inflate(LayoutInflater.from(context), this, true)
    }

    /**
     * 设置数据
     */
    fun setData(userInfo: UserInfo?) {
        //更新登录状态
        mBinding.groupInfo.visible(userInfo != null)
        mBinding.tvLogin.visible(userInfo == null)
        //头像
        mBinding.ivAvatar.setOnClickListener {
            if (userInfo == null) {
                //去登录
                ARouter.getInstance().build(RoutePath.AUTH)
                    .navigation()
            } else {
                //TODO 跳转到个人信息页面
            }
        }
        //用户信息
        userInfo?.apply {
            //用户名
            mBinding.tvUserName.text = username
            //TODO 等级
            mBinding.tvLevel.text = context.getString(R.string.user_level, 10)
            //TODO 排名
            mBinding.tvRank.text = context.getString(R.string.user_level, 120)
        }
    }

}