package com.example.wanandroid.view.common

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.annotation.ColorRes
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.example.module_common.utils.extension.dp2px
import com.example.wanandroid.R
import kotlin.math.roundToInt

/**
 * @author: Yang
 * @date: 2023/2/26
 * @description: 通用RecycleView分割线
 */
class SimpleDividerItemDecoration(
    context: Context,
    private val inset: Int = 10.dp2px(),
    @ColorRes private val colorRes: Int = R.color.common_divider
) : RecyclerView.ItemDecoration() {

    companion object {
        private const val DIVIDER_HEIGHT = 1
    }

    private val paint = Paint()
    private val mBounds = Rect()

    init {
        //颜色
        paint.color = context.resources.getColor(colorRes)
        //填充
        paint.style = Paint.Style.FILL
        //抗锯齿
        paint.isAntiAlias = true
    }

    override fun onDraw(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(canvas, parent, state)
        canvas.save()
        for (i in 0 until parent.childCount) {
            val child = parent.getChildAt(i)
            //计算位置
            parent.layoutManager!!.getDecoratedBoundsWithMargins(child, mBounds)
            val right = (mBounds.right - inset).toFloat()
            val left = inset.toFloat()
            val top = (mBounds.bottom - DIVIDER_HEIGHT).toFloat()
            val bottom = mBounds.bottom.toFloat()
            val pos = parent.getChildAdapterPosition(child)
            val type = parent.adapter!!.getItemViewType(pos)
            //排除特殊类型item
            if (type != BaseQuickAdapter.EMPTY_VIEW && type != BaseQuickAdapter.LOAD_MORE_VIEW
                && type != BaseQuickAdapter.HEADER_VIEW && type != BaseQuickAdapter.FOOTER_VIEW
            ) {
                canvas.drawRect(left, top, right, bottom, paint)
            }
        }
        canvas.restore()
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val pos = parent.getChildAdapterPosition(view)
        val type = parent.adapter!!.getItemViewType(pos)
        //排除特殊类型item
        if (type != BaseQuickAdapter.EMPTY_VIEW && type != BaseQuickAdapter.LOAD_MORE_VIEW
            && type != BaseQuickAdapter.HEADER_VIEW && type != BaseQuickAdapter.FOOTER_VIEW
        ) {
            outRect.bottom = DIVIDER_HEIGHT
        }
    }

}