package com.example.wanandroid.net

import com.alibaba.android.arouter.launcher.ARouter
import com.example.modele_net.common.error.NetError
import com.example.modele_net.scope_v1.IResult
import com.example.modele_net.scope_v1.NetErrorHandleDelegate
import com.example.wanandroid.R
import com.example.wanandroid.app.WanApplication
import com.example.wanandroid.common.RoutePath
import com.example.wanandroid.utils.toast.ToastUtils
import com.example.wanandroid.utils.user.AuthManager
import com.example.wanandroid.utils.user.UserManager

/**
 * @author: Yang
 * @date: 2023/4/5
 * @description: WanAndroid网络错误处理类
 */
class WanNetErrorHandler : NetErrorHandleDelegate {

    override fun handleError(result: IResult): NetError? {
        if (result is ResponseResult<*>) {
            return when (result.errorCode) {
                ResponseResult.ERROR_CODE_LOGIN -> {//未登录错误
                    if (UserManager.hasLogin) {//已登录，登录过期
                        //清空用户信息
                        AuthManager.logout()
                        ToastUtils.show(WanApplication.context, R.string.toast_login_expires)
                    } else {//未登录
                        ToastUtils.show(WanApplication.context, R.string.toast_login_error)
                    }
                    //跳转到登录页面
                    ARouter.getInstance().build(RoutePath.AUTH)
                        .navigation()
                    //返回错误信息
                    NetError(result.errorCode!!, result.errorMsg ?: "")
                }

                ResponseResult.ERROR_CODE_COMMON -> {//其他错误
                    val common =
                        WanApplication.context.getString(R.string.toast_net_error)
                    ToastUtils.show(result.errorMsg ?: common)
                    //返回错误信息
                    NetError(result.errorCode!!, result.errorMsg ?: common)
                }

                else -> {//调用成功
                    null
                }
            }
        }
        return null
    }

}