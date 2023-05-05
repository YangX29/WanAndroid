package com.example.wanandroid.view.widget.toolbar

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.util.TypedValue
import androidx.annotation.DrawableRes
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.module_common.utils.extension.invisible
import com.example.module_common.utils.extension.obtainStyledAttributes
import com.example.module_common.utils.extension.sp2px
import com.example.module_common.utils.extension.visible
import com.example.wanandroid.R
import com.example.wanandroid.databinding.LayoutCommonTitleBinding

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

    private lateinit var mBinding: LayoutCommonTitleBinding

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

    //右边额外按钮
    private var extraButton = -1

    //是否显示左边按钮
    private var hideLeftButton = false

    //沉浸式
    private var fitStatusBar = true

    //左边按钮点击事件
    private var leftClickListener: (() -> Unit)? = null

    //右边按钮点击事件
    private var rightClickListener: (() -> Unit)? = null

    //右边按钮点击事件
    private var extraClickListener: (() -> Unit)? = null

    init {
        //初始化属性
        obtainStyledAttributes(attrs, R.styleable.CommonToolBar) {
            title = getString(R.styleable.CommonToolBar_title) ?: ""
            titleColor = getColor(
                R.styleable.CommonToolBar_titleColor,
                context.resources.getColor(R.color.common_title)
            )
            titleSize = getDimension(R.styleable.CommonToolBar_titleSize, 16.sp2px().toFloat())
            leftButton = getResourceId(R.styleable.CommonToolBar_leftButton, R.drawable.icon_back)
            rightButton = getResourceId(R.styleable.CommonToolBar_rightButton, -1)
            extraButton = getResourceId(R.styleable.CommonToolBar_extraButton, -1)
            hideLeftButton = getBoolean(R.styleable.CommonToolBar_hideLeftButton, false)
            fitStatusBar = getBoolean(R.styleable.CommonToolBar_fitStatusBar, true)
        }
        //初始化view
        initView()
    }

    /**
     * 初始化
     */
    private fun initView() {
        val toolbar = CommonToolBarLayout(context)
        addView(toolbar, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        mBinding = toolbar.getBarBinding()
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
        //右边按钮
        mBinding.ivMenu.apply {
            setOnClickListener { rightClickListener?.invoke() }
            if (rightButton != -1) {
                setImageResource(rightButton)
            }
            visible(rightButton != -1)
        }
        //右边额外按钮
        mBinding.ivExtra.apply {
            setOnClickListener { extraClickListener?.invoke() }
            if (extraButton != -1) {
                setImageResource(extraButton)
            }
            visible(extraButton != -1)
        }
    }

    /**
     * 设置标题
     */
    fun setTitle(title: String) {
        mBinding.tvTitle.text = title
    }

    /**
     * 设置是否显示标题
     */
    fun showTitle(show: Boolean) {
        mBinding.tvTitle.visible(show)
    }

    /**
     * 右边按钮
     */
    fun setRightButton(@DrawableRes res: Int) {
        rightButton = res
        mBinding.ivMenu.setImageResource(res)
        mBinding.ivMenu.visible()
    }

    /**
     * 右边额外按钮
     */
    fun setExtraButton(@DrawableRes res: Int) {
        extraButton = res
        mBinding.ivExtra.setImageResource(res)
        mBinding.ivExtra.visible()
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
        rightClickListener = listener
    }

    /**
     * 右边额外按钮点击事件
     */
    fun setOnExtraClick(listener: () -> Unit) {
        extraClickListener = listener
    }

    /**
     * 是否显示右边按钮
     */
    fun showRightButton(show: Boolean) {
        mBinding.ivMenu.visible(show)
    }

    /**
     * 是否显示右边额外按钮
     */
    fun showExtraButton(show: Boolean) {
        mBinding.ivExtra.visible(show)
    }


}