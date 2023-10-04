package com.example.module_common.utils.permission

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

/**
 * @author: Yang
 * @date: 2023/10/3
 * @description: 权限工具类
 */
object PermissionUtils {

    /**
     * 检查权限
     */
    fun checkPermission(
        activity: Activity,
        permissions: MutableList<String>
    ): Boolean {
        permissions.forEach {
            val result = ContextCompat.checkSelfPermission(activity, it)
            if (result == PackageManager.PERMISSION_DENIED) {
                return false
            }
        }
        return true
    }

    /**
     * 请求权限
     */
    fun requestPermission(
        activity: FragmentActivity,
        permissionList: MutableList<String>,
        callback: (Map<String, Boolean>, Boolean) -> Unit
    ) {
        //检查需要申请的权限
        val needRequestPermissions = mutableListOf<String>()
        permissionList.forEach {
            val result = ContextCompat.checkSelfPermission(activity, it)
            if (result == PackageManager.PERMISSION_DENIED) {
                needRequestPermissions.add(it)
            }
        }
        if (needRequestPermissions.isEmpty()) {
            //所有权限申请成功
            callback.invoke(permissionList.associateWith { true }, true)
        } else {
            //请求成功
            DispatchResultFragment.requestPermission(activity, permissionList, callback)
        }
    }

    internal class DispatchResultFragment : Fragment() {


        private var mCallback: ((Map<String, Boolean>, Boolean) -> Unit)? = null
        private var mPermissions = mutableListOf<String>()

        private val mLauncher =
            registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
                //权限申请结果
                mCallback?.invoke(it, !it.containsValue(false))
            }

        companion object {

            fun requestPermission(
                activity: FragmentActivity,
                permissions: MutableList<String>,
                callback: (Map<String, Boolean>, Boolean) -> Unit
            ) {
                val fragment = DispatchResultFragment()
                fragment.init(permissions, callback)
                //添加fragment
                activity.supportFragmentManager.beginTransaction()
                    .add(fragment as Fragment, activity::class.java.simpleName)
                    .commitAllowingStateLoss()
            }

        }

        private fun init(
            permissions: MutableList<String>,
            callback: (Map<String, Boolean>, Boolean) -> Unit
        ) {
            mPermissions = permissions
            mCallback = callback
        }

        override fun onAttach(context: Context) {
            super.onAttach(context)
            //申请权限
            mLauncher.launch(mPermissions.toTypedArray())
        }

    }
}