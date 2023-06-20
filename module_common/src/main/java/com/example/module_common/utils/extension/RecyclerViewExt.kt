package com.example.module_common.utils.extension

import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.view.View
import androidx.annotation.ColorRes
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import androidx.recyclerview.widget.SimpleItemAnimator
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.module_common.view.help.SimpleDividerItemDecoration

/**
 * 设置分割线
 * @param colorRes 分割线颜色
 * @param size 分割线大小
 */
fun RecyclerView.divider(@ColorRes colorRes: Int, size: Int, space: Int = 0) {
    divider(ColorDrawable(resources.getColor(colorRes)), size, space)
}

/**
 * 设置分割线
 * @param drawable 分割线drawable
 * @param size 分割线大小
 * @param space item之间的间隔，默认为分割线大小
 */
fun RecyclerView.divider(drawable: Drawable, size: Int, space: Int = 0) {
    setSimpleDivider {
        if (space != 0) {
            setItemSpace(space, space)
        }
        setDividerSize(size)
        drawDivider(true)
        setDividerDrawable(drawable)
    }
}

/**
 * 设置间隔
 * @param size 间隔大小
 * @param header 首行/列距离边缘的间隔
 * @param footer 尾行/列距离边缘的间隔
 */
fun RecyclerView.space(size: Int, header: Int = 0, footer: Int = 0) {
    setSimpleDivider {
        setItemSpace(size, size)
        //设置header和footer空白间隔
        (layoutManager as? LinearLayoutManager)?.let {
            if (it.orientation == LinearLayoutManager.HORIZONTAL) {
                setEdgeSpace(header, 0, footer, 0)
            } else {
                setEdgeSpace(0, header, 0, footer)
            }
        }
    }
}

/**
 * 使用[SimpleDividerItemDecoration]设置RecyclerView的item分割线和间隔；
 * 如果已存在[SimpleDividerItemDecoration]，会修改原有的对象，否则创建新的对象
 */
fun RecyclerView.setSimpleDivider(block: SimpleDividerItemDecoration.() -> Unit) {
    obtainItemDecoration<SimpleDividerItemDecoration>()
        .checkNull({
            block()
        }, {
            val decoration = SimpleDividerItemDecoration()
            block.invoke(decoration)
            addItemDecoration(decoration)
        })
}

/**
 * 获取RecyclerView中已存在的对应类型的ItemDecoration
 */
inline fun <reified T : ItemDecoration> RecyclerView.obtainItemDecoration(): T? {
    for (i in 0 until itemDecorationCount) {
        val decoration = getItemDecorationAt(i)
        if (decoration is T) {
            return decoration
        }
    }
    return null
}

/**
 * 设置itemDecoration，注意：该方法会清除其他的itemDecoration
 * @param itemDecoration 添加的itemDecoration
 */
fun RecyclerView.setItemDecoration(itemDecoration: ItemDecoration) {
    //移除原有itemDecoration
    removeAllItemDecoration()
    //添加itemDecoration
    addItemDecoration(itemDecoration)
}

/**
 * 移除所有itemDecoration
 */
fun RecyclerView.removeAllItemDecoration() {
    if (itemDecorationCount <= 0) return
    val decorations = mutableListOf<ItemDecoration>()
    //获取原有itemDecoration
    for (index in 0 until itemDecorationCount) {
        decorations.add(getItemDecorationAt(index))
    }
    //移除原有itemDecoration
    decorations.forEach {
        removeItemDecoration(it)
    }
}

/**
 * 设置布局为[LinearLayoutManager]
 * @param orientation 布局方向，默认为竖直方向
 * @param reverse 是否反转方向
 */
fun RecyclerView.linearLayout(
    @RecyclerView.Orientation orientation: Int = RecyclerView.VERTICAL, reverse: Boolean = false
) {
    layoutManager = LinearLayoutManager(context, orientation, reverse)
}

/**
 * 设置布局为[GridLayoutManager]
 * @param spanCount 主方向行数/列数
 * @param orientation 布局方向，默认为竖直方向
 * @param reverse 是否反转方向
 */
fun RecyclerView.gridLayout(
    spanCount: Int,
    @RecyclerView.Orientation orientation: Int = RecyclerView.VERTICAL,
    reverse: Boolean = false
) {
    layoutManager = GridLayoutManager(context, spanCount, orientation, reverse)
}

/**
 * 设置布局为[StaggeredGridLayoutManager]
 * @param spanCount 主方向行数/列数
 * @param orientation 布局方向，默认为竖直方向
 * @param reverse 是否反转方向
 */
fun RecyclerView.staggeredGridLayout(
    spanCount: Int,
    @RecyclerView.Orientation orientation: Int = RecyclerView.VERTICAL,
    reverse: Boolean = false
) {
    layoutManager = StaggeredGridLayoutManager(spanCount, orientation).apply {
        reverseLayout = reverse
    }
}

/**
 * 移除item动画
 */
fun RecyclerView.removeItemAnimation() {
    (itemAnimator as? SimpleItemAnimator)?.supportsChangeAnimations = false
}

/**
 * 隐藏上滑和下拉边界阴影
 */
fun RecyclerView.neverOverScroll() {
    overScrollMode = View.OVER_SCROLL_NEVER
}

/**
 * TODO 设置滚动条样式
 */
fun RecyclerView.scrollBar() {

}