package com.example.wanandroid.view.common

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Color
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.module_common.utils.extension.invisible
import com.example.module_common.utils.extension.sp2px
import com.example.module_common.utils.extension.visible
import com.example.wanandroid.R
import com.example.wanandroid.databinding.ViewCommonToolBarBinding
import com.example.wanandroid.utils.extension.adaptImmersionByMargin

/**
 * @author: Yang
 * @date: 2023/3/10
 * @description: 通用标题栏
 */
class CommonToolBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val mBinding: ViewCommonToolBarBinding

    //标题
    private lateinit var title: String

    //标题颜色
    private var titleColor = Color.WHITE

    //标题大小
    private var titleSize = 1f

    //左边按钮
    private var leftButton = R.drawable.icon_back

    //右边按钮
    private var rightButton = -1

    //是否显示左边按钮
    private var hideLeftButton = false

    //沉浸式
    private var fitStatusBar = true

    //左边按钮点击事件
    private var leftClickListener: (() -> Unit)? = null

    //右边按钮点击事件
    private var rightClickListener: (() -> Unit)? = null

    init {
        val view = View.inflate(context, R.layout.view_common_tool_bar, this)
        mBinding = ViewCommonToolBarBinding.bind(view)
        //初始化属性
        val ta = context.obtainStyledAttributes(attrs, R.styleable.CommonToolBar, defStyleAttr, 0)
        initAttrs(ta)
        ta.recycle()
        //初始化view
        initView()
    }

    /**
     * 初始化属性
     */
    private fun initAttrs(ta: TypedArray) {
        title = ta.getString(R.styleable.CommonToolBar_title) ?: ""
        titleColor = ta.getColor(
            R.styleable.CommonToolBar_titleColor,
            context.resources.getColor(R.color.common_title)
        )
        titleSize = ta.getDimension(R.styleable.CommonToolBar_titleSize, 16.sp2px().toFloat())
        leftButton = ta.getResourceId(R.styleable.CommonToolBar_leftButton, R.drawable.icon_back)
        rightButton = ta.getResourceId(R.styleable.CommonToolBar_rightButton, -1)
        hideLeftButton = ta.getBoolean(R.styleable.CommonToolBar_hideLeftButton, false)
        fitStatusBar = ta.getBoolean(R.styleable.CommonToolBar_fitStatusBar, true)
    }

    /**
     * 初始化
     */
    private fun initView() {
        //标题
        mBinding.tvTitle.apply {
            text = title
            setTextSize(TypedValue.COMPLEX_UNIT_PX, titleSize)
            setTextColor(titleColor)
        }
        //左边按钮
        mBinding.ivBack.apply {
            setImageResource(leftButton)
            setOnClickListener { leftClickListener?.invoke() }
            invisible(hideLeftButton)
        }
        //左边按钮
        mBinding.ivMenu.apply {
            setOnClickListener { rightClickListener?.invoke() }
            if (rightButton != -1) {
                setImageResource(rightButton)
            }
            visible(rightButton != -1)
        }
        //沉浸式
        if (fitStatusBar) {
            mBinding.spHolder.adaptImmersionByMargin()
        }
    }

    /**
     * 左边按钮点击事件
     */
    fun setOnLeftClick(listener: () -> Unit) {
        leftClickListener = listener
    }

    /**
     * 右边按钮点击事件
     */
    fun setOnRightClick(listener: () -> Unit) {
        leftClickListener = listener
    }


}