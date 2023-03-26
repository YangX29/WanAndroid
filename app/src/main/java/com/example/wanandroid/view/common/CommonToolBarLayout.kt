package com.example.wanandroid.view.common

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.viewbinding.ViewBinding
import com.example.wanandroid.R
import com.example.wanandroid.databinding.LayoutCommonToolBarBinding
import com.example.wanandroid.databinding.ViewCommonToolBarBinding
import com.example.wanandroid.utils.extension.adaptImmersionByMargin

/**
 * @author: Yang
 * @date: 2023/3/13
 * @description: 通用Toolbar布局，统一标题栏高度，沉浸式
 */
class CommonToolBarLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val mBinding: LayoutCommonToolBarBinding

    //bar
    @LayoutRes
    private var barLayout = R.layout.view_common_tool_bar
    lateinit var barView: View

    //沉浸式
    private var fitStatusBar = true

    init {
        val view = View.inflate(context, R.layout.layout_common_tool_bar, this)
        mBinding = LayoutCommonToolBarBinding.bind(view)
        //初始化属性
        val ta =
            context.obtainStyledAttributes(attrs, R.styleable.CommonToolBarLayout, defStyleAttr, 0)
        barLayout = ta.getResourceId(
            R.styleable.CommonToolBarLayout_bar_layout,
            R.layout.view_common_tool_bar
        )
        fitStatusBar = ta.getBoolean(R.styleable.CommonToolBarLayout_fitStatus, true)
        ta.recycle()
        //初始化
        initView()
    }

    /**
     * 初始化
     */
    private fun initView() {
        //沉浸式
        if (fitStatusBar) {
            mBinding.bar.adaptImmersionByMargin()
        }
        //bar
        View.inflate(context, barLayout, mBinding.bar)
        barView = mBinding.bar.getChildAt(0)
    }

    /**
     * 设置Bar内容
     */
    inline fun <reified VB : ViewBinding> setBar(block: VB.() -> Unit) {
        try {
            val binding =
                VB::class.java.getMethod("bind", View::class.java).invoke(null, barView) as? VB
                    ?: return
            block.invoke(binding)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 获取toolbar中的view
     */
    fun <V : View> findView(@IdRes id: Int): V? {
        return barView.findViewById(id)
    }

    /**
     * 设置通用标题
     */
    fun setCommonTitle(@StringRes title: Int, listener: () -> Unit) {
        if (barLayout == R.layout.view_common_tool_bar) {
            ViewCommonToolBarBinding.bind(barView).apply {
                tvTitle.setText(title)
                ivBack.setOnClickListener { listener.invoke() }
            }
        }
    }

    /**
     * 设置通用标题
     */
    fun setCommonTitle(title: String, listener: () -> Unit) {
        if (barLayout == R.layout.view_common_tool_bar) {
            ViewCommonToolBarBinding.bind(barView).apply {
                tvTitle.text = title
                ivBack.setOnClickListener { listener.invoke() }
            }
        }
    }

}