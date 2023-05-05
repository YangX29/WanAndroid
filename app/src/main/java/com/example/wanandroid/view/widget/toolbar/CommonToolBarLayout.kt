package com.example.wanandroid.view.widget.toolbar

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.viewbinding.ViewBinding
import com.example.module_common.utils.extension.obtainStyledAttributes
import com.example.wanandroid.R
import com.example.wanandroid.databinding.LayoutCommonTitleBinding
import com.example.wanandroid.databinding.LayoutCommonToolBarBinding
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

    private val mBinding = LayoutCommonToolBarBinding.inflate(LayoutInflater.from(context), this)

    //bar
    @LayoutRes
    private var barLayout = R.layout.layout_common_title
    lateinit var barView: View

    //沉浸式
    private var fitStatusBar = true

    init {
        //初始化属性
        obtainStyledAttributes(attrs, R.styleable.CommonToolBarLayout) {
            barLayout = getResourceId(
                R.styleable.CommonToolBarLayout_bar_layout,
                R.layout.layout_common_title
            )
            fitStatusBar = getBoolean(R.styleable.CommonToolBarLayout_fitStatus, true)
        }
        //初始化
        initView()
    }

    /**
     * 初始化
     */
    private fun initView() {
        //背景
        setBackgroundResource(R.color.common_background)
        //沉浸式
        if (fitStatusBar) {
            mBinding.bar.adaptImmersionByMargin()
        }
        //bar
        View.inflate(context, barLayout, mBinding.bar)
        barView = mBinding.bar.getChildAt(0)
    }

    /**
     * 获取toolbar布局ViewBinding
     */
    inline fun <reified VB : ViewBinding> getBarBinding(): VB {
        return VB::class.java.getMethod("bind", View::class.java).invoke(null, barView) as VB
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
        if (barLayout == R.layout.layout_common_title) {
            LayoutCommonTitleBinding.bind(barView).apply {
                tvTitle.setText(title)
                ivBack.setOnClickListener { listener.invoke() }
            }
        }
    }

    /**
     * 设置通用标题
     */
    fun setCommonTitle(title: String, listener: () -> Unit) {
        if (barLayout == R.layout.layout_common_title) {
            LayoutCommonTitleBinding.bind(barView).apply {
                tvTitle.text = title
                ivBack.setOnClickListener { listener.invoke() }
            }
        }
    }

}