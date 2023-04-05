package com.example.wanandroid.utils.user

import android.content.Context
import android.os.Bundle
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.annotation.Interceptor
import com.alibaba.android.arouter.facade.callback.InterceptorCallback
import com.alibaba.android.arouter.facade.template.IInterceptor
import com.alibaba.android.arouter.launcher.ARouter
import com.example.wanandroid.common.RoutePath
import com.example.wanandroid.ui.auth.AuthActivity

/**
 * @author: Yang
 * @date: 2023/4/5
 * @description: 登录拦截器，部分页面需要登录后才能进入
 */
@Interceptor(name = "login", priority = 1)
class LoginInterceptor : IInterceptor {

    companion object {
        //需要进行登录拦截的页面
        const val INTERCEPTOR_PAGE = 1001
    }

    override fun init(context: Context?) {

    }

    override fun process(postcard: Postcard?, callback: InterceptorCallback?) {
        //判断是否需要拦截，未登录且为需要拦截的页面
        if (!UserManager.hasLogin && postcard?.extra == INTERCEPTOR_PAGE) {
            //拦截
            callback?.onInterrupt(null)
            //跳转到登录页面
            val params = postcard.extras ?: Bundle()
            ARouter.getInstance().build(RoutePath.AUTH)
                .withString(AuthActivity.KEY_TARGET_PATH, postcard.path)
                .withBundle(AuthActivity.KEY_TARGET_BUNDLE, params)
                .navigation()
        } else {
            callback?.onContinue(postcard)
        }
    }

}