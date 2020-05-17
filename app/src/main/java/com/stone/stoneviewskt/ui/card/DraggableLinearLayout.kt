package com.stone.stoneviewskt.ui.card

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.LinearLayout
import androidx.customview.widget.ViewDragHelper
import com.google.android.material.card.MaterialCardView

/**
 * desc:    可拖动 LinearLayout, 使用 ViewDragHelper。
 *          对 MaterialCardView 在拖动状态时 设置 isDragged = true 。
 * author:  stone
 * email:   aa86799@163.com
 * blog :   https://stone.blog.csdn.net
 * time:    2020/5/17 17:37
 */
class DraggableLinearLayout : LinearLayout {

    private var viewDragHelper: ViewDragHelper? = null
    private val draggableChildren = mutableListOf<View>()
    private var viewDragListener: ViewDragListener? = null

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {

        //sensitivity 速率：1.0 是正常速率；越大越慢
        viewDragHelper = ViewDragHelper.create(this, 0.5f, dragCallback)
    }

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context) : this(context, null, 0)

    interface ViewDragListener {
        fun onViewCaptured(capturedChild: View, activePointerId: Int)
        fun onViewReleased(releasedChild: View, xvel: Float, yvel: Float)
    }

    fun addDraggableChild(child: View) {
        require(!(child.parent !== this))
        draggableChildren.add(child)
    }

    fun removeDraggableChild(child: View) {
        require(!(child.parent !== this))
        draggableChildren.remove(child)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        return viewDragHelper?.shouldInterceptTouchEvent(ev) == true || super.onInterceptTouchEvent(ev)
    }

    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        viewDragHelper?.processTouchEvent(ev!!)
        return super.onTouchEvent(ev)
    }

    private val dragCallback: ViewDragHelper.Callback = object : ViewDragHelper.Callback() {
        //如果返回true表示捕获相关View，可以根据第一个参数child决定捕获哪个View。
        override fun tryCaptureView(child: View, pointerId: Int): Boolean {
            return child.visibility == View.VISIBLE && viewIsDraggableChild(child)
        }

        override fun onViewCaptured(capturedChild: View, activePointerId: Int) {
            if (capturedChild is MaterialCardView) {
                capturedChild.isDragged = true
            }
            viewDragListener?.onViewCaptured(capturedChild, activePointerId)
        }

        /**
         * @param xvel x方向速率
         * @param yvel y方向速率
         */
        override fun onViewReleased(releasedChild: View, xvel: Float, yvel: Float) {
            if (releasedChild is MaterialCardView) {
                releasedChild.isDragged = false
            }
            viewDragListener?.onViewReleased(releasedChild, xvel, yvel)
        }

        override fun getViewHorizontalDragRange(child: View): Int {
            return child.width
        }

        override fun getViewVerticalDragRange(child: View): Int {
            return child.height
        }

        /**
         *  设置水平方向拖动后固定的位置； 返回 view:left
         *  @param left view 拖动后的 left
         */
        override fun clampViewPositionHorizontal(view: View, left: Int, dx: Int): Int {
            if (left < 0) return 0
            if (left > width - view.width) return width - view.width
            return left
        }

        /**
         *  设置垂直方向拖动后固定的位置； 返回 view:top
         *  @param top view 拖动后的 top
         */
        override fun clampViewPositionVertical(view: View, top: Int, dy: Int): Int {
            if (top < 0) return 0
            if (top > height - view.height) return height - view.height
            return top
        }

        override fun onViewDragStateChanged(state: Int) {
            super.onViewDragStateChanged(state)
        }

        override fun onViewPositionChanged(changedView: View, left: Int, top: Int, dx: Int, dy: Int) {
            super.onViewPositionChanged(changedView, left, top, dx, dy)
        }
    }

    private fun viewIsDraggableChild(view: View): Boolean {
        return draggableChildren.isEmpty() || draggableChildren.contains(view)
    }

    fun setViewDragListener(viewDragListener: ViewDragListener) {
        this.viewDragListener = viewDragListener
    }
}