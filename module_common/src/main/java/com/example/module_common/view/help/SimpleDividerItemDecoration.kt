package com.example.module_common.view.help

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.View
import androidx.annotation.ColorInt
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import kotlin.math.min

/**
 * 通用的RecyclerView分割线，可以实现均分间隔和分割线效果
 * 对于GridLayoutManager的item次方向的大小应该设置为match_parent
 * TODO StaggeredGridLayoutManager处理
 */
class SimpleDividerItemDecoration : ItemDecoration() {

    companion object {
        private const val TAG = "SimpleDividerItemDecoration"
    }

    //竖直方向的间隔/分割线大小，当布局为LinearLayoutManager且为水平方向时无效
    private var verticalSpace = 0

    //水平方向的间隔/分割线大小，当布局为LinearLayoutManager且为竖直方向时无效
    private var horizontalSpace = 0

    //列表四方形边缘间隔
    private var topSpace = 0
    private var bottomSpace = 0
    private var leftSpace = 0
    private var rightSpace = 0

    //分割线边距padding
    private var dividerPadding = 0

    //是否绘制分割线
    private var drawDivider = false

    //分割线颜色
    private var dividerDrawable: Drawable = ColorDrawable(Color.WHITE)

    //分割线大小，默认为item间隔
    private var dividerSize = -1

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        //根据recyclerView布局处理间隔
        parent.layoutManager?.let {
            when (it) {
                is GridLayoutManager -> setGridItemOffsets(outRect, view, parent)
                is LinearLayoutManager -> setLinearItemOffsets(outRect, view, parent)
            }
        }
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        //如果不需要绘制分割线
        if (!drawDivider) return
        //根据recyclerView布局来处理分割线绘制
        parent.layoutManager?.let {
            when (it) {
                is GridLayoutManager -> drawGridItemDivider(c, parent)
                is LinearLayoutManager -> drawLinearItemDivider(c, parent)
            }
        }
    }

    /**
     * 设置LinearLayoutManager间隔
     */
    private fun setLinearItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView
    ) {
        val manager = parent.layoutManager as? LinearLayoutManager ?: return
        val position = parent.getChildAdapterPosition(view)
        val itemCount = manager.itemCount
        if (manager.orientation == LinearLayoutManager.HORIZONTAL) {
            //水平方向
            outRect.left = if (position == 0) leftSpace else horizontalSpace
            outRect.right = if (position == itemCount - 1) rightSpace else 0
            outRect.top = topSpace
            outRect.bottom = bottomSpace
        } else {
            //竖直方向
            outRect.top = if (position == 0) topSpace else verticalSpace
            outRect.bottom = if (position == itemCount - 1) bottomSpace else 0
            outRect.left = leftSpace
            outRect.right = rightSpace
        }
    }

    /**
     * 绘制LinearLayoutManager分割线
     */
    private fun drawLinearItemDivider(canvas: Canvas, parent: RecyclerView) {
        val manager = parent.layoutManager as? LinearLayoutManager ?: return
        //绘制当前显示的item的分割线
        for (i in 0 until parent.childCount) {
            val child = parent.getChildAt(i)
            val position = parent.getChildAdapterPosition(child)
            //最后一个item不绘制分割线
            if (position != manager.itemCount - 1) {
                if (manager.orientation == LinearLayoutManager.HORIZONTAL) {
                    //默认间隔大小就是分割线的大小,分割线不应该超过间隔大小
                    val dividerWidth =
                        if (dividerSize <= 0) horizontalSpace else min(dividerSize, horizontalSpace)
                    val left = child.right + (horizontalSpace - dividerWidth.toFloat()) / 2
                    val right = child.right + (horizontalSpace + dividerWidth.toFloat()) / 2
                    //绘制
                    dividerDrawable.setBounds(
                        left.toInt(),
                        child.top + dividerPadding,
                        right.toInt(),
                        child.bottom - dividerPadding
                    )
                    dividerDrawable.draw(canvas)
                } else {
                    //默认间隔大小就是分割线的大小,分割线不应该超过间隔大小
                    val dividerHeight =
                        if (dividerSize <= 0) verticalSpace else min(dividerSize, verticalSpace)
                    val top = child.bottom + (verticalSpace - dividerHeight.toFloat()) / 2
                    val bottom = child.bottom + (verticalSpace + dividerHeight.toFloat()) / 2
                    //绘制
                    dividerDrawable.setBounds(
                        child.left + dividerPadding,
                        top.toInt(),
                        child.right - dividerPadding,
                        bottom.toInt()
                    )
                    dividerDrawable.draw(canvas)
                }
            }
        }
    }

    /**
     * 设置GridLayoutManager间隔
     */
    private fun setGridItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView
    ) {
        val manager = parent.layoutManager as? GridLayoutManager ?: return
        val itemCount = parent.adapter?.itemCount ?: return
        val position = parent.getChildAdapterPosition(view)
        //方向
        val orientation = manager.orientation
        //主方向行数/列数
        val spanCount = manager.spanCount
        //item所跨行数/列数
        val spanSize = manager.spanSizeLookup.getSpanSize(position)
        //item在主方向的索引
        val spanIndex = manager.spanSizeLookup.getSpanIndex(position, spanCount)
        //item的group索引，次方向索引
        val groupIndex = manager.spanSizeLookup.getSpanGroupIndex(position, spanCount)
        val maxGroupIndex = manager.spanSizeLookup.getSpanGroupIndex(itemCount - 1, spanCount)
        //设置item之间间隔，考虑跨行/列显示的情况
        if (orientation == LinearLayoutManager.HORIZONTAL) {
            //每个item的左右间隔必须相等，否则item大小会不相同
            val itemSpace = (verticalSpace * (spanCount - 1) + topSpace + bottomSpace) / spanCount
            val top = spanIndex * (verticalSpace - itemSpace) + topSpace
            val bottom = itemSpace * spanSize - verticalSpace * (spanSize - 1) - top
            outRect.top = top
            outRect.bottom = bottom
            //左右间隔
            outRect.left = if (groupIndex == 0) leftSpace else 0
            outRect.right = if (groupIndex == maxGroupIndex) rightSpace else horizontalSpace
        } else {
            //每个item的左右间隔必须相等，否则item大小会不相同
            val itemSpace = (horizontalSpace * (spanCount - 1) + leftSpace + rightSpace) / spanCount
            val left = spanIndex * (horizontalSpace - itemSpace) + leftSpace
            val right = itemSpace * spanSize - horizontalSpace * (spanSize - 1) - left
            outRect.left = left
            outRect.right = right
            //上下间隔处理
            outRect.top = if (groupIndex == 0) topSpace else 0
            outRect.bottom = if (groupIndex == maxGroupIndex) bottomSpace else verticalSpace
        }
    }

    /**
     * 绘制GridLayoutManager分割线
     */
    private fun drawGridItemDivider(canvas: Canvas, parent: RecyclerView) {
        val manager = parent.layoutManager as? GridLayoutManager ?: return
        val itemCount = parent.adapter?.itemCount ?: return
        if (itemCount <= 0) return
        val orientation = manager.orientation
        val spanCount = manager.spanCount
        val maxGroupIndex = manager.spanSizeLookup.getSpanGroupIndex(itemCount - 1, spanCount)
        //绘制当前显示的item分割线
        for (i in 0 until parent.childCount) {
            val child = parent.getChildAt(i)
            val position = parent.getChildAdapterPosition(child)
            //获取item所在行列，主方向索引为跨行/列的结尾索引，如：竖直方向，跨3列的第1列item，其columnIndex为2
            val spanSize = manager.spanSizeLookup.getSpanSize(position)
            val spanIndex = manager.spanSizeLookup.getSpanIndex(position, spanCount)
            val groupIndex = manager.spanSizeLookup.getSpanGroupIndex(position, spanCount)
            val columnIndex =
                if (orientation == LinearLayoutManager.HORIZONTAL) groupIndex else spanIndex + spanSize - 1
            val rowIndex =
                if (orientation == LinearLayoutManager.HORIZONTAL) spanIndex + spanSize - 1 else groupIndex
            val maxColumnIndex =
                if (orientation == LinearLayoutManager.HORIZONTAL) maxGroupIndex else spanCount - 1
            val maxRowIndex =
                if (orientation == LinearLayoutManager.HORIZONTAL) spanCount - 1 else maxGroupIndex

            //绘制不同行之间的分割线，最后一行不绘制
            if (rowIndex != maxRowIndex) {
                val dividerHeight =
                    if (dividerSize <= 0) verticalSpace else min(dividerSize, verticalSpace)
                //竖直方向时当第1列item跨列显示时，无法直接使用columnIndex等于0来判断，因为columnIndex在跨列时是其结尾索引
                val isFirstColumn =
                    (columnIndex == 0) || (spanIndex == 0 && orientation == LinearLayoutManager.VERTICAL)
                val topV = child.bottom + (verticalSpace - dividerHeight.toFloat()) / 2
                val bottomV = child.bottom + (verticalSpace + dividerHeight.toFloat()) / 2
                val leftV =
                    if (isFirstColumn) child.left.toFloat() else child.left - horizontalSpace.toFloat() / 2
                val rightV =
                    if (columnIndex == maxColumnIndex) child.right.toFloat() else child.right + horizontalSpace.toFloat() / 2
                //绘制
                dividerDrawable.setBounds(
                    leftV.toInt() + dividerPadding,
                    topV.toInt() + dividerPadding,
                    rightV.toInt() - dividerPadding,
                    bottomV.toInt() - dividerPadding
                )
                dividerDrawable.draw(canvas)
            }

            //绘制不同列之间的分割线，最后一列不绘制
            if (columnIndex != maxColumnIndex) {
                val dividerWidth =
                    if (dividerSize <= 0) horizontalSpace else min(dividerSize, horizontalSpace)
                //首行
                val isFirstRow =
                    (rowIndex == 0) || (spanIndex == 0 && orientation == LinearLayoutManager.HORIZONTAL)
                val leftH = child.right + (horizontalSpace - dividerWidth.toFloat()) / 2
                val rightH = child.right + (horizontalSpace + dividerWidth.toFloat()) / 2
                val topH =
                    if (isFirstRow) child.top.toFloat() else child.top - verticalSpace.toFloat() / 2
                val bottomH =
                    if (rowIndex == maxRowIndex) child.bottom.toFloat() else child.bottom + verticalSpace.toFloat() / 2
                //绘制
                dividerDrawable.setBounds(
                    leftH.toInt() + dividerPadding,
                    topH.toInt() + dividerPadding,
                    rightH.toInt() - dividerPadding,
                    bottomH.toInt() - dividerPadding
                )
                dividerDrawable.draw(canvas)
            }
        }
    }

    /**
     * 检查item间隔[verticalSpace]和[horizontalSpace]，必须大于等于[dividerSize],
     * 如果小于[dividerSize]会被强制修改为[dividerSize]
     */
    private fun checkSpace() {
        //如果item之间的间隔小于小于分割线大小，设置为分割线大小
        if (verticalSpace < dividerSize) {
            Log.w(
                TAG,
                "the verticalSpace(${verticalSpace}) is small than divider size(${dividerSize}), it will be changed to ${dividerSize}"
            )
            verticalSpace = dividerSize
        }
        if (horizontalSpace < dividerSize) {
            Log.w(
                TAG,
                "the horizontalSpace(${horizontalSpace}) is small than divider size(${dividerSize}), it will be changed to ${dividerSize}"
            )
            horizontalSpace = dividerSize
        }
    }

    /**
     * 设置item之间的间隔大小
     */
    fun setItemSpace(vertical: Int, horizontal: Int) {
        verticalSpace = vertical
        horizontalSpace = horizontal
        //校验数据正确性
        checkSpace()
    }

    /**
     * 设置边缘间隔大小，item距离RecyclerView边缘的间距
     */
    fun setEdgeSpace(left: Int = 0, top: Int = 0, right: Int = 0, bottom: Int = 0) {
        topSpace = top
        leftSpace = left
        bottomSpace = bottom
        rightSpace = right
    }

    /**
     * 设置分割线边距
     */
    fun setDividerPadding(padding: Int) {
        dividerPadding = padding
    }

    /**
     * 是否绘制分割线
     */
    fun drawDivider(draw: Boolean) {
        drawDivider = draw
    }

    /**
     * 设置分割线大小
     */
    fun setDividerSize(size: Int) {
        dividerSize = size
        //校验数据正确性
        checkSpace()
    }

    /**
     * 设置分割线颜色
     */
    fun setDividerColor(@ColorInt color: Int) {
        dividerDrawable = ColorDrawable(color)
    }

    /**
     * 设置分割线drawable
     */
    fun setDividerDrawable(drawable: Drawable) {
        dividerDrawable = drawable
    }

}