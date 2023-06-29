package com.example.module_common.utils.extension

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

/**
 * 启动Activity，通过泛型指定Activity
 * @param block 参数DSL
 * @param options 启动Activity参数,动画
 * @param callback 页面返回结果回调
 */
inline fun <reified T : Activity> FragmentActivity.launchActivity(
    block: (Intent.() -> Unit) = {},
    noinline callback: (Intent?.() -> Unit)? = null,
    options: ActivityOptionsCompat? = null,
) {
    val startIntent = Intent(this, T::class.java)
    startIntent.block()
    launchActivity(startIntent, callback, options)
}

/**
 * 启动Activity，通过指定泛型启动，使用更加方便，带返回结果
 * @param block 目标intent，用于操作intent，如传入参数等
 * @param options 启动Activity参数,动画
 * @param callback 页面返回结果回调
 */
inline fun <reified T : Activity> FragmentActivity.launchActivity(
    block: (Intent.() -> Unit) = { },
    options: ActivityOptionsCompat? = null,
    noinline callback: ((Intent?) -> Unit) = { }
) {
    val intent = Intent(this, T::class.java)
    intent.block()
    launchActivity(intent, callback, options)
}

/**
 * 启动Activity，带返回结果回调和动画
 * @param intent 目标intent
 * @param options 启动Activity参数,动画
 * @param callback 页面返回结果回调
 */
fun FragmentActivity.launchActivity(
    intent: Intent,
    callback: (Intent?.() -> Unit)? = null,
    options: ActivityOptionsCompat? = null,
) {
    //通过DispatchResultFragment启动Activity,因为launcher必须在onStart之前创建,所以需要使用Fragment间接启动activity用于传递回调
    DispatchResultFragment.launchActivity(this, intent, options, callback)
}

/**
 * 结束Activity，携带返回结果
 * @param intent 返回结果数据
 */
fun Activity.finishWithResult(intent: Intent? = null) {
    setResult(RESULT_OK, intent)
    finish()
}

/**
 * 用于分发Activity返回结果的Fragment
 */
internal class DispatchResultFragment : Fragment() {

    private var mIntent: Intent? = null
    private var mOptions: ActivityOptionsCompat? = null
    private var mCallback: ((Intent?) -> Unit)? = null

    //启动器
    private val mLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            //如果返回结果为RESULT_OK，回调
            if (it.resultCode == RESULT_OK) {
                mCallback?.invoke(it.data)
            }
        }

    companion object {

        /**
         * 启动activity
         */
        fun launchActivity(
            activity: FragmentActivity,
            intent: Intent,
            options: ActivityOptionsCompat?,
            callback: (Intent?.() -> Unit)?
        ): DispatchResultFragment {
            val fragment = DispatchResultFragment()
            //初始化fragment
            fragment.init(intent, options) {
                //回调
                callback?.invoke(it)
                //回调后移除fragment
                activity.supportFragmentManager.beginTransaction().remove(fragment)
                    .commitAllowingStateLoss()
            }
            //添加fragment
            activity.supportFragmentManager.beginTransaction()
                .add(fragment, activity::class.java.simpleName).commitAllowingStateLoss()
            return fragment
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        //启动activity
        mIntent?.let { mLauncher.launch(it, mOptions) }
    }

    override fun onDetach() {
        super.onDetach()
        mCallback = null
    }

    /**
     * 初始化参数
     */
    private fun init(
        intent: Intent,
        options: ActivityOptionsCompat?,
        callback: (Intent?) -> Unit
    ) {
        mIntent = intent
        mOptions = options
        mCallback = callback
    }

}