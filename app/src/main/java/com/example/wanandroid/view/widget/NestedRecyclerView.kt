package com.example.wanandroid.view.widget

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import kotlin.math.abs

/**
 * @author: Yang
 * @date: 2023/7/25
 * @description: 嵌套recyclerView，用于解决ViewPager2嵌套RecyclerView滑动不流畅问题
 */
class NestedRecyclerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {

    private var lastX = 0f
    private var lastY = 0f

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (ev == null) {
            return super.dispatchTouchEvent(ev)
        }
        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
//                parent?.requestDisallowInterceptTouchEvent(true)
                allParentRequestDisallowInterceptTouchEvent(true)
            }

            MotionEvent.ACTION_MOVE -> {
                val offsetX = ev.x - lastX
                val offsetY = ev.y - lastY
                val orientation = when (layoutManager) {
                    is LinearLayoutManager -> {
                        (layoutManager as LinearLayoutManager).orientation
                    }

                    is StaggeredGridLayoutManager -> {
                        (layoutManager as StaggeredGridLayoutManager).orientation
                    }

                    else -> {
                        -1
                    }
                }
                val canScroll = when (orientation) {
                    HORIZONTAL -> (abs(offsetY) < abs(offsetX)) && canScrollHorizontally(offsetX.toInt())
                    VERTICAL -> (abs(offsetY) > abs(offsetX)) && canScrollVertically(offsetY.toInt())
                    else -> false
                }
                //判断列表方向是否可滑动，如果可滑动，禁止父布局拦截事件
                if (!canScroll) {
//                    parent?.requestDisallowInterceptTouchEvent(false)
                    allParentRequestDisallowInterceptTouchEvent(false)
                }
            }

            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
//                parent?.requestDisallowInterceptTouchEvent(false)
                allParentRequestDisallowInterceptTouchEvent(false)
            }
        }
        lastY = ev.y
        lastX = ev.x
        return super.dispatchTouchEvent(ev)
    }

    private fun allParentRequestDisallowInterceptTouchEvent(disallow: Boolean) {
        var view = parent
        while (view != null) {
            view.requestDisallowInterceptTouchEvent(disallow)
            view = view.parent
        }
    }

}